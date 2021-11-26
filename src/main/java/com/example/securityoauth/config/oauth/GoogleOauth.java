package com.example.securityoauth.config.oauth;

import com.example.securityoauth.utils.JsonToMap;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class GoogleOauth implements SocialOauth{
    @Value("${sns.google.url}")
    private String GOOGLE_SNS_BASE_URL;
    @Value("${sns.google.client.id}")
    private String GOOGLE_SNS_CLIENT_ID;
    @Value("${sns.google.callback.url}")
    private String GOOGLE_SNS_CALLBACK_URL;
    @Value("${sns.google.client.secret}")
    private String GOOGLE_SNS_CLIENT_SECRET;
    @Value("${sns.google.token.url}")
    private String GOOGLE_SNS_TOKEN_BASE_URL;
    @Value("${sns.google.userinfo.url}")
    private String GOOGLE_SNS_USER_INFO_URL;


    // 구글 소셜 로그인 redirect 주소 얻기
    @Override
    public String getOauthRedirectURL() {
        Map<String, Object> params = new HashMap<>();

        params.put("scope", "profile email");
        params.put("response_type", "code");
        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
        params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);
        params.put("access_type","offline");

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        return GOOGLE_SNS_BASE_URL + "?" + parameterString;
    }

    // 구글 소셜 로그인 access_token 및 refresh_token 등의 정보 얻기

    /**
     * 1회용 code로 API 요청을 하여 받는 데이터
     * access_token
     * expires_in
     * refresh_token
     * scope
     * token_type
     * id_token
     * @param code 1회용 코드
     * @return json 응답데이터
     */
    @Override
    public String requestAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
        params.put("client_secret", GOOGLE_SNS_CLIENT_SECRET);
        params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);
        params.put("grant_type", "authorization_code");

        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(GOOGLE_SNS_TOKEN_BASE_URL, params, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {

            return responseEntity.getBody();
        }
        return null;
    }


    /**
     * "id": "100337049058593586888",
     * "email": "qwq140@gmail.com",
     * "verified_email": true,
     * "name": "박재빈",
     * "given_name": "재빈",
     * "family_name": "박",
     * "picture": "https://lh3.googleusercontent.com/a/AATXAJyzLQRt7RZyiPIF10VIDQr7jAXgnA7oqQc4iUP4xg=s96-c",
     * "locale": "ko"
     * @param accessToken
     * @return SocialUserInfo
     */
    public SocialUserInfo requestUserInfo(String accessToken){
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<SocialUserInfo> responseEntity =
                    restTemplate.getForEntity(GOOGLE_SNS_USER_INFO_URL+"?access_token="+accessToken, SocialUserInfo.class);
            System.out.println(responseEntity.getStatusCode());
            System.out.println(responseEntity.getBody());
            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            return null;
        }
    }

    /**
     *
     * @param tokenInfo
     * @return SocialUserInfo
     */
    // access_token 검증
    public SocialUserInfo verifyAccessToken(String tokenInfo){
        System.out.println(tokenInfo);
        Map<String, String> map = JsonToMap.jsonToObject(tokenInfo);
        String accessToken = map.get("access_token");
        SocialUserInfo socialUserInfo = requestUserInfo(accessToken);

        socialUserInfo.setAccessToken(accessToken);
        socialUserInfo.setRefreshToken(map.get("refresh_token"));
        return socialUserInfo;
    }


    /**
     * "access_token":
     * "expires_in":
     * "scope":
     * "token_type":
     * "id_token":
     * @param refreshToken
     * @return
     */
    // access_token 재발급
    @Override
    public String reissuanceAccessToken(String refreshToken) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            Map<String, Object> params = new HashMap<>();
            params.put("client_id", GOOGLE_SNS_CLIENT_ID);
            params.put("client_secret", GOOGLE_SNS_CLIENT_SECRET);
            params.put("grant_type", "refresh_token");
            params.put("refresh_token", refreshToken);

            ResponseEntity<String> responseEntity =
                    restTemplate.postForEntity(GOOGLE_SNS_TOKEN_BASE_URL, params, String.class);

            Map<String, String> map = JsonToMap.jsonToObject(responseEntity.getBody());
            String accessToken = map.get("access_token");

            return accessToken;
        } catch (HttpClientErrorException e){
            return null;
        }

    }
}
