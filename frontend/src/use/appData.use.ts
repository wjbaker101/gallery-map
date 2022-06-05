import { readonly, ref } from 'vue';

import { authApiClient } from '@/api/auth/authApiClient';
import { ILogInRequest } from '@/api/auth/type/LogIn.type';

import { IAlbum } from '@/model/album.model';
import { albumsApiClient } from '@/api/albums/albumsApiClient';
import { ICreateAlbumRequest } from '@/api/albums/type/CreateAlbum.type';

import { IUploadPhotoRequest } from '@/api/albums/type/UploadPhoto.type';
import { IPhoto } from '@/model/photo.model';

const albums = ref<Array<IAlbum> | null>(null);
(async () => {
    const result = await albumsApiClient.getAlbums();
    albums.value = result;
})();

const authLoginToken = ref<string | null>(null);

export const useAppData = function () {
    return {

        auth: {
            loginToken: readonly(authLoginToken),

            async logIn(request: ILogInRequest): Promise<void> {
                const result = await authApiClient.logIn(request);
                authLoginToken.value = result;
            },
        },

        albums: {
            value: readonly(albums),

            async add(request: ICreateAlbumRequest): Promise<IAlbum> {
                const result = await albumsApiClient.createAlbum(request);

                albums.value?.push(result);

                return result;
            },
        },

        photos: {
            async add(albumId: number, request: IUploadPhotoRequest): Promise<IPhoto> {
                const result = await albumsApiClient.uploadPhoto(albumId, request);

                const album = albums.value?.find(x => x.id == albumId);
                album?.photos.push(result);

                return result;
            },

            async delete(albumId: number, photoId: number): Promise<void> {
                const result = await albumsApiClient.deletePhoto(albumId, photoId);

                const album = albums.value?.find(x => x.id == albumId);
                if (!album)
                    return;

                album.photos = album.photos.filter(x => x.id !== photoId);
            },

            async reorder(albumId: number, photos: Record<number, number>): Promise<void> {
                const reorderPhotosResult = await albumsApiClient.reorderPhotos(albumId, {
                    photos,
                });

                const getAlbumsResult = await albumsApiClient.getAlbums();
                albums.value = getAlbumsResult;
            },
        },

    };
};