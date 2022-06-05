import { IApiPhoto } from '@/api/albums/type/ApiPhoto.type';

export interface IApiAlbum {
    id: number;
    title: string;
    photos: Array<IApiPhoto>;
}