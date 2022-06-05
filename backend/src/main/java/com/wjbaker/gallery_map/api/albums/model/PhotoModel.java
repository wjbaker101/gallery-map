package com.wjbaker.gallery_map.api.albums.model;

import java.time.Instant;

public final record PhotoModel(
    long id,
    String title,
    String url,
    String thumbnailUrl,
    Double longitude,
    Double latitude,
    Instant createdAt) {}
