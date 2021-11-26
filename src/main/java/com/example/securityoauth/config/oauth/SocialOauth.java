package com.example.securityoauth.config.oauth;

import com.example.securityoauth.modules.user.dto.response.UserDetailDTO;
import com.example.securityoauth.modules.user.entity.SocialEntity;

public interface SocialOauth {
    String getOauthRedirectURL();
    String requestAccessToken(String code);
    SocialUserInfo requestUserInfo(String accessToken);
    SocialUserInfo verifyAccessToken(String tokenInfo);
    String reissuanceAccessToken(String refreshToken);
}
