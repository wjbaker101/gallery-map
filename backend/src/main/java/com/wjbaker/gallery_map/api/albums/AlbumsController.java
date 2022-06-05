package com.wjbaker.gallery_map.api.albums;

import com.wjbaker.gallery_map.api.albums.type.*;
import com.wjbaker.gallery_map.type.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public final class AlbumsController {

    private final AlbumsService albumsService;

    public AlbumsController(@Autowired final AlbumsService albumsService) {
        this.albumsService = albumsService;
    }

    @GetMapping("/albums")
    public ApiResponse<GetAlbumsResponse> getAlbums() {
        var result = this.albumsService.getAlbums();
        return ApiResponse.FromResult(result);
    }

    @PostMapping("/album")
    public ApiResponse<CreateAlbumResponse> createAlbum(@RequestBody final CreateAlbumRequest request) {
        var result = this.albumsService.createAlbum(request);
        return ApiResponse.FromResult(result);
    }

    @PutMapping("/album/{id}")
    public ApiResponse<UpdateAlbumResponse> updateAlbum(
        @PathVariable final long id,
        @RequestBody final UpdateAlbumRequest request) {

        var result = this.albumsService.updateAlbum(id, request);
        return ApiResponse.FromResult(result);
    }

    @DeleteMapping("/album/{id}")
    public ApiResponse<Void> deleteAlbum(@PathVariable final long id) {
        var result = this.albumsService.deleteAlbum(id);
        return ApiResponse.FromResult(result);
    }
}