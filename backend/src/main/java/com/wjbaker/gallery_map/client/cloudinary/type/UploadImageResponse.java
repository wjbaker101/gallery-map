package com.wjbaker.gallery_map.client.cloudinary.type;

public final record UploadImageResponse(
    String url,
    String assetId) {}