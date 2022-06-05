package com.wjbaker.gallery_map.client.cloudinary.type;

import java.util.List;

public final record GetFolderResponse(
    List<Folder> folders,
    int totalCount) {

    public final record Folder(
        String path,
        String name) {}
}
