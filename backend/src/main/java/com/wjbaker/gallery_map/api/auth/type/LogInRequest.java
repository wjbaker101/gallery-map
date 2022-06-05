package com.wjbaker.gallery_map.api.auth.type;

public final record LogInRequest(
    String username,
    String password) {}