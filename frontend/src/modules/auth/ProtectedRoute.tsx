import { useEffect, useState } from "react";
import {Navigate, Outlet, useLocation, useNavigate, useParams} from "react-router-dom";
import { AuthService } from "./AuthService.tsx";
import { AppProvider, useApp } from "../shared/appcontext/AppContext";
import { SideBar } from "../shared/sidebar/SideBar";

const ProtectedLayout = () => {
    const { slug } = useParams();
    const { company, loading } = useApp();
    const navigate = useNavigate();

    useEffect(() => {
        if (!loading && company && company.slug !== slug) {
            navigate(`/${company.slug}/dashboard`, { replace: true });
        }
    }, [loading, company, slug]);

    if (loading) return null;

    return (
        <>
            <SideBar />
            <div className="pl-20">
                <Outlet />
            </div>
        </>
    );
};

interface ProtectedActionProps {
    action: string;
    children: React.ReactNode;
}

const ProtectedAction = ({ action, children }: ProtectedActionProps) => {
    const { user } = useApp();
    const navigate = useNavigate();

    const hasPermission = user?.role.some(role =>
        role.isActive &&
        role.actions?.some(a => a.name === action)
    );

    if (!hasPermission) {
        return <Navigate to="/dashboard" replace />;
    }

    return <>{children}</>;
};

export const ProtectedRoute = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const [checking, setChecking] = useState(true);

    useEffect(() => {
        setChecking(true);
        AuthService.checkSession()
            .catch((err) => {
                const msg = err instanceof Error ? err.message : "Session expired.";
                sessionStorage.setItem("logout_error", msg);
                navigate("/login", { replace: true });
            })
            .finally(() => setChecking(false));
    }, [location.pathname]);

    if (checking) return null;

    return (
        <AppProvider>
            <ProtectedLayout />
        </AppProvider>
    );
};