import { useTheme } from "../hooks/useTheme";
import { AuthService } from "../../auth/AuthService";
import { useApp } from "../appcontext/AppContext";
import { useNavigate } from "react-router-dom";

export const SideBar = () => {
    const { dark, toggle } = useTheme();
    const { user, company } = useApp();
    const navigate = useNavigate();

    const handleLogout = () => {
        AuthService.logout();
        navigate("/login", { replace: true });
    };

    return (
        <div className="fixed left-4 top-1/2 -translate-y-1/2 z-50 flex flex-col items-center justify-between gap-4 bg-white/80 dark:bg-gray-900/80 backdrop-blur-md border border-gray-200 dark:border-gray-700 shadow-xl rounded-2xl px-3 py-4">

            {/* Top: Company logo */}
            <div className="flex flex-col items-center gap-4">
                {company?.companyLogo ? (
                    <img src={company.companyLogo} alt="Company"
                         className="h-8 w-8 rounded-xl object-contain"/>
                ) : (
                    <div className="h-8 w-8 rounded-xl bg-blue-100 dark:bg-blue-900 text-blue-600 dark:text-blue-300 text-sm font-bold flex items-center justify-center">
                        {company?.name?.[0] ?? "C"}
                    </div>
                )}

                <div className="w-full h-px bg-gray-200 dark:bg-gray-700"/>

                {/* Nav icons */}
                <button title="Dashboard" onClick={() => navigate(`/${company?.slug}/dashboard`)}
                        className="p-2 rounded-xl text-gray-500 dark:text-gray-400 hover:bg-gray-100 dark:hover:bg-gray-800 hover:text-blue-600 dark:hover:text-blue-400 transition-colors">
                    <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none"
                         viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2}>
                        <path strokeLinecap="round" strokeLinejoin="round"
                              d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"/>
                    </svg>
                </button>
                {user?.role.some(role => role.name === 'admin' && role.isActive) && (
                    <button
                        title="Roles"
                        onClick={() => navigate(`/${company?.slug}/roles`)}
                        className="p-2 rounded-xl text-gray-500 dark:text-gray-400 hover:bg-gray-100 dark:hover:bg-gray-800 hover:text-blue-600 dark:hover:text-blue-400 transition-colors">
                        <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none"
                             viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2}>
                            <path strokeLinecap="round" strokeLinejoin="round"
                                  d="M12 3l7 4v5c0 5-3.5 9-7 9s-7-4-7-9V7l7-4z" />
                        </svg>
                    </button>
                )}
            </div>

            {/* Bottom: theme toggle + profile + logout */}
            <div className="flex flex-col items-center gap-3">
                <div className="w-full h-px bg-gray-200 dark:bg-gray-700"/>

                {/* Dark mode toggle */}
                <button onClick={toggle} title="Toggle theme"
                        className="p-2 rounded-xl text-gray-400 dark:text-gray-500 hover:bg-gray-100 dark:hover:bg-gray-800 hover:text-yellow-500 dark:hover:text-yellow-300 transition-colors">
                    {dark ? (
                        <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none"
                             viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2}>
                            <path strokeLinecap="round" strokeLinejoin="round"
                                  d="M12 3v1m0 16v1m8.66-9h-1M4.34 12h-1m15.07-6.07-.71.71M6.34 17.66l-.71.71m12.02 0-.71-.71M6.34 6.34l-.71-.71M12 5a7 7 0 100 14A7 7 0 0012 5z"/>
                        </svg>
                    ) : (
                        <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none"
                             viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2}>
                            <path strokeLinecap="round" strokeLinejoin="round"
                                  d="M21 12.79A9 9 0 1111.21 3 7 7 0 0021 12.79z"/>
                        </svg>
                    )}
                </button>

                {/* Profile picture */}
                {user?.profileImage ? (
                    <img src={user.profileImage} alt="Profile"
                         className="h-8 w-8 rounded-full object-cover border border-gray-300 dark:border-gray-600"/>
                ) : (
                    <div className="h-8 w-8 rounded-full bg-gray-200 dark:bg-gray-700 text-gray-600 dark:text-gray-300 text-sm font-medium flex items-center justify-center border border-gray-300 dark:border-gray-600">
                        {user?.username?.[0]?.toUpperCase() ?? "U"}
                    </div>
                )}

                {/* Logout */}
                <button onClick={handleLogout} title="Logout"
                        className="p-2 rounded-xl text-gray-400 dark:text-gray-500 hover:bg-red-50 dark:hover:bg-red-900/30 hover:text-red-500 dark:hover:text-red-400 transition-colors">
                    <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none"
                         viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2}>
                        <path strokeLinecap="round" strokeLinejoin="round"
                              d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a2 2 0 01-2 2H5a2 2 0 01-2-2V7a2 2 0 012-2h6a2 2 0 012 2v1"/>
                    </svg>
                </button>
            </div>
        </div>
    );
};