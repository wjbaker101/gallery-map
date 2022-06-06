package com.wjbaker.gallery_map.api.photos.type;

import org.springframework.web.multipart.MultipartFile;

public final record CreatePhotoRequest(
    String title,
    MultipartFile file) {}