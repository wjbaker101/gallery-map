package com.wjbaker.gallery_map.api.auth;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public final class PasswordService {

    private static final String PEPPER = "uJWedVX(I*Lb@phOu~[Czzex~KR->|";

    public Pair<String, String> hashPassword(final String plainPassword) {
        var salt = randomString();

        var hashed = getHashedPassword(plainPassword, salt);

        return Pair.of(hashed, salt);
    }

    public boolean check(final String plainPassword, final String hashedPassword, final String salt) {
        var hashed = getHashedPassword(plainPassword, salt);
        return hashed.equals(hashedPassword);
    }

    private static String randomString() {
        var array = new byte[15];
        new Random().nextBytes(array);

        return DigestUtils.sha512Hex(array);
    }

    private static String getHashedPassword(final String plainPassword, final String salt) {
        var toHash = plainPassword + salt + PEPPER;
        return DigestUtils.sha512Hex(toHash);
    }
}