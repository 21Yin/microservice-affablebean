package com.example.apisecurity.service;

import lombok.Getter;

public class Login {
    @Getter
    private final Jwt refreshToken;
    @Getter
    private final Jwt accessToken;

    private static final Long ACCESS_VALIDITY = 2L;
    private static final Long REFRESH_VALIDITY = 2880L;

    public static Login of(long userId, String accessSecret, String refreshSecret){
        return new Login(

                Jwt.of(userId,REFRESH_VALIDITY, refreshSecret),
                Jwt.of(userId, ACCESS_VALIDITY,accessSecret)
                );
    }

    public Login(Jwt refreshToken, Jwt accessToken) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
    }

}
