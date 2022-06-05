package com.wjbaker.gallery_map.api.auth;

import com.wjbaker.gallery_map.api.auth.type.CreateUserRequest;
import com.wjbaker.gallery_map.api.auth.type.LogInRequest;
import com.wjbaker.gallery_map.api.auth.type.LogInResponse;
import com.wjbaker.gallery_map.type.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public final class AuthController {

    private final AuthService authService;

    public AuthController(@Autowired final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/user")
    public ApiResponse<Void> createUser(@RequestBody final CreateUserRequest request) {
        var result = this.authService.createUser(request);
        return ApiResponse.FromResult(result);
    }

    @PostMapping("/login")
    public ApiResponse<LogInResponse> logIn(@RequestBody final LogInRequest request) {
        var result = this.authService.logIn(request);
        return ApiResponse.FromResult(result);
    }
}