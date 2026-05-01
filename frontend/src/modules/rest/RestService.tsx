const BASE_URL = import.meta.env.VITE_API_URL;
type HttpMethod = "GET" | "POST" | "DELETE" | "PUT" | "PATCH";

async function request<T>(url: string, method: HttpMethod, body?: unknown): Promise<T> {
    const token = localStorage.getItem("session_token");
    const validToken = token && token !== "undefined" && token !== "null" ? token : null;

    const response = await fetch(`${BASE_URL}${url}`, {
        method,
        headers: {
            "Content-Type": "application/json",
            ...(validToken ? { Authorization: `Bearer ${validToken}` } : {}),
        },
        body: body ? JSON.stringify(body) : undefined,
    });

    const data = await response.json();

    if (!response.ok) {
        throw new Error(data.message ?? `HTTP error! status: ${response.status}`);
    }

    return data;
}

export const RestService = {
    get: <T,>(url: string): Promise<T> => request<T>(url, "GET"),
    post: <T, B>(url: string, body: B): Promise<T> => request<T>(url, "POST", body),
    delete: <T,>(url: string): Promise<T> => request<T>(url, "DELETE"),
    put: <T, B>(url: string, body: B): Promise<T> => request<T>(url, "PUT", body),
};