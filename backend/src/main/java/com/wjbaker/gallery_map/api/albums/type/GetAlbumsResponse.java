package com.wjbaker.gallery_map.api.albums.type;

import com.wjbaker.gallery_map.api.albums.model.AlbumModel;
import lombok.Getter;

import java.util.List;

public final record GetAlbumsResponse(
    @Getter List<AlbumModel> albums) {}