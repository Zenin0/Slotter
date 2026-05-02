import { useState, useEffect } from "react";
import * as React from "react";
import { AuthService } from "./AuthService.tsx";
import { useNavigate } from "react-router-dom";

function Login() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    useEffect(() => {
        const msg = sessionStorage.getItem("logout_error");
        if (msg) {
            setError(msg);
            sessionStorage.removeItem("logout_error");
        }
    }, []);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);
        try {
            await AuthService.login(email, password);
            navigate("/dashboard");
        } catch (err) {
            setError(err instanceof Error ? err.message : "Something went wrong");
        }
    };

    return (
        <div className="min-h-screen bg-gray-100 dark:bg-gray-950 flex items-center justify-center px-4">

            {/* Error notification */}
            {error && (
                <div className="fixed top-5 left-1/2 -translate-x-1/2 z-50 flex items-center gap-3 bg-red-50 dark:bg-red-900/30 border border-red-300 dark:border-red-700 text-red-700 dark:text-red-400 px-4 py-3 rounded-lg shadow-md">
                    <span>⚠️ {error}</span>
                    <button onClick={() => setError(null)}
                            className="text-red-400 hover:text-red-600 dark:hover:text-red-300 font-bold">✕</button>
                </div>
            )}

            {/* Card */}
            <div className="bg-white dark:bg-gray-900 border border-transparent dark:border-gray-700 rounded-2xl shadow-lg p-8 w-full max-w-md">
                <h2 className="text-2xl font-semibold text-gray-800 dark:text-white mb-1">Welcome back 👋</h2>
                <p className="text-gray-500 dark:text-gray-400 text-sm mb-6">Sign in to your account</p>

                <form onSubmit={handleSubmit} className="flex flex-col gap-4">

                    {/* Email */}
                    <div className="flex flex-col gap-1">
                        <label className="text-sm font-medium text-gray-600 dark:text-gray-400">Email</label>
                        <input
                            type="email"
                            required
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            className="border border-gray-300 dark:border-gray-700 bg-white dark:bg-gray-800 text-gray-800 dark:text-white rounded-lg px-4 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 transition placeholder-gray-400 dark:placeholder-gray-600"
                            placeholder="you@example.com"
                        />
                    </div>

                    {/* Password */}
                    <div className="flex flex-col gap-1">
                        <label className="text-sm font-medium text-gray-600 dark:text-gray-400">Password</label>
                        <input
                            type="password"
                            required
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            className="border border-gray-300 dark:border-gray-700 bg-white dark:bg-gray-800 text-gray-800 dark:text-white rounded-lg px-4 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 transition placeholder-gray-400 dark:placeholder-gray-600"
                            placeholder="••••••••"
                        />
                    </div>

                    {/* Submit */}
                    <button
                        type="submit"
                        className="mt-2 bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 rounded-lg transition">
                        Login
                    </button>
                </form>
            </div>
        </div>
    );
}

export default Login;