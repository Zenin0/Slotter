import { useEffect, useState } from "react";
import { RestService } from "../rest/RestService.tsx";
import { useApp } from "../shared/appcontext/AppContext.tsx";
import { useTranslation } from "react-i18next";

interface User {
    id: string;
    name: string;
    email: string;
    username: string;
    profileImage?: string;
    isActive: boolean;
    companyId: string;
}

interface ApiResponse<T> {
    data: T;
    message: string;
    success: boolean;
}

function Users() {
    const { t } = useTranslation();
    const { company } = useApp();

    const [users, setUsers] = useState<User[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    const [showCreate, setShowCreate] = useState(false);
    const [newEmail, setNewEmail] = useState("");
    const [newUsername, setNewUsername] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [saving, setSaving] = useState(false);

    const [editingUser, setEditingUser] = useState<User | null>(null);
    const [editEmail, setEditEmail] = useState("");
    const [editUsername, setEditUsername] = useState("");
    const [editPassword, setEditPassword] = useState("");
    const [editSaving, setEditSaving] = useState(false);

    const fetchUsers = async () => {
        try {
            setLoading(true);
            const response = await RestService.get<ApiResponse<User[]>>(
                `/api/v1/user/company/${company?.id}`
            );
            setUsers(response.data);
        } catch {
            setError(t("failedLoadUsers"));
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        if (company?.id) fetchUsers();
    }, [company?.id]);

    const handleCreate = async () => {
        if (!newEmail.trim() || !newUsername.trim() || !newPassword.trim()) return;
        setSaving(true);

        try {
            await RestService.post("/api/v1/user", {
                email: newEmail,
                username: newUsername,
                password: newPassword,
                companyId: company?.id,
                isActive: true,
            });

            setNewEmail("");
            setNewUsername("");
            setNewPassword("");
            setShowCreate(false);
            await fetchUsers();
        } catch {
            setError(t("failedCreateUser"));
        } finally {
            setSaving(false);
        }
    };

    const openEdit = (user: User) => {
        setEditingUser(user);
        setEditEmail(user.email);
        setEditUsername(user.username);
        setEditPassword("");
    };

    const handleEdit = async () => {
        if (!editingUser || !editEmail.trim() || !editUsername.trim()) return;
        setEditSaving(true);

        try {
            await RestService.put(`/api/v1/user/${editingUser.id}`, {
                email: editEmail,
                username: editUsername,
                password: editPassword || null,
                isActive: editingUser.isActive,
                companyId: editingUser.companyId,
            });

            setEditingUser(null);
            setEditPassword("");
            await fetchUsers();
        } catch {
            setError(t("failedUpdateUser"));
        } finally {
            setEditSaving(false);
        }
    };

    const handleToggleActive = async (user: User) => {
        try {
            await RestService.put(`/api/v1/user/${user.id}`, {
                email: user.email,
                username: user.username,
                password: null,
                isActive: !user.isActive,
                companyId: user.companyId,
            });

            await fetchUsers();
        } catch {
            setError(t("failedUpdateUser"));
        }
    };

    if (loading) return null;

    return (
        <div className="min-h-screen bg-gray-100 dark:bg-gray-950 p-8">
            <div className="max-w-4xl mx-auto">

                {/* Error */}
                {error && (
                    <div className="mb-4 flex items-center justify-between bg-red-50 dark:bg-red-900/30 border border-red-200 dark:border-red-700 text-red-600 dark:text-red-400 px-4 py-3 rounded-xl text-sm">
                        <span>⚠️ {error}</span>
                        <button
                            onClick={() => setError(null)}
                            className="font-bold hover:text-red-800 dark:hover:text-red-200"
                        >
                            ✕
                        </button>
                    </div>
                )}

                {/* Header */}
                <div className="mb-6 flex items-center justify-between">
                    <div>
                        <h1 className="text-2xl font-semibold text-gray-800 dark:text-white">
                            {t("users")}
                        </h1>
                        <p className="text-sm text-gray-500 dark:text-gray-400 mt-1">
                            {t("membersOf")} {company?.name}
                        </p>
                    </div>

                    <div className="flex items-center gap-3">
                        <span className="text-xs text-gray-400 dark:text-gray-500 bg-white dark:bg-gray-900 border border-gray-200 dark:border-gray-700 px-3 py-1.5 rounded-xl">
                            {users.length} {t("users").toLowerCase()}
                        </span>

                        <button
                            onClick={() => setShowCreate(true)}
                            className="flex items-center gap-2 bg-blue-600 hover:bg-blue-700 text-white text-sm font-medium px-4 py-2 rounded-xl transition-colors"
                        >
                            <svg xmlns="http://www.w3.org/2000/svg" className="h-4 w-4" fill="none"
                                 viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2}>
                                <path strokeLinecap="round" strokeLinejoin="round" d="M12 4v16m8-8H4"/>
                            </svg>
                            {t("newUser")}
                        </button>
                    </div>
                </div>

                {/* Users list */}
                <div className="bg-white dark:bg-gray-900 rounded-2xl shadow-sm border border-gray-100 dark:border-gray-700 divide-y divide-gray-50 dark:divide-gray-800">
                    {users.length === 0 ? (
                        <p className="text-sm text-gray-400 dark:text-gray-500 p-6">
                            {t("noUsersFound")}
                        </p>
                    ) : users.map((u) => (
                        <div key={u.id} className="flex items-center justify-between px-6 py-4">
                            <div className="flex items-center gap-4">
                                {u.profileImage ? (
                                    <img src={u.profileImage} alt={u.username}
                                         className="h-9 w-9 rounded-full object-cover border border-gray-200 dark:border-gray-700"/>
                                ) : (
                                    <div className="h-9 w-9 rounded-full bg-blue-50 dark:bg-blue-900/30 text-blue-600 dark:text-blue-400 flex items-center justify-center font-bold text-sm">
                                        {u.username[0].toUpperCase()}
                                    </div>
                                )}
                                <div>
                                    <p className="text-sm font-semibold text-gray-800 dark:text-white">{u.username}</p>
                                    <p className="text-xs text-gray-400 dark:text-gray-500">{u.email}</p>
                                </div>
                            </div>

                            <div className="flex items-center gap-2">
                                <span className={`text-xs px-2 py-0.5 rounded-full font-medium ${
                                    u.isActive
                                        ? "bg-green-50 dark:bg-green-900/30 text-green-600 dark:text-green-400"
                                        : "bg-red-50 dark:bg-red-900/30 text-red-500 dark:text-red-400"
                                }`}>
                                    {u.isActive ? t("active") : t("inactive")}
                                </span>

                                <button
                                    onClick={() => openEdit(u)}
                                    className="text-xs px-3 py-1 rounded-lg border border-gray-200 dark:border-gray-700 text-gray-500 dark:text-gray-400 hover:bg-gray-50 dark:hover:bg-gray-800 transition-colors"
                                >
                                    {t("edit")}
                                </button>

                                <button
                                    onClick={() => handleToggleActive(u)}
                                    className="text-xs px-3 py-1 rounded-lg border border-gray-200 dark:border-gray-700 text-gray-500 dark:text-gray-400 hover:bg-gray-50 dark:hover:bg-gray-800 transition-colors"
                                >
                                    {u.isActive ? t("deactivate") : t("activate")}
                                </button>
                            </div>
                        </div>
                    ))}
                </div>
            </div>

            {/* Create modal */}
            {showCreate && (
                <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/40 backdrop-blur-sm">
                    <div className="bg-white dark:bg-gray-900 rounded-2xl shadow-xl border border-gray-100 dark:border-gray-700 p-6 w-full max-w-md">
                        <h2 className="text-lg font-semibold text-gray-800 dark:text-white mb-4">
                            {t("newUser")}
                        </h2>

                        <div className="flex flex-col gap-4">
                            <input
                                value={newUsername}
                                onChange={(e) => setNewUsername(e.target.value)}
                                placeholder={t("usernamePlaceholder")}
                                className="border border-gray-300 dark:border-gray-700 bg-white dark:bg-gray-800 text-gray-800 dark:text-white rounded-lg px-4 py-2 text-sm"
                            />

                            <input
                                value={newEmail}
                                onChange={(e) => setNewEmail(e.target.value)}
                                placeholder={t("emailPlaceholder")}
                                className="border border-gray-300 dark:border-gray-700 bg-white dark:bg-gray-800 text-gray-800 dark:text-white rounded-lg px-4 py-2 text-sm"
                            />

                            <input
                                type="password"
                                value={newPassword}
                                onChange={(e) => setNewPassword(e.target.value)}
                                placeholder={t("passwordPlaceholder")}
                                className="border border-gray-300 dark:border-gray-700 bg-white dark:bg-gray-800 text-gray-800 dark:text-white rounded-lg px-4 py-2 text-sm"
                            />
                        </div>

                        <div className="flex justify-end gap-2 mt-6">
                            <button
                                onClick={() => setShowCreate(false)}
                                className="px-4 py-2 text-sm rounded-xl border border-gray-200 dark:border-gray-700 text-gray-500 dark:text-gray-400 hover:bg-gray-50 dark:hover:bg-gray-800 transition-colors"
                            >
                                {t("cancel")}
                            </button>

                            <button
                                onClick={handleCreate}
                                disabled={saving}
                                className="px-4 py-2 text-sm rounded-xl bg-blue-600 hover:bg-blue-700 disabled:opacity-50 text-white font-medium transition-colors"
                            >
                                {saving ? t("saving") : t("create")}
                            </button>
                        </div>
                    </div>
                </div>
            )}

            {/* Edit modal */}
            {editingUser && (
                <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/40 backdrop-blur-sm">
                    <div className="bg-white dark:bg-gray-900 rounded-2xl shadow-xl border border-gray-100 dark:border-gray-700 p-6 w-full max-w-md">
                        <h2 className="text-lg font-semibold text-gray-800 dark:text-white mb-4">
                            {t("editUser")}
                        </h2>

                        <div className="flex flex-col gap-4">
                            <input
                                value={editUsername}
                                onChange={(e) => setEditUsername(e.target.value)}
                                placeholder={t("usernamePlaceholder")}
                                className="border border-gray-300 dark:border-gray-700 bg-white dark:bg-gray-800 text-gray-800 dark:text-white rounded-lg px-4 py-2 text-sm"
                            />

                            <input
                                value={editEmail}
                                onChange={(e) => setEditEmail(e.target.value)}
                                placeholder={t("emailPlaceholder")}
                                className="border border-gray-300 dark:border-gray-700 bg-white dark:bg-gray-800 text-gray-800 dark:text-white rounded-lg px-4 py-2 text-sm"
                            />

                            <input
                                type="password"
                                value={editPassword}
                                onChange={(e) => setEditPassword(e.target.value)}
                                placeholder={t("keepPasswordPlaceholder")}
                                className="border border-gray-300 dark:border-gray-700 bg-white dark:bg-gray-800 text-gray-800 dark:text-white rounded-lg px-4 py-2 text-sm"
                            />
                        </div>

                        <div className="flex justify-end gap-2 mt-6">
                            <button
                                onClick={() => setEditingUser(null)}
                                className="px-4 py-2 text-sm rounded-xl border border-gray-200 dark:border-gray-700 text-gray-500 dark:text-gray-400 hover:bg-gray-50 dark:hover:bg-gray-800 transition-colors"
                            >
                                {t("cancel")}
                            </button>

                            <button
                                onClick={handleEdit}
                                disabled={editSaving}
                                className="px-4 py-2 text-sm rounded-xl bg-blue-600 hover:bg-blue-700 disabled:opacity-50 text-white font-medium transition-colors"
                            >
                                {editSaving ? t("saving") : t("save")}
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}

export default Users;