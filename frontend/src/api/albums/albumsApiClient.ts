import { apiClient } from '@/api/apiClient';
import { IApiResponse } from '@/api/ApiResponse.type';

import { IAlbum } from '@/model/album.model';
import { IPhoto } from '@/model/photo.model';

import { IGetAlbumsResponse } from '@/api/albums/type/GetAlbums.type';
import { ICreateAlbumRequest, ICreateAlbumResponse } from '@/api/albums/type/CreateAlbum.type';
import { IUploadPhotoRequest, IUploadPhotoResponse } from '@/api/albums/type/UploadPhoto.type';
import { IReorderPhotosRequest } from '@/api/albums/type/ReorderPhotos.type';

export const albumsApiClient = {

    async getAlbums(): Promise<Array<IAlbum>> {
        const response = await apiClient
            .get<IApiResponse<IGetAlbumsResponse>>('/albums');

        const albums = response.data.result.albums;

        return albums.map(album => ({
            id: album.id,
            title: album.title,
            photos: album.photos.map(photo => ({
                id: photo.id,
                title: photo.title,
                description: photo.description,
                url: photo.url,
                thumbnailUrl: photo.thumbnailUrl,
                longitude: photo.longitude,
                latitude: photo.latitude,
            })),
        }));
    },

    async createAlbum(request: ICreateAlbumRequest, loginToken: string): Promise<IAlbum> {
        const response = await apiClient
            .post<IApiResponse<ICreateAlbumResponse>>('/album', request, {
                headers: {
                    'Authorization': `Bearer ${loginToken}`,
                },
            });

        const album = response.data.result.album;

        return {
            id: album.id,
            title: album.title,
            photos: album.photos.map(photo => ({
                id: photo.id,
                title: photo.title,
                description: photo.description,
                url: photo.url,
                thumbnailUrl: photo.thumbnailUrl,
                longitude: photo.longitude,
                latitude: photo.latitude,
            })),
        };
    },

    async deleteAlbum(id: number, loginToken: string): Promise<void> {
        const response = await apiClient
            .delete<IApiResponse<ICreateAlbumResponse>>(`/album/${id}`, {
                headers: {
                    'Authorization': `Bearer ${loginToken}`,
                },
            });
    },

    async uploadPhoto(albumId: number, request: IUploadPhotoRequest, loginToken: string): Promise<IPhoto> {
        const formData = new FormData();
        for (const [ key, value ] of Object.entries(request)) {
            formData.append(key, value);
        }

        const response = await apiClient
            .post<IApiResponse<IUploadPhotoResponse>>(`/album/${albumId}/photo`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                    'Authorization': `Bearer ${loginToken}`,
                },
            });

        const photo = response.data.result.photo;

        return {
            id: photo.id,
            title: photo.title,
            description: photo.description,
            url: photo.url,
            thumbnailUrl: photo.thumbnailUrl,
            longitude: photo.longitude,
            latitude: photo.latitude,
        };
    },

    async deletePhoto(albumId: number, photoId: number, loginToken: string): Promise<void> {
        const response = await apiClient
            .delete<IApiResponse<void>>(`/album/${albumId}/photo/${photoId}`, {
                headers: {
                    'Authorization': `Bearer ${loginToken}`,
                },
            });
    },

    async reorderPhotos(albumId: number, request: IReorderPhotosRequest, loginToken: string): Promise<void> {
        const response = await apiClient
            .put<IApiResponse<void>>(`/album/${albumId}/photos/reorder`, request, {
                headers: {
                    'Authorization': `Bearer ${loginToken}`,
                },
            });
    },
};