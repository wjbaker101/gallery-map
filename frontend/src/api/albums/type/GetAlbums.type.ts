import { IApiAlbum } from '@/api/albums/type/ApiAlbum.type';

export interface IGetAlbumsResponse {
    albums: Array<IApiAlbum>;
}