import { RestService } from "../rest/RestService.tsx";

interface LoginBody {
    email: string;
    password: string;
}

interface LoginResponse {
    message: string;
    success: boolean;
    data: {
        token: string;
        userid: string;
    };
}

interface CheckSessionResponse {
    message: string;
    success: boolean;
}

const CHECK_INTERVAL_MS = 30 * 1000;
let sessionCheckInterval: ReturnType<typeof setInterval> | null = null;

export const AuthService = {
    login: async (email: string, password: string): Promise<void> => {
        const response = await RestService.post<LoginResponse, LoginBody>(
            "/api/auth/login",
            { email, password }
        );
        if (!response.data.token) throw new Error(response.message);
        console.log(response)
        localStorage.setItem("session_token", response.data.token);
        localStorage.setItem("user_id", response.data.userid)
        AuthService.startSessionCheck();
    },

    logout: (errorMessage?: string): void => {
        RestService.delete("/api/auth/logout", {token : localStorage.getItem("session_token")}).then(() => {
            localStorage.removeItem("session_token");
            AuthService.stopSessionCheck();
            if (errorMessage) sessionStorage.setItem("logout_error", errorMessage);
            window.location.href = "/login";
        });

    },

    isAuthenticated: (): boolean => !!localStorage.getItem("session_token"),

    getToken: (): string | null => localStorage.getItem("session_token"),

    checkSession: async (): Promise<void> => {
        if (!AuthService.isAuthenticated()) {
            throw new Error("Your session has expired. Please log in again.");
        }
        try {
            const response = await RestService.get<CheckSessionResponse>("/api/auth/checkSession");
            if (!response.success) {
                throw new Error("Your session has expired. Please log in again.");
            }
        } catch (err) {
            localStorage.removeItem("session_token");
            AuthService.stopSessionCheck();
            throw err;
        }
    },

    startSessionCheck: (): void => {
        AuthService.stopSessionCheck();
        sessionCheckInterval = setInterval(AuthService.checkSession, CHECK_INTERVAL_MS);
    },

    stopSessionCheck: (): void => {
        if (sessionCheckInterval !== null) {
            clearInterval(sessionCheckInterval);
            sessionCheckInterval = null;
        }
    },
};