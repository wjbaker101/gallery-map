import { IApiPhoto } from './ApiPhoto.type';

export interface IUploadPhotoRequest {
    title: string;
    file: Blob;
}

export interface IUploadPhotoResponse {
    photo: IApiPhoto;
}