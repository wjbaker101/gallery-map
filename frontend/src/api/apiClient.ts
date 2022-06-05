import axios, { AxiosRequestConfig } from 'axios';

const api = axios.create({
    baseURL: '/api',
});

export const apiClient = {

    async get<T>(url: string, options?: AxiosRequestConfig) {
        return await api.get<T>(url, options);
    },

    async post<T>(url: string, request: any, options?: AxiosRequestConfig) {
        return await api.post<T>(url, request, options);
    },

    async put<T>(url: string, request: any, options?: AxiosRequestConfig) {
        return await api.put<T>(url, request, options);
    },

    async delete<T>(url: string, options?: AxiosRequestConfig) {
        return await api.delete<T>(url, options);
    },
};