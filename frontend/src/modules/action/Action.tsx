import { useEffect, useState } from "react";
import { RestService } from "../rest/RestService.tsx";
import { useApp } from "../shared/appcontext/AppContext.tsx";

interface Role {
    id: string;
    name: string;
    isActive: boolean;
}

interface Action {
    uuid: string;
    name: string;
    description: string;
    roles: Role[];
}

interface ApiResponse<T> {
    data: T;
    message: string;
    success: boolean;
}

function Actions() {
    const { company } = useApp();
    const [actions, setActions] = useState<Action[]>([]);
    const [allRoles, setAllRoles] = useState<Role[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    // Create modal
    const [showCreate, setShowCreate] = useState(false);
    const [newName, setNewName] = useState("");
    const [newDescription, setNewDescription] = useState("");
    const [newRoleIds, setNewRoleIds] = useState<string[]>([]);
    const [saving, setSaving] = useState(false);

    // Edit modal
    const [editingAction, setEditingAction] = useState<Action | null>(null);
    const [editName, setEditName] = useState("");
    const [editDescription, setEditDescription] = useState("");
    const [editRoleIds, setEditRoleIds] = useState<string[]>([]);
    const [editSaving, setEditSaving] = useState(false);

    const fetchActions = async () => {
        try {
            setLoading(true);
            const response = await RestService.get<ApiResponse<Action[]>>("/api/v1/action");
            setActions(response.data);
        } catch {
            setError("Failed to load actions.");
        } finally {
            setLoading(false);
        }
    };

    const fetchRoles = async () => {
        try {
            const response = await RestService.get<ApiResponse<Role[]>>("/api/v1/role");
            setAllRoles(response.data);
        } catch {
            setError("Failed to load roles.");
        }
    };

    useEffect(() => {
        fetchActions();
        fetchRoles();
    }, []);

    const toggleRoleId = (id: string, selected: string[], setSelected: (ids: string[]) => void) => {
        setSelected(selected.includes(id) ? selected.filter(r => r !== id) : [...selected, id]);
    };

    const handleCreate = async () => {
        if (!newName.trim()) return;
        setSaving(true);
        try {
            await RestService.post("/api/v1/action", {
                name: newName,
                description: newDescription,
                roleIds: newRoleIds,
            });
            setNewName("");
            setNewDescription("");
            setNewRoleIds([]);
            setShowCreate(false);
            await fetchActions();
        } catch {
            setError("Failed to create action.");
        } finally {
            setSaving(false);
        }
    };

    const openEdit = (action: Action) => {
        setEditingAction(action);
        setEditName(action.name);
        setEditDescription(action.description);
        setEditRoleIds(action.roles.map(r => r.id));
    };

    const handleEdit = async () => {
        if (!editingAction || !editName.trim()) return;
        setEditSaving(true);
        try {
            await RestService.put(`/api/v1/action/${editingAction.id}`, {
                name: editName,
                description: editDescription,
                roleIds: editRoleIds,
            });
            setEditingAction(null);
            await fetchActions();
        } catch {
            setError("Failed to update action.");
        } finally {
            setEditSaving(false);
        }
    };

    if (loading) return null;

    const RoleSelector = ({
                              selected,
                              setSelected,
                          }: {
        selected: string[];
        setSelected: (ids: string[]) => void;
    }) => (
        <div className="flex flex-wrap gap-2">
            {allRoles.map(role => {
                const active = selected.includes(role.id);
                return (
                    <button
                        key={role.id}
                        type="button"
                        onClick={() => toggleRoleId(role.id, selected, setSelected)}
                        className={`text-xs px-3 py-1 rounded-full border font-medium transition-colors ${
                            active
                                ? "bg-blue-600 border-blue-600 text-white"
                                : "border-gray-300 dark:border-gray-600 text-gray-500 dark:text-gray-400 hover:border-blue-400 hover:text-blue-500"
                        }`}>
                        {role.name}
                    </button>
                );
            })}
            {allRoles.length === 0 && (
                <p className="text-xs text-gray-400 dark:text-gray-500">No roles available.</p>
            )}
        </div>
    );

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
                        <h1 className="text-2xl font-semibold text-gray-800 dark:text-white">Actions</h1>
                        <p className="text-sm text-gray-500 dark:text-gray-400 mt-1">Manage actions for {company?.name}</p>
                    </div>
                    <div className="flex items-center gap-3">
                        <span className="text-xs text-gray-400 dark:text-gray-500 bg-white dark:bg-gray-900 border border-gray-200 dark:border-gray-700 px-3 py-1.5 rounded-xl">
                            {actions.length} action{actions.length !== 1 ? "s" : ""}
                        </span>
                        <button
                            onClick={() => setShowCreate(true)}
                            className="flex items-center gap-2 bg-blue-600 hover:bg-blue-700 text-white text-sm font-medium px-4 py-2 rounded-xl transition-colors">
                            <svg xmlns="http://www.w3.org/2000/svg" className="h-4 w-4" fill="none"
                                 viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2}>
                                <path strokeLinecap="round" strokeLinejoin="round" d="M12 4v16m8-8H4"/>
                            </svg>
                            New Action
                        </button>
                    </div>
                </div>

                {/* Actions list */}
                <div className="bg-white dark:bg-gray-900 rounded-2xl shadow-sm border border-gray-100 dark:border-gray-700 divide-y divide-gray-50 dark:divide-gray-800">
                    {actions.length === 0 ? (
                        <p className="text-sm text-gray-400 dark:text-gray-500 p-6">No actions found.</p>
                    ) : actions.map((action) => (
                        <div key={action.id} className="flex items-center justify-between px-6 py-4">
                            <div className="flex items-center gap-4">
                                <div className="h-9 w-9 rounded-xl bg-blue-50 dark:bg-blue-900/30 text-blue-600 dark:text-blue-400 flex items-center justify-center font-bold text-sm">
                                    {action.name[0].toUpperCase()}
                                </div>
                                <div>
                                    <p className="text-sm font-semibold text-gray-800 dark:text-white">{action.name}</p>
                                    <p className="text-xs text-gray-400 dark:text-gray-500">{action.description || "No description"}</p>
                                    {action.roles.length > 0 && (
                                        <div className="flex flex-wrap gap-1 mt-1">
                                            {action.roles.map(r => (
                                                <span key={r.id} className="text-xs px-2 py-0.5 rounded-full bg-gray-100 dark:bg-gray-800 text-gray-500 dark:text-gray-400">
                                                    {r.name}
                                                </span>
                                            ))}
                                        </div>
                                    )}
                                </div>
                            </div>
                            <div className="flex items-center gap-2">
                                <button
                                    onClick={() => openEdit(action)}
                                    className="text-xs px-3 py-1 rounded-lg border border-gray-200 dark:border-gray-700 text-gray-500 dark:text-gray-400 hover:bg-gray-50 dark:hover:bg-gray-800 transition-colors">
                                    Edit
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
                        <h2 className="text-lg font-semibold text-gray-800 dark:text-white mb-4">New Action</h2>
                        <div className="flex flex-col gap-4">
                            {[
                                { label: "Name", value: newName, set: setNewName, type: "text", placeholder: "e.g. Send Email" },
                                { label: "Description", value: newDescription, set: setNewDescription, type: "text", placeholder: "Optional description" },
                            ].map(({ label, value, set, type, placeholder }) => (
                                <div key={label} className="flex flex-col gap-1">
                                    <label className="text-sm font-medium text-gray-600 dark:text-gray-400">{label}</label>
                                    <input
                                        type={type}
                                        value={value}
                                        onChange={(e) => set(e.target.value)}
                                        placeholder={placeholder}
                                        className="border border-gray-300 dark:border-gray-700 bg-white dark:bg-gray-800 text-gray-800 dark:text-white rounded-lg px-4 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 transition placeholder-gray-400 dark:placeholder-gray-600"
                                    />
                                </div>
                            ))}
                            <div className="flex flex-col gap-2">
                                <label className="text-sm font-medium text-gray-600 dark:text-gray-400">Roles</label>
                                <RoleSelector selected={newRoleIds} setSelected={setNewRoleIds} />
                            </div>
                        </div>
                        <div className="flex justify-end gap-2 mt-6">
                            <button
                                onClick={() => { setShowCreate(false); setNewName(""); setNewDescription(""); setNewRoleIds([]); }}
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
            {editingAction && (
                <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/40 backdrop-blur-sm">
                    <div className="bg-white dark:bg-gray-900 rounded-2xl shadow-xl border border-gray-100 dark:border-gray-700 p-6 w-full max-w-md">
                        <h2 className="text-lg font-semibold text-gray-800 dark:text-white mb-4">Edit Action</h2>
                        <div className="flex flex-col gap-4">
                            {[
                                { label: "Name", value: editName, set: setEditName, type: "text", placeholder: "e.g. Send Email" },
                                { label: "Description", value: editDescription, set: setEditDescription, type: "text", placeholder: "Optional description" },
                            ].map(({ label, value, set, type, placeholder }) => (
                                <div key={label} className="flex flex-col gap-1">
                                    <label className="text-sm font-medium text-gray-600 dark:text-gray-400">{label}</label>
                                    <input
                                        type={type}
                                        value={value}
                                        onChange={(e) => set(e.target.value)}
                                        placeholder={placeholder}
                                        className="border border-gray-300 dark:border-gray-700 bg-white dark:bg-gray-800 text-gray-800 dark:text-white rounded-lg px-4 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 transition placeholder-gray-400 dark:placeholder-gray-600"
                                    />
                                </div>
                            ))}
                            <div className="flex flex-col gap-2">3
                                <label className="text-sm font-medium text-gray-600 dark:text-gray-400">Roles</label>
                                <RoleSelector selected={editRoleIds} setSelected={setEditRoleIds} />
                            </div>
                        </div>
                        <div className="flex justify-end gap-2 mt-6">
                            <button
                                onClick={() => setEditingAction(null)}
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

export default Actions;