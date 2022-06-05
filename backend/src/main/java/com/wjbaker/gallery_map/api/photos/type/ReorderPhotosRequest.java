package com.wjbaker.gallery_map.api.photos.type;

import java.util.Map;

public final record ReorderPhotosRequest(Map<Long, Integer> photos) {}