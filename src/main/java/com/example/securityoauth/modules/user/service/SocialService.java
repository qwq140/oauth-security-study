package com.example.securityoauth.modules.user.service;

import com.example.securityoauth.config.oauth.*;
import com.example.securityoauth.enum_package.type.SocialLoginType;
import com.example.securityoauth.modules.user.entity.SocialEntity;
import com.example.securityoauth.modules.user.repository.SocialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SocialService {

    private final GoogleOauth googleOauth;
    private final FacebookOauth facebookOauth;
    private final KakaoOauth kakaoOauth;
    private final NaverOauth naverOauth;
    private final HttpServletResponse response;

    private final SocialRepository socialRepository;

    public void request(SocialLoginType socialLoginType){
        String redirectURL;
        switch (socialLoginType){
            case GOOGLE:
                redirectURL = googleOauth.getOauthRedirectURL();
                break;
            case FACEBOOK:
                redirectURL = facebookOauth.getOauthRedirectURL();
                break;
            case KAKAO:
                redirectURL = kakaoOauth.getOauthRedirectURL();
                break;
            case NAVER:
                redirectURL = naverOauth.getOauthRedirectURL();
                break;
            default: {
                throw new IllegalArgumentException("알 수 없는 소셜 로그인 형식입니다.");
            }
        }
        try {
            response.sendRedirect(redirectURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String requestAccessToken(SocialLoginType socialLoginType, String code){
        switch (socialLoginType){
            case GOOGLE:
                return googleOauth.requestAccessToken(code);
            case FACEBOOK:
                return facebookOauth.requestAccessToken(code);
            case KAKAO:
                return kakaoOauth.requestAccessToken(code);
            case NAVER:
                return naverOauth.requestAccessToken(code);
            default: {
                throw new IllegalArgumentException("알 수 없는 소셜 로그인 형식입니다.");
            }
        }
    }

    public SocialUserInfo requestUserInfo(SocialLoginType socialLoginType, String accessToken){
        switch (socialLoginType){
            case GOOGLE:
                return googleOauth.requestUserInfo(accessToken);
            case FACEBOOK:
                return facebookOauth.requestUserInfo(accessToken);
            case KAKAO:
                return kakaoOauth.requestUserInfo(accessToken);
            case NAVER:
                return naverOauth.requestUserInfo(accessToken);
            default: {
                throw new IllegalArgumentException("알 수 없는 소셜 로그인 형식입니다.");
            }
        }
    }

    public SocialUserInfo verifyAccessToken(SocialLoginType socialLoginType, String tokenInfo){
        switch (socialLoginType){
            case GOOGLE:
                return googleOauth.verifyAccessToken(tokenInfo);
            case FACEBOOK:
                return facebookOauth.verifyAccessToken(tokenInfo);
            case KAKAO:
                return kakaoOauth.verifyAccessToken(tokenInfo);
            case NAVER:
                return naverOauth.verifyAccessToken(tokenInfo);
            default: {
                throw new IllegalArgumentException("알 수 없는 소셜 로그인 형식입니다.");
            }
        }
    }

    public String reissuanceAccessToken(SocialLoginType socialLoginType, String refreshToken){
        switch (socialLoginType){
            case GOOGLE:
                return googleOauth.reissuanceAccessToken(refreshToken);
            case FACEBOOK:
                return facebookOauth.reissuanceAccessToken(refreshToken);
            case KAKAO:
                return kakaoOauth.reissuanceAccessToken(refreshToken);
            case NAVER:
                return naverOauth.reissuanceAccessToken(refreshToken);
            default: {
                throw new IllegalArgumentException("알 수 없는 소셜 로그인 형식입니다.");
            }
        }
    }

    public SocialEntity findBySub(String sub){
        Optional<SocialEntity> socialEntityOptional = socialRepository.findBySub(sub);
        if (socialEntityOptional.isEmpty()){
            return null;
        }
        return socialEntityOptional.get();
    }

    @Transactional
    public SocialEntity save(SocialUserInfo socialUserInfo, SocialLoginType socialLoginType){
        SocialEntity socialEntity = socialUserInfo.toEntity();
        socialEntity.setProvider(socialLoginType);
        return socialRepository.save(socialEntity);
    }

    @Transactional
    public SocialEntity updateAccessToken(Integer socialIdx, String accessToken){
        Optional<SocialEntity> socialEntityOptional = socialRepository.findById(socialIdx);
        if (socialEntityOptional.isEmpty()){
            return null;
        }
        socialEntityOptional.get().setAccessToken(accessToken);
        return socialEntityOptional.get();
    }

}
