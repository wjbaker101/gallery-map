package com.wjbaker.gallery_map.api.albums.type;

import com.wjbaker.gallery_map.api.albums.model.AlbumModel;

public final record CreateAlbumResponse(
    AlbumModel album) {}
