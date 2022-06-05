import { IApiAlbum } from '@/api/albums/type/ApiAlbum.type';

export interface ICreateAlbumRequest {
    title: string;
}

export interface ICreateAlbumResponse {
    album: IApiAlbum;
}