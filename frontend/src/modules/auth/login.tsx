import { useState } from "react";
import "./login.css";
import * as React from "react";
import { AuthService } from "./AuthService.tsx";
import { useNavigate } from "react-router-dom";

function Login() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

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
        <div className="login-container">
            {error && (
                <div className="error-popup">
                    <span>⚠️ {error}</span>
                    <button className="error-close" onClick={() => setError(null)}>✕</button>
                </div>
            )}
            <div className="login-card">
                <h2>Welcome back 👋</h2>
                <p>Sign in to your account</p>
                <form onSubmit={handleSubmit}>
                    <div className="input-group">
                        <input
                            type="email"
                            required
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                        />
                        <label>Email</label>
                    </div>
                    <div className="input-group">
                        <input
                            type="password"
                            required
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                        <label>Password</label>
                    </div>
                    <button type="submit">Login</button>
                </form>
            </div>
        </div>
    );
}

export default Login;