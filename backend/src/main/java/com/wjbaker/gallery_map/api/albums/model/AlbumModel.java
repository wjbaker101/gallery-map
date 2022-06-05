package com.wjbaker.gallery_map.api.albums.model;

import java.time.Instant;
import java.util.List;

public final record AlbumModel(
    long id,
    String title,
    Instant createdAt,
    List<PhotoModel> photos) {}