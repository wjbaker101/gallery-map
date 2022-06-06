interface ICacheItem<T> {
    item: T;
    expiresAt: number;
}

export const useCache = function () {
    return {
        get<T>(key: string): T | null {
            const cache = localStorage.getItem(key);
            if (cache === null)
                return null;

            const item = JSON.parse(cache) as ICacheItem<T>;

            if (item.expiresAt < Date.now())
                return null;

            return item.item;
        },

        set(key: string, data: any, expiresIn: number = 0): void {
            const item: ICacheItem<any> = {
                item: data,
                expiresAt: Date.now() + expiresIn,
            };

            localStorage.setItem(key, JSON.stringify(item));
        },

        unset(key: string): void {
            localStorage.removeItem(key);
        },
    };
};