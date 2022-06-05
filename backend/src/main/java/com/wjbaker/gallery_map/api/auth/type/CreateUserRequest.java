package com.wjbaker.gallery_map.api.auth.type;

public final record CreateUserRequest(
    String username,
    String password) {}