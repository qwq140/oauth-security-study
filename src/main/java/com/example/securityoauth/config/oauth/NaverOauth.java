package com.example.securityoauth.config.oauth;

import org.springframework.stereotype.Component;

@Component
public class NaverOauth implements SocialOauth{
    @Override
    public String getOauthRedirectURL() {
        return null;
    }

    @Override
    public String requestAccessToken(String code) {
        return null;
    }

    @Override
    public SocialUserInfo requestUserInfo(String accessToken) {
        return null;
    }

    @Override
    public SocialUserInfo verifyAccessToken(String tokenInfo) {
        return null;
    }

    @Override
    public String reissuanceAccessToken(String refreshToken) {
        return null;
    }
}
