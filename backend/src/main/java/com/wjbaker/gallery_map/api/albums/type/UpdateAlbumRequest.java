package com.wjbaker.gallery_map.api.albums.type;

public final record UpdateAlbumRequest(
    String title,
    String coverUrl,
    double longitude,
    double latitude) {}