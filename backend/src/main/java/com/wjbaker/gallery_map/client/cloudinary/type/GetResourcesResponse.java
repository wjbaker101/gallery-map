package com.wjbaker.gallery_map.client.cloudinary.type;

import java.time.LocalDateTime;
import java.util.List;

public final record GetResourcesResponse(
    List<Resource> resources) {

    public final record Resource(
        String id,
        String createdAt,
        long fileSize,
        int width,
        int height,
        String url) {}
}
