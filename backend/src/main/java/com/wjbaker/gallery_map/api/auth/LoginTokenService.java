package com.wjbaker.gallery_map.api.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.wjbaker.gallery_map.core.result.ResultOf;
import com.wjbaker.gallery_map.database.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public final class LoginTokenService {

    private static final String ISSUER = "gallery.wjbaker.com";
    private static final String USERNAME_CLAIM = "u";

    private final Algorithm algorithm;

    public LoginTokenService(@Value("${login_token.secret_key}") final String secretKey) {
        this.algorithm = Algorithm.HMAC256(secretKey);
    }

    public ResultOf<String> create(final UserEntity user) {
        try {
            return ResultOf.of(JWT
                .create()
                .withIssuer(ISSUER)
                .withClaim(USERNAME_CLAIM, user.getUsername())
                .sign(this.algorithm));
        }
        catch (final JWTCreationException exception) {
            return ResultOf.failure("Failed to create login token, configuration was invalid.");
        }
        catch (final Exception exception) {
            return ResultOf.failure("Failed to create login token, something went wrong.");
        }
    }

    public ResultOf<String> check(final String token) {
        try {
            var verifier = JWT
                .require(this.algorithm)
                .withIssuer(ISSUER)
                .build()
                .verify(token);

            return ResultOf.of(verifier.getClaim(USERNAME_CLAIM).asString());
        }
        catch (final JWTVerificationException exception) {
            return ResultOf.failure("Failed to validate login token, token is invalid.");
        }
        catch (final Exception exception) {
            return ResultOf.failure("Failed to validate login token, something went wrong.");
        }
    }
}