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
    };
}

export const AuthService = {
    login: async (email: string, password: string): Promise<void> => {
        const response = await RestService.post<LoginResponse, LoginBody>(
            "/api/auth/login",
            { email, password }
        );

        if (!response.data.token) {
            throw new Error(response.message);
        }

        localStorage.setItem("session_token", response.data.token);
    },

    logout: (): void => {
        localStorage.removeItem("session_token");
    },

    isAuthenticated: (): boolean => {
        return !!localStorage.getItem("session_token");
    },

    getToken: (): string | null => {
        return localStorage.getItem("session_token");
    },
};