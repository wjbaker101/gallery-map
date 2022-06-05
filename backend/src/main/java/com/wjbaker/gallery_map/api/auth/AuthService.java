package com.wjbaker.gallery_map.api.auth;

import com.wjbaker.gallery_map.api.auth.type.CreateUserRequest;
import com.wjbaker.gallery_map.api.auth.type.LogInRequest;
import com.wjbaker.gallery_map.api.auth.type.LogInResponse;
import com.wjbaker.gallery_map.core.result.Result;
import com.wjbaker.gallery_map.core.result.ResultOf;
import org.springframework.stereotype.Service;

@Service
public final class AuthService {

    public Result createUser(final CreateUserRequest request) {
        return Result.success();
    }

    public ResultOf<LogInResponse> logIn(final LogInRequest request) {
        return ResultOf.of(new LogInResponse("Test"));
    }
}