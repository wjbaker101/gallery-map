interface ICacheItem<T> {
    item: T;
    expiresAt: number;
}

type CacheKey = 'auth-login-token';

export const useCache = function () {
    return {
        get<T>(key: CacheKey): T | null {
            const cache = localStorage.getItem(key);
            if (cache === null)
                return null;

            const item = JSON.parse(cache) as ICacheItem<T>;

            if (item.expiresAt < Date.now())
                return null;

            return item.item;
        },

        set(key: CacheKey, data: any, expiresIn: number = 0): void {
            const item: ICacheItem<any> = {
                item: data,
                expiresAt: Date.now() + expiresIn,
            };

            localStorage.setItem(key, JSON.stringify(item));
        },

        unset(key: CacheKey): void {
            localStorage.removeItem(key);
        },
    };
};