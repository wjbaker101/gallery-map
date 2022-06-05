package com.wjbaker.gallery_map.api.albums;

import com.wjbaker.gallery_map.api.albums.model.AlbumModel;
import com.wjbaker.gallery_map.api.albums.model.PhotoModel;
import com.wjbaker.gallery_map.api.albums.type.*;
import com.wjbaker.gallery_map.core.result.Result;
import com.wjbaker.gallery_map.core.result.ResultOf;
import com.wjbaker.gallery_map.database.entity.AlbumEntity;
import com.wjbaker.gallery_map.database.repository.IAlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;

@Service
public final class AlbumsService {

    private final IAlbumRepository albumRepository;

    public AlbumsService(@Autowired final IAlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public ResultOf<GetAlbumsResponse> getAlbums() {
        var albums = this.albumRepository.search();

        return ResultOf.of(new GetAlbumsResponse(albums.stream().map(album -> new AlbumModel(
            album.getId(),
            album.getTitle(),
            album.getCreatedAt(),
            album.getPhotos().stream().map(photo -> new PhotoModel(
                photo.getId(),
                photo.getTitle(),
                photo.getUrl(),
                photo.getThumbnailUrl(),
                photo.getLongitude(),
                photo.getLatitude(),
                photo.getCreatedAt()
            )).toList()
        )).toList()));
    }

    public ResultOf<CreateAlbumResponse> createAlbum(final CreateAlbumRequest request) {
        var album = this.albumRepository.save(new AlbumEntity(
            0,
            new ArrayList<>(),
            request.title(),
            Instant.now()
        ));

        return ResultOf.of(new CreateAlbumResponse(
            new AlbumModel(
                album.getId(),
                album.getTitle(),
                album.getCreatedAt(),
                new ArrayList<>()
            )
        ));
    }

    public ResultOf<UpdateAlbumResponse> updateAlbum(final long id, final UpdateAlbumRequest request) {
        var albumOptional = this.albumRepository.findById(id);
        if (albumOptional.isEmpty())
            return ResultOf.failure(String.format("Unable to find album with id: %d.", id));

        var album = albumOptional.get();

        album.setTitle(request.title());

        this.albumRepository.save(album);

        return ResultOf.of(new UpdateAlbumResponse(
            new AlbumModel(
                album.getId(),
                album.getTitle(),
                album.getCreatedAt(),
                new ArrayList<>()
            )
        ));
    }

    public Result deleteAlbum(final long id) {
        var albumOptional = this.albumRepository.findById(id);
        if (albumOptional.isEmpty())
            return Result.failure(String.format("Unable to find album with id: %d.", id));

        this.albumRepository.delete(albumOptional.get());

        return Result.success();
    }
}