import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Login from "./modules/auth/Login";
import Dashboard from "./modules/dashboard/DashBoard";
import Roles from "./modules/roles/Roles"
import { ProtectedRoute, ProtectedAction } from "./modules/auth/ProtectedRoute";
import { useTheme } from "./modules/shared/hooks/useTheme";
import Users from "./modules/user/User";
import Action from "./modules/action/Action.tsx";
import Customer from "./modules/customer/Customer.tsx";

function App() {
    useTheme();
    return (
        <div className="min-h-screen bg-gray-100 dark:bg-gray-950 text-gray-800 dark:text-white transition-colors duration-300">
            <BrowserRouter>
                <Routes>
                    <Route path="/login" element={<Login />} />

                    {/* Backoffice - protected */}
                    <Route path="/:slug" element={<ProtectedRoute />}>
                        <Route path="dashboard" element={<Dashboard />} />

                        <Route element={<ProtectedAction action="Manage Roles" />}>
                            <Route path="roles" element={<Roles />} />
                        </Route>

                        <Route element={<ProtectedAction action="Manage Users" />}>
                            <Route path="users" element={<Users />} />
                        </Route>

                        <Route element={<ProtectedAction action="Manage Actions" />}>
                            <Route path="actions" element={<Action />} />
                        </Route>

                        <Route element={<ProtectedAction action="Manage Customers" />}>
                            <Route path="customers" element={<Customer />} />
                        </Route>
                    </Route>

                    <Route path="*" element={<Navigate to="/login" replace />} />
                </Routes>
            </BrowserRouter>
        </div>
    );
}

export default App;