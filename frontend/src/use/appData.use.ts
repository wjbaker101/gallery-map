import { readonly, ref } from 'vue';

import { useCache } from '@/use/cache.use';

import { authApiClient } from '@/api/auth/authApiClient';
import { ILogInRequest } from '@/api/auth/type/LogIn.type';

import { IAlbum } from '@/model/album.model';
import { albumsApiClient } from '@/api/albums/albumsApiClient';
import { ICreateAlbumRequest } from '@/api/albums/type/CreateAlbum.type';

import { IPhoto } from '@/model/photo.model';
import { IUploadPhotoRequest } from '@/api/albums/type/UploadPhoto.type';

const cache = useCache();

const albums = ref<Array<IAlbum> | null>(null);
(async () => {
    const result = await albumsApiClient.getAlbums();
    albums.value = result;
})();

const authLoginToken = ref<string | null>(cache.get<string>('auth-login-token'));

export const useAppData = function () {
    return {

        auth: {
            loginToken: readonly(authLoginToken),

            async logIn(request: ILogInRequest): Promise<void> {
                const result = await authApiClient.logIn(request);
                authLoginToken.value = result;

                cache.set('auth-login-token', authLoginToken.value, 2 * 24 * 60 * 60 * 1000);
            },

            logOut() {
                authLoginToken.value = null;
                cache.unset('auth-login-token');
            },
        },

        albums: {
            value: readonly(albums),

            async add(request: ICreateAlbumRequest): Promise<IAlbum> {
                if (authLoginToken.value === null)
                    throw new Error('Login token of an admin user is required for this request.');

                const result = await albumsApiClient.createAlbum(request, authLoginToken.value);

                albums.value?.push(result);

                return result;
            },

            async delete(id: number) {
                if (authLoginToken.value === null)
                    throw new Error('Login token of an admin user is required for this request.');

                const result = await albumsApiClient.deleteAlbum(id, authLoginToken.value);

                albums.value = albums.value?.filter(x => x.id !== id) ?? null;
            },
        },

        photos: {
            async add(albumId: number, request: IUploadPhotoRequest): Promise<IPhoto> {
                if (authLoginToken.value === null)
                    throw new Error('Login token of an admin user is required for this request.');

                const result = await albumsApiClient.uploadPhoto(albumId, request, authLoginToken.value);

                const album = albums.value?.find(x => x.id == albumId);
                album?.photos.push(result);

                return result;
            },

            async delete(albumId: number, photoId: number): Promise<void> {
                if (authLoginToken.value === null)
                    throw new Error('Login token of an admin user is required for this request.');

                const result = await albumsApiClient.deletePhoto(albumId, photoId, authLoginToken.value);

                const album = albums.value?.find(x => x.id == albumId);
                if (!album)
                    return;

                album.photos = album.photos.filter(x => x.id !== photoId);
            },

            async reorder(albumId: number, photos: Record<number, number>): Promise<void> {
                if (authLoginToken.value === null)
                    throw new Error('Login token of an admin user is required for this request.');

                const request = {
                    photos,
                };

                const reorderPhotosResult = await albumsApiClient.reorderPhotos(albumId, request, authLoginToken.value);

                const getAlbumsResult = await albumsApiClient.getAlbums();
                albums.value = getAlbumsResult;
            },
        },

    };
};