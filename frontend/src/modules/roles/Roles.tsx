import { useEffect, useState } from "react";
import { RestService } from "../rest/RestService.tsx";

interface Role {
    id: string;
    name: string;
    description: string;
    isActive: boolean;
}

interface ApiResponse<T> {
    data: T;
    message: string;
    success: boolean;
}

function Roles() {
    const [roles, setRoles] = useState<Role[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    // Create modal
    const [showCreate, setShowCreate] = useState(false);
    const [newName, setNewName] = useState("");
    const [newDescription, setNewDescription] = useState("");
    const [saving, setSaving] = useState(false);

    // Edit modal
    const [editingRole, setEditingRole] = useState<Role | null>(null);
    const [editName, setEditName] = useState("");
    const [editDescription, setEditDescription] = useState("");
    const [editSaving, setEditSaving] = useState(false);

    const fetchRoles = async () => {
        try {
            setLoading(true);
            const response = await RestService.get<ApiResponse<Role[]>>("/api/role");
            setRoles(response.data);
        } catch {
            setError("Failed to load roles.");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchRoles();
    }, []);

    const handleCreate = async () => {
        if (!newName.trim()) return;
        setSaving(true);
        try {
            await RestService.post("/api/role", { name: newName, description: newDescription });
            setNewName("");
            setNewDescription("");
            setShowCreate(false);
            await fetchRoles();
        } catch {
            setError("Failed to create role.");
        } finally {
            setSaving(false);
        }
    };

    const openEdit = (role: Role) => {
        setEditingRole(role);
        setEditName(role.name);
        setEditDescription(role.description);
    };

    const handleEdit = async () => {
        if (!editingRole || !editName.trim()) return;
        setEditSaving(true);
        try {
            await RestService.put(`/api/role/${editingRole.id}`, {
                ...editingRole,
                name: editName,
                description: editDescription,
            });
            setEditingRole(null);
            await fetchRoles();
        } catch {
            setError("Failed to update role.");
        } finally {
            setEditSaving(false);
        }
    };

    const handleToggleActive = async (role: Role) => {
        try {
            await RestService.put(`/api/role/${role.id}`, { ...role, isActive: !role.isActive });
            await fetchRoles();
        } catch {
            setError("Failed to update role.");
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
                        <button onClick={() => setError(null)} className="font-bold hover:text-red-800 dark:hover:text-red-200">✕</button>
                    </div>
                )}

                {/* Header */}
                <div className="mb-6 flex items-center justify-between">
                    <div>
                        <h1 className="text-2xl font-semibold text-gray-800 dark:text-white">Roles</h1>
                        <p className="text-sm text-gray-500 dark:text-gray-400 mt-1">Manage roles for your company</p>
                    </div>
                    <button
                        onClick={() => setShowCreate(true)}
                        className="flex items-center gap-2 bg-blue-600 hover:bg-blue-700 text-white text-sm font-medium px-4 py-2 rounded-xl transition-colors">
                        <svg xmlns="http://www.w3.org/2000/svg" className="h-4 w-4" fill="none"
                             viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2}>
                            <path strokeLinecap="round" strokeLinejoin="round" d="M12 4v16m8-8H4"/>
                        </svg>
                        New Role
                    </button>
                </div>

                {/* Roles list */}
                <div className="bg-white dark:bg-gray-900 rounded-2xl shadow-sm border border-gray-100 dark:border-gray-700 divide-y divide-gray-50 dark:divide-gray-800">
                    {roles.length === 0 ? (
                        <p className="text-sm text-gray-400 dark:text-gray-500 p-6">No roles found.</p>
                    ) : roles.map((role) => (
                        <div key={role.id} className="flex items-center justify-between px-6 py-4">
                            <div className="flex items-center gap-4">
                                <div className="h-9 w-9 rounded-xl bg-blue-50 dark:bg-blue-900/30 text-blue-600 dark:text-blue-400 flex items-center justify-center font-bold text-sm">
                                    {role.name[0].toUpperCase()}
                                </div>
                                <div>
                                    <p className="text-sm font-semibold text-gray-800 dark:text-white">{role.name}</p>
                                    <p className="text-xs text-gray-400 dark:text-gray-500">{role.description || "No description"}</p>
                                </div>
                            </div>
                            <div className="flex items-center gap-2">
                                <span className={`text-xs px-2 py-0.5 rounded-full font-medium ${role.isActive ? "bg-green-50 dark:bg-green-900/30 text-green-600 dark:text-green-400" : "bg-red-50 dark:bg-red-900/30 text-red-500 dark:text-red-400"}`}>
                                    {role.isActive ? "Active" : "Inactive"}
                                </span>
                                {/* Edit button */}
                                <button
                                    onClick={() => openEdit(role)}
                                    className="text-xs px-3 py-1 rounded-lg border border-gray-200 dark:border-gray-700 text-gray-500 dark:text-gray-400 hover:bg-gray-50 dark:hover:bg-gray-800 transition-colors">
                                    Edit
                                </button>
                                {/* Toggle active */}
                                <button
                                    onClick={() => handleToggleActive(role)}
                                    className="text-xs px-3 py-1 rounded-lg border border-gray-200 dark:border-gray-700 text-gray-500 dark:text-gray-400 hover:bg-gray-50 dark:hover:bg-gray-800 transition-colors">
                                    {role.isActive ? "Deactivate" : "Activate"}
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
                        <h2 className="text-lg font-semibold text-gray-800 dark:text-white mb-4">New Role</h2>
                        <div className="flex flex-col gap-4">
                            <div className="flex flex-col gap-1">
                                <label className="text-sm font-medium text-gray-600 dark:text-gray-400">Name</label>
                                <input
                                    type="text"
                                    value={newName}
                                    onChange={(e) => setNewName(e.target.value)}
                                    placeholder="e.g. Admin"
                                    className="border border-gray-300 dark:border-gray-700 bg-white dark:bg-gray-800 text-gray-800 dark:text-white rounded-lg px-4 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 transition"
                                />
                            </div>
                            <div className="flex flex-col gap-1">
                                <label className="text-sm font-medium text-gray-600 dark:text-gray-400">Description</label>
                                <input
                                    type="text"
                                    value={newDescription}
                                    onChange={(e) => setNewDescription(e.target.value)}
                                    placeholder="Optional description"
                                    className="border border-gray-300 dark:border-gray-700 bg-white dark:bg-gray-800 text-gray-800 dark:text-white rounded-lg px-4 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 transition"
                                />
                            </div>
                        </div>
                        <div className="flex justify-end gap-2 mt-6">
                            <button
                                onClick={() => { setShowCreate(false); setNewName(""); setNewDescription(""); }}
                                className="px-4 py-2 text-sm rounded-xl border border-gray-200 dark:border-gray-700 text-gray-500 dark:text-gray-400 hover:bg-gray-50 dark:hover:bg-gray-800 transition-colors">
                                Cancel
                            </button>
                            <button
                                onClick={handleCreate}
                                disabled={saving || !newName.trim()}
                                className="px-4 py-2 text-sm rounded-xl bg-blue-600 hover:bg-blue-700 disabled:opacity-50 text-white font-medium transition-colors">
                                {saving ? "Saving..." : "Create"}
                            </button>
                        </div>
                    </div>
                </div>
            )}

            {/* Edit modal */}
            {editingRole && (
                <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/40 backdrop-blur-sm">
                    <div className="bg-white dark:bg-gray-900 rounded-2xl shadow-xl border border-gray-100 dark:border-gray-700 p-6 w-full max-w-md">
                        <h2 className="text-lg font-semibold text-gray-800 dark:text-white mb-4">Edit Role</h2>
                        <div className="flex flex-col gap-4">
                            <div className="flex flex-col gap-1">
                                <label className="text-sm font-medium text-gray-600 dark:text-gray-400">Name</label>
                                <input
                                    type="text"
                                    value={editName}
                                    onChange={(e) => setEditName(e.target.value)}
                                    className="border border-gray-300 dark:border-gray-700 bg-white dark:bg-gray-800 text-gray-800 dark:text-white rounded-lg px-4 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 transition"
                                />
                            </div>
                            <div className="flex flex-col gap-1">
                                <label className="text-sm font-medium text-gray-600 dark:text-gray-400">Description</label>
                                <input
                                    type="text"
                                    value={editDescription}
                                    onChange={(e) => setEditDescription(e.target.value)}
                                    className="border border-gray-300 dark:border-gray-700 bg-white dark:bg-gray-800 text-gray-800 dark:text-white rounded-lg px-4 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 transition"
                                />
                            </div>
                        </div>
                        <div className="flex justify-end gap-2 mt-6">
                            <button
                                onClick={() => setEditingRole(null)}
                                className="px-4 py-2 text-sm rounded-xl border border-gray-200 dark:border-gray-700 text-gray-500 dark:text-gray-400 hover:bg-gray-50 dark:hover:bg-gray-800 transition-colors">
                                Cancel
                            </button>
                            <button
                                onClick={handleEdit}
                                disabled={editSaving || !editName.trim()}
                                className="px-4 py-2 text-sm rounded-xl bg-blue-600 hover:bg-blue-700 disabled:opacity-50 text-white font-medium transition-colors">
                                {editSaving ? "Saving..." : "Save changes"}
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}

export default Roles;