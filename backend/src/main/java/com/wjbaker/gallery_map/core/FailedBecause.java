package com.wjbaker.gallery_map.core;

public final class FailedBecause {

    public static String couldNotFindAlbum(final long albumId) {
        return String.format("Unable to find album (%d).", albumId);
    }

    public static String couldNotFindPhotoInAlbum(final long albumId, final long photoId) {
        return String.format("Unable to find photo (%d) in album (%d).", photoId, albumId);
    }
}