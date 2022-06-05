import axios, { AxiosRequestConfig } from 'axios';

const api = axios.create({
    baseURL: '/api',
});

export const apiClient = {

    async get<T>(url: string) {
        return await api.get<T>(url);
    },

    async post<T>(url: string, request: any, options?: AxiosRequestConfig) {
        return await api.post<T>(url, request, options);
    },

    async put<T>(url: string, request: any) {
        return await api.put<T>(url, request);
    },

    async delete<T>(url: string) {
        return await api.delete<T>(url);
    },
};