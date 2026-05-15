// src/shared/AppContext.tsx
import {createContext, type ReactNode, useContext, useEffect, useState} from "react";
import {RestService} from "../../rest/RestService.tsx";

interface User {
    id: string;
    name: string;
    email: string;
    username: string;
    profileImage?: string;
    isActive: boolean;
    company: Company;
    role: Role[]
}

interface Company {
    id: string;
    name: string;
    companyLogo?: string;
    slug: string;
    users: User[];
}

interface Role {
    id: string;
    name: string;
    description: string;
    isActive: boolean
    actions: Action[]
}

interface Action {
    id: string;
    name: string;
    description: string;
}

interface ApiResponse<T> {
    data: T;
    message: string;
    success: boolean;
}

interface AppContextType {
    user: User | null;
    company: Company | null;
    loading: boolean;
}

const AppContext = createContext<AppContextType>({
    user: null,
    company: null,
    loading: true,
});

export const useApp = () => useContext(AppContext);

export const AppProvider = ({ children }: { children: ReactNode }) => {
    const [user, setUser] = useState<User | null>(null);
    const [company, setCompany] = useState<Company | null>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchAppData = async () => {
            try {
                const userResponse = await RestService.get<ApiResponse<User>>("/api/v1/user/" + localStorage.getItem("user_id"));
                const userData = userResponse.data;


                const companyResponse = await RestService.get<ApiResponse<Company>>(`/api/v1/company/${userData.company.id}`);
                const companyData = companyResponse.data;
                setCompany(companyData);

                const rolesResponse = await RestService.get<ApiResponse<Role[]>>(`/api/v1/role/user/${userData.id}`);
                userData.role = rolesResponse.data
                setUser(userData);


            } catch {
                // token errors already handled by ProtectedRoute
            } finally {
                setLoading(false);
            }
        };
        fetchAppData();
    }, []);

    return (
        <AppContext.Provider value={{ user, company, loading }}>
            {children}
        </AppContext.Provider>
    );
};