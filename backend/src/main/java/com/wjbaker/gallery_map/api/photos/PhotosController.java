package com.wjbaker.gallery_map.api.photos;

import com.wjbaker.gallery_map.api.photos.type.CreatePhotoRequest;
import com.wjbaker.gallery_map.api.photos.type.CreatePhotoResponse;
import com.wjbaker.gallery_map.api.photos.type.ReorderPhotosRequest;
import com.wjbaker.gallery_map.type.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/album/{albumId}")
public final class PhotosController {

    private final PhotosService photosService;

    public PhotosController(@Autowired final PhotosService photosService) {
        this.photosService = photosService;
    }

    @PostMapping("/photo")
    public ApiResponse<CreatePhotoResponse> createPhoto(
        @PathVariable final long albumId,
        @RequestParam("title") final String title,
        @RequestParam("file") final MultipartFile file) throws Exception {

        var request = new CreatePhotoRequest(
            title
        );

        var result = this.photosService.createPhoto(albumId, request, file);
        return ApiResponse.FromResult(result);
    }

    @DeleteMapping("/photo/{photoId}")
    public ApiResponse<Void> deletePhoto(@PathVariable final long albumId, @PathVariable final long photoId) {
        var result = this.photosService.deletePhoto(albumId, photoId);
        return ApiResponse.FromResult(result);
    }

    @PutMapping("/photos/reorder")
    public ApiResponse<Void> reorderPhotos(
        @PathVariable final long albumId,
        @RequestBody final ReorderPhotosRequest request) {

        var result = this.photosService.reorderPhotos(albumId, request);
        return ApiResponse.FromResult(result);
    }
}