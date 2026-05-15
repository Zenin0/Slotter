import {useEffect, useState} from "react";
import {RestService} from "../rest/RestService.tsx";
import {useTranslation} from "react-i18next";
import {useApp} from "../shared/appcontext/AppContext";


interface Customer {
    id: string;
    name: string;
    email: string;
    phone: string;
}

interface ApiResponse<T> {
    data: T;
    message: string;
    success: boolean;
}

function Customers() {
    const {t} = useTranslation();

    const [customers, setCustomers] = useState<Customer[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    // Create modal
    const [showCreate, setShowCreate] = useState(false);
    const [newName, setNewName] = useState("");
    const [newEmail, setNewEmail] = useState("");
    const [newPhone, setNewPhone] = useState("");
    const [saving, setSaving] = useState(false);

    // Edit modal
    const [editingCustomer, setEditingCustomer] = useState<Customer | null>(null);
    const [editName, setEditName] = useState("");
    const [editEmail, setEditEmail] = useState("");
    const [editPhone, setEditPhone] = useState("");
    const [editSaving, setEditSaving] = useState(false);

    // Delete confirm modal
    const [deletingCustomer, setDeletingCustomer] = useState<Customer | null>(null);
    const [deleteLoading, setDeleteLoading] = useState(false);

    const {company} = useApp();

    const fetchCustomers = async () => {
        try {
            setLoading(true);

            const response = await RestService.get<ApiResponse<Customer[]>>(`/api/v1/customer/${company?.slug}`);
            setCustomers(response.data);
        } catch {
            setError(t("failedLoadCustomers"));
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchCustomers();
    }, []);

    const handleCreate = async () => {
        if (!newName.trim()) return;
        setSaving(true);

        try {
            await RestService.post(`/api/v1/customer/${company?.slug}`, {
                name: newName,
                email: newEmail,
                phone: newPhone,
                company: company?.id
            });

            setNewName("");
            setNewEmail("");
            setNewPhone("");
            setShowCreate(false);
            await fetchCustomers();
        } catch {
            setError(t("failedCreateCustomer"));
        } finally {
            setSaving(false);
        }
    };

    const openEdit = (customer: Customer) => {
        setEditingCustomer(customer);
        setEditName(customer.name);
        setEditEmail(customer.email);
        setEditPhone(customer.phone);
    };

    const handleEdit = async () => {
        if (!editingCustomer || !editName.trim()) return;
        setEditSaving(true);

        try {
            await RestService.put(`/api/v1/customer//+${company?.slug}/${editingCustomer.id}`, {
                ...editingCustomer,
                name: editName,
                email: editEmail,
                phone: editPhone,
            });

            setEditingCustomer(null);
            await fetchCustomers();
        } catch {
            setError(t("failedUpdateCustomer"));
        } finally {
            setEditSaving(false);
        }
    };

    const handleDelete = async () => {
        if (!deletingCustomer) return;
        setDeleteLoading(true);

        try {
            await RestService.delete(`/api/v1/customer/${company?.slug}/${deletingCustomer.id}`, null);
            setDeletingCustomer(null);
            await fetchCustomers();
        } catch {
            setError(t("failedDeleteCustomer"));
        } finally {
            setDeleteLoading(false);
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
                            {t("customers")}
                        </h1>
                        <p className="text-sm text-gray-500 dark:text-gray-400 mt-1">
                            {t("manageCustomers")}
                        </p>
                    </div>

                    <button
                        onClick={() => setShowCreate(true)}
                        className="flex items-center gap-2 bg-blue-600 hover:bg-blue-700 text-white text-sm font-medium px-4 py-2 rounded-xl transition-colors"
                    >
                        <svg xmlns="http://www.w3.org/2000/svg" className="h-4 w-4" fill="none"
                             viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2}>
                            <path strokeLinecap="round" strokeLinejoin="round" d="M12 4v16m8-8H4"/>
                        </svg>
                        {t("newCustomer")}
                    </button>
                </div>

                {/* Customers list */}
                <div className="bg-white dark:bg-gray-900 rounded-2xl shadow-sm border border-gray-100 dark:border-gray-700 divide-y divide-gray-50 dark:divide-gray-800">

                    {customers.length === 0 ? (
                        <p className="text-sm text-gray-400 dark:text-gray-500 p-6">
                            {t("noCustomers")}
                        </p>
                    ) : customers.map((customer) => (
                        <div key={customer.id} className="flex items-center justify-between px-6 py-4">

                            <div className="flex items-center gap-4">
                                <div className="h-9 w-9 rounded-xl bg-blue-50 dark:bg-blue-900/30 text-blue-600 dark:text-blue-400 flex items-center justify-center font-bold text-sm">
                                    {customer.name[0].toUpperCase()}
                                </div>

                                <div>
                                    <p className="text-sm font-semibold text-gray-800 dark:text-white">
                                        {customer.name}
                                    </p>
                                    <p className="text-xs text-gray-400 dark:text-gray-500">
                                        {customer.email || t("noEmail")}
                                        {customer.phone ? ` · ${customer.phone}` : ""}
                                    </p>
                                </div>
                            </div>

                            <div className="flex items-center gap-2">
                                <button
                                    onClick={() => openEdit(customer)}
                                    className="text-xs px-3 py-1 rounded-lg border border-gray-200 dark:border-gray-700 text-gray-500 dark:text-gray-400 hover:bg-gray-50 dark:hover:bg-gray-800 transition-colors"
                                >
                                    {t("edit")}
                                </button>

                                <button
                                    onClick={() => setDeletingCustomer(customer)}
                                    className="text-xs px-3 py-1 rounded-lg border border-red-200 dark:border-red-800 text-red-500 dark:text-red-400 hover:bg-red-50 dark:hover:bg-red-900/20 transition-colors"
                                >
                                    {t("delete")}
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
                            {t("newCustomer")}
                        </h2>

                        <div className="flex flex-col gap-4">
                            <div className="flex flex-col gap-1">
                                <label className="text-sm font-medium text-gray-600 dark:text-gray-400">
                                    {t("name")}
                                </label>
                                <input
                                    type="text"
                                    value={newName}
                                    onChange={(e) => setNewName(e.target.value)}
                                    className="border border-gray-300 dark:border-gray-700 bg-white dark:bg-gray-800 text-gray-800 dark:text-white rounded-lg px-4 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 transition"
                                />
                            </div>

                            <div className="flex flex-col gap-1">
                                <label className="text-sm font-medium text-gray-600 dark:text-gray-400">
                                    {t("email")}
                                </label>
                                <input
                                    type="email"
                                    value={newEmail}
                                    onChange={(e) => setNewEmail(e.target.value)}
                                    placeholder={t("emailPlaceholder")}
                                    className="border border-gray-300 dark:border-gray-700 bg-white dark:bg-gray-800 text-gray-800 dark:text-white rounded-lg px-4 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 transition"
                                />
                            </div>

                            <div className="flex flex-col gap-1">
                                <label className="text-sm font-medium text-gray-600 dark:text-gray-400">
                                    {t("phone")}
                                </label>
                                <input
                                    type="tel"
                                    value={newPhone}
                                    onChange={(e) => setNewPhone(e.target.value)}
                                    placeholder={t("optionalPhone")}
                                    className="border border-gray-300 dark:border-gray-700 bg-white dark:bg-gray-800 text-gray-800 dark:text-white rounded-lg px-4 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 transition"
                                />
                            </div>
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
                                disabled={saving || !newName.trim()}
                                className="px-4 py-2 text-sm rounded-xl bg-blue-600 hover:bg-blue-700 disabled:opacity-50 text-white font-medium transition-colors"
                            >
                                {saving ? t("saving") : t("create")}
                            </button>
                        </div>
                    </div>
                </div>
            )}

            {/* Edit modal */}
            {editingCustomer && (
                <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/40 backdrop-blur-sm">
                    <div className="bg-white dark:bg-gray-900 rounded-2xl shadow-xl border border-gray-100 dark:border-gray-700 p-6 w-full max-w-md">

                        <h2 className="text-lg font-semibold text-gray-800 dark:text-white mb-4">
                            {t("editCustomer")}
                        </h2>

                        <div className="flex flex-col gap-4">
                            <div className="flex flex-col gap-1">
                                <label className="text-sm font-medium text-gray-600 dark:text-gray-400">
                                    {t("name")}
                                </label>
                                <input
                                    type="text"
                                    value={editName}
                                    onChange={(e) => setEditName(e.target.value)}
                                    className="border border-gray-300 dark:border-gray-700 bg-white dark:bg-gray-800 text-gray-800 dark:text-white rounded-lg px-4 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 transition"
                                />
                            </div>

                            <div className="flex flex-col gap-1">
                                <label className="text-sm font-medium text-gray-600 dark:text-gray-400">
                                    {t("email")}
                                </label>
                                <input
                                    type="email"
                                    value={editEmail}
                                    onChange={(e) => setEditEmail(e.target.value)}
                                    className="border border-gray-300 dark:border-gray-700 bg-white dark:bg-gray-800 text-gray-800 dark:text-white rounded-lg px-4 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 transition"
                                />
                            </div>

                            <div className="flex flex-col gap-1">
                                <label className="text-sm font-medium text-gray-600 dark:text-gray-400">
                                    {t("phone")}
                                </label>
                                <input
                                    type="tel"
                                    value={editPhone}
                                    onChange={(e) => setEditPhone(e.target.value)}
                                    className="border border-gray-300 dark:border-gray-700 bg-white dark:bg-gray-800 text-gray-800 dark:text-white rounded-lg px-4 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 transition"
                                />
                            </div>
                        </div>

                        <div className="flex justify-end gap-2 mt-6">
                            <button
                                onClick={() => setEditingCustomer(null)}
                                className="px-4 py-2 text-sm rounded-xl border border-gray-200 dark:border-gray-700 text-gray-500 dark:text-gray-400 hover:bg-gray-50 dark:hover:bg-gray-800 transition-colors"
                            >
                                {t("cancel")}
                            </button>

                            <button
                                onClick={handleEdit}
                                disabled={editSaving || !editName.trim()}
                                className="px-4 py-2 text-sm rounded-xl bg-blue-600 hover:bg-blue-700 disabled:opacity-50 text-white font-medium transition-colors"
                            >
                                {editSaving ? t("saving") : t("save")}
                            </button>
                        </div>
                    </div>
                </div>
            )}

            {/* Delete confirm modal */}
            {deletingCustomer && (
                <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/40 backdrop-blur-sm">
                    <div className="bg-white dark:bg-gray-900 rounded-2xl shadow-xl border border-gray-100 dark:border-gray-700 p-6 w-full max-w-sm">

                        <h2 className="text-lg font-semibold text-gray-800 dark:text-white mb-2">
                            {t("deleteCustomer")}
                        </h2>
                        <p className="text-sm text-gray-500 dark:text-gray-400 mb-6">
                            {t("deleteCustomerConfirm", {name: deletingCustomer.name})}
                        </p>

                        <div className="flex justify-end gap-2">
                            <button
                                onClick={() => setDeletingCustomer(null)}
                                className="px-4 py-2 text-sm rounded-xl border border-gray-200 dark:border-gray-700 text-gray-500 dark:text-gray-400 hover:bg-gray-50 dark:hover:bg-gray-800 transition-colors"
                            >
                                {t("cancel")}
                            </button>

                            <button
                                onClick={handleDelete}
                                disabled={deleteLoading}
                                className="px-4 py-2 text-sm rounded-xl bg-red-600 hover:bg-red-700 disabled:opacity-50 text-white font-medium transition-colors"
                            >
                                {deleteLoading ? t("deleting") : t("delete")}
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}

export default Customers;