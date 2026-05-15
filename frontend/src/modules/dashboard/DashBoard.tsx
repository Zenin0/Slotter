import {useApp} from "../shared/appcontext/AppContext";
import {useTranslation} from "react-i18next";

function Dashboard() {
    const {user, company, loading} = useApp();
    const {t} = useTranslation();

    if (loading) return null;

    return (<div className="min-h-screen bg-gray-100 dark:bg-gray-950 p-8">
            <div className="max-w-5xl mx-auto">

                {/* Header */}
                <div className="mb-8 flex items-center justify-between">
                    <div>
                        <h1 className="text-2xl font-semibold text-gray-800 dark:text-white">
                            {t("welcomeBackName", {name: user?.username})}
                        </h1>

                        <p className="text-gray-500 dark:text-gray-400 text-sm mt-1">
                            {company?.name}
                        </p>
                    </div>

                    <div
                        className="flex items-center gap-3 bg-white dark:bg-gray-900 border border-gray-100 dark:border-gray-700 shadow-sm rounded-2xl px-4 py-2">
                        {company?.companyLogo ? (
                            <img src={company.companyLogo} alt="Company" className="h-6 w-6 object-contain"/>) : (<div
                                className="h-6 w-6 rounded bg-blue-100 dark:bg-blue-900 text-blue-600 dark:text-blue-300 text-xs font-bold flex items-center justify-center">
                                {company?.name?.[0]}
                            </div>)}

                        <span className="text-sm text-gray-600 dark:text-gray-300 font-medium">
                            {company?.name}
                        </span>

                        <span
                            className="text-xs text-gray-400 dark:text-gray-500 bg-gray-100 dark:bg-gray-800 px-2 py-0.5 rounded-full">
                            {company?.slug}
                        </span>
                    </div>
                </div>

                {/* User info cards */}
                <div className="grid grid-cols-1 sm:grid-cols-3 gap-4 mb-8">

                    <div
                        className="bg-white dark:bg-gray-900 rounded-2xl shadow-sm border border-gray-100 dark:border-gray-700 p-5 flex items-center gap-4">
                        <div>
                            <p className="text-xs text-gray-400 dark:text-gray-500">
                                {t("loggedInAs")}
                            </p>
                            <p className="text-sm font-semibold text-gray-800 dark:text-white">
                                {user?.username}
                            </p>
                            <p className="text-xs text-gray-400 dark:text-gray-500">
                                {user?.email}
                            </p>
                        </div>
                    </div>

                    <div
                        className="bg-white dark:bg-gray-900 rounded-2xl shadow-sm border border-gray-100 dark:border-gray-700 p-5 flex items-center gap-4">
                        <div
                            className="text-2xl p-3 rounded-xl bg-green-50 dark:bg-green-900/30 text-green-600 dark:text-green-400">
                            🏢
                        </div>
                        <div>
                            <p className="text-xs text-gray-400 dark:text-gray-500">
                                {t("company")}
                            </p>
                            <p className="text-sm font-semibold text-gray-800 dark:text-white">
                                {company?.name}
                            </p>
                            <p className="text-xs text-gray-400 dark:text-gray-500">
                                /{company?.slug}
                            </p>
                        </div>
                    </div>

                    <div
                        className="bg-white dark:bg-gray-900 rounded-2xl shadow-sm border border-gray-100 dark:border-gray-700 p-5 flex items-center gap-4">
                        <div
                            className={`text-2xl p-3 rounded-xl ${user?.isActive ? "bg-green-50 dark:bg-green-900/30 text-green-600 dark:text-green-400" : "bg-red-50 dark:bg-red-900/30 text-red-500 dark:text-red-400"}`}>
                            {user?.isActive ? "✅" : "❌"}
                        </div>

                        <div>
                            <p className="text-xs text-gray-400 dark:text-gray-500">
                                {t("accountStatus")}
                            </p>

                            <p className="text-sm font-semibold text-gray-800 dark:text-white">
                                {user?.isActive ? t("active") : t("inactive")}
                            </p>

                            <p className="text-xs text-gray-400 dark:text-gray-500">
                                ID: {user?.id.slice(0, 8)}...
                            </p>
                        </div>
                    </div>
                </div>

                {/* Company users */}
                <div
                    className="bg-white dark:bg-gray-900 rounded-2xl shadow-sm border border-gray-100 dark:border-gray-700 p-6">
                    <h2 className="text-base font-semibold text-gray-700 dark:text-gray-200 mb-4">
                        {t("companyUsers")}
                        <span
                            className="ml-2 text-xs text-gray-400 dark:text-gray-500 font-normal bg-gray-100 dark:bg-gray-800 px-2 py-0.5 rounded-full">
                            {company?.users?.length ?? 0}
                        </span>
                    </h2>

                    <div className="flex flex-col gap-2">
                        {company?.users?.length ? company.users.map((u) => (<div key={u.id}
                                                                                 className="flex items-center justify-between py-2 border-b border-gray-50 dark:border-gray-800 last:border-0">

                                <div className="flex items-center gap-3">
                                    <div
                                        className="h-8 w-8 rounded-full bg-gray-100 dark:bg-gray-800 flex items-center justify-center text-sm font-medium text-gray-600 dark:text-gray-300">
                                        {u.profileImage ? <img src={u.profileImage}
                                                               className="h-8 w-8 rounded-full object-cover"/> : u.username[0].toUpperCase()}
                                    </div>

                                    <div>
                                        <p className="text-sm font-medium text-gray-700 dark:text-gray-200">
                                            {u.username}
                                        </p>
                                        <p className="text-xs text-gray-400 dark:text-gray-500">
                                            {u.email}
                                        </p>
                                    </div>
                                </div>

                                <span
                                    className={`text-xs px-2 py-0.5 rounded-full font-medium ${u.isActive ? "bg-green-50 dark:bg-green-900/30 text-green-600 dark:text-green-400" : "bg-red-50 dark:bg-red-900/30 text-red-400"}`}>
                                    {u.isActive ? t("active") : t("inactive")}
                                </span>
                            </div>)) : (<p className="text-sm text-gray-400 dark:text-gray-500">
                                {t("noUsers")}
                            </p>)}
                    </div>
                </div>

            </div>
        </div>);
}

export default Dashboard;