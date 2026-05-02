import { useEffect, useState } from "react";
import { Outlet, useLocation, useNavigate, useParams } from "react-router-dom";
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