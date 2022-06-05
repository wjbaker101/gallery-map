package com.wjbaker.gallery_map.api.photos;

import com.wjbaker.gallery_map.api.albums.model.PhotoModel;
import com.wjbaker.gallery_map.api.photos.type.CreatePhotoRequest;
import com.wjbaker.gallery_map.api.photos.type.CreatePhotoResponse;
import com.wjbaker.gallery_map.api.photos.type.ReorderPhotosRequest;
import com.wjbaker.gallery_map.client.cloudinary.CloudinaryClient;
import com.wjbaker.gallery_map.core.FailedBecause;
import com.wjbaker.gallery_map.core.result.Result;
import com.wjbaker.gallery_map.core.result.ResultOf;
import com.wjbaker.gallery_map.database.entity.PhotoEntity;
import com.wjbaker.gallery_map.database.repository.IAlbumRepository;
import com.wjbaker.gallery_map.database.repository.IPhotoRepository;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;

@Service
public final class PhotosService {

    private final IAlbumRepository albumRepository;
    private final IPhotoRepository photoRepository;
    private final CloudinaryClient cloudinaryClient;

    public PhotosService(
        @Autowired final IAlbumRepository albumRepository,
        @Autowired final IPhotoRepository photoRepository,
        @Autowired final CloudinaryClient cloudinaryClient) {

        this.albumRepository = albumRepository;
        this.photoRepository = photoRepository;
        this.cloudinaryClient = cloudinaryClient;
    }

    public ResultOf<CreatePhotoResponse> createPhoto(
        final long albumId,
        final CreatePhotoRequest request,
        final MultipartFile file) throws Exception {

        var albumOptional = this.albumRepository.findById(albumId);
        if (albumOptional.isEmpty())
            return ResultOf.failure(String.format("Unable to find album with id: %d.", albumId));

        var fileAsBytes = file.getBytes();
        var gps = getGpsFromImage(fileAsBytes);

        var photo = this.photoRepository.save(new PhotoEntity(
            0,
            albumOptional.get(),
            request.title(),
            null,
            null,
            gps.isFailure() ? null : gps.getContent().getFirst(),
            gps.isFailure() ? null : gps.getContent().getSecond(),
            Instant.now(),
            null,
            0
        ));

        var uploadImageResult = this.cloudinaryClient.uploadImage(
            fileAsBytes,
            "/gallery/" + albumOptional.get().getId(),
            String.valueOf(photo.getId()));
        if (uploadImageResult.isFailure())
            return ResultOf.fromFailure(uploadImageResult);

        photo.setUrl(uploadImageResult.getContent().url());
        photo.setThumbnailUrl(mapPhotoThumbnailUrl(photo.getUrl()));
        photo.setExternalIdentifier(uploadImageResult.getContent().assetId());

        this.photoRepository.save(photo);

        return ResultOf.of(new CreatePhotoResponse(new PhotoModel(
            photo.getId(),
            photo.getTitle(),
            photo.getUrl(),
            photo.getUrl(),
            photo.getLongitude(),
            photo.getLatitude(),
            photo.getCreatedAt()
        )));
    }

    private ResultOf<Pair<Double, Double>> getGpsFromImage(final byte[] image) throws IOException, ImageReadException {
        var metadata = Imaging.getMetadata(image);
        if (!(metadata instanceof JpegImageMetadata))
            return ResultOf.failure("No gps data to extract from image.");

        var jpgMetadata = ((JpegImageMetadata)metadata).getExif();
        if (jpgMetadata == null)
            return ResultOf.failure("No gps data to extract from image.");

        var gps = jpgMetadata.getGPS();
        if (gps == null)
            return ResultOf.failure("No gps data to extract from image.");

        return ResultOf.of(Pair.of(
            gps.getLongitudeAsDegreesEast(),
            gps.getLatitudeAsDegreesNorth()
        ));
    }

    private static String mapPhotoThumbnailUrl(final String url) {
        return url.replace("/image/upload", "/image/upload/w_160,h_160,c_fill");
    }

    public Result deletePhoto(final long albumId, final long photoId) {
        var albumOptional = this.albumRepository.findById(albumId);
        if (albumOptional.isEmpty())
            return Result.failure(FailedBecause.couldNotFindAlbum(albumId));

        var album = albumOptional.get();

        var photoOptional = album.getPhotos().stream().filter(x -> x.getId() == photoId).findFirst();
        if (photoOptional.isEmpty())
            return Result.failure(FailedBecause.couldNotFindPhotoInAlbum(albumId, photoId));

        var photo = photoOptional.get();

        var deleteImageResponse = this.cloudinaryClient.deleteImage(photo.getExternalIdentifier());
        if (deleteImageResponse.isFailure())
            return Result.fromFailure(deleteImageResponse);

        this.photoRepository.delete(photo);

        return Result.success();
    }

    public Result reorderPhotos(final long albumId, final ReorderPhotosRequest request) {
        var albumOptional = this.albumRepository.findById(albumId);
        if (albumOptional.isEmpty())
            return Result.failure(FailedBecause.couldNotFindAlbum(albumId));

        var album = albumOptional.get();

        for (final PhotoEntity photo: album.getPhotos()) {
            photo.setListOrder(request.photos().get(photo.getId()));
        }

        this.photoRepository.saveAll(album.getPhotos());

        return Result.success();
    }
}