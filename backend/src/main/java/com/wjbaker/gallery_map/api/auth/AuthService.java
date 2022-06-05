package com.wjbaker.gallery_map.api.auth;

import com.wjbaker.gallery_map.api.auth.type.CreateUserRequest;
import com.wjbaker.gallery_map.api.auth.type.LogInRequest;
import com.wjbaker.gallery_map.api.auth.type.LogInResponse;
import com.wjbaker.gallery_map.core.result.Result;
import com.wjbaker.gallery_map.core.result.ResultOf;
import com.wjbaker.gallery_map.database.entity.UserEntity;
import com.wjbaker.gallery_map.database.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Locale;

@Service
public final class AuthService {

    private final IUserRepository userRepository;
    private final PasswordService passwordService;
    private final LoginTokenService loginTokenService;

    public AuthService(
        @Autowired final IUserRepository userRepository,
        @Autowired final PasswordService passwordService,
        @Autowired final LoginTokenService loginTokenService) {

        this.userRepository = userRepository;
        this.passwordService = passwordService;
        this.loginTokenService = loginTokenService;
    }

    public Result createUser(final CreateUserRequest request) {
        var existingUserWithUsername = this.userRepository.findByUsername(request.username().toLowerCase(Locale.ROOT));
        if (existingUserWithUsername.isPresent())
            return Result.failure("Cannot create user, a user already exists with that username.");

        var generatedPassword = this.passwordService.hashPassword(request.password());

        var user = this.userRepository.save(new UserEntity(
            0,
            request.username(),
            generatedPassword.getFirst(),
            generatedPassword.getSecond(),
            Instant.now(),
            false
        ));

        return Result.success();
    }

    public ResultOf<LogInResponse> logIn(final LogInRequest request) {
        var userOptional = this.userRepository.findByUsername(request.username());
        if (userOptional.isEmpty())
            return ResultOf.failure("Could not find user with the given username, please try again.");

        var user = userOptional.get();

        var isPasswordValid = this.passwordService.check(request.password(), user.getPassword(), user.getPasswordSalt());
        if (!isPasswordValid)
            return ResultOf.failure("Password was incorrect for the user, please try again.");

        var loginTokenResult = this.loginTokenService.create(user);
        if (loginTokenResult.isFailure())
            return ResultOf.fromFailure(loginTokenResult);

        return ResultOf.of(new LogInResponse(
            loginTokenResult.getContent()
        ));
    }
}