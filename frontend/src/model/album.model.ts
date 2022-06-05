import { IPhoto } from '@/model/photo.model';

export interface IAlbum {
    id: number;
    title: string;
    photos: Array<IPhoto>;
}