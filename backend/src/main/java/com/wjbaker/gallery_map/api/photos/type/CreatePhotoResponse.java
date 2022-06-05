package com.wjbaker.gallery_map.api.photos.type;

import com.wjbaker.gallery_map.api.albums.model.PhotoModel;

public final record CreatePhotoResponse(
    PhotoModel photo) {}