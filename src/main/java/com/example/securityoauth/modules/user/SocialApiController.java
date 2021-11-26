package com.example.securityoauth.modules.user;


import com.example.securityoauth.enum_package.type.SocialLoginType;
import com.example.securityoauth.modules.user.service.SocialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
@Slf4j
public class SocialApiController {

    private final SocialService socialService;

    /**
     * 사용자로부터 SNS 로그인 요청을 SocialLoginType을 받아서 처리
     * @param socialLoginType (GOOGLE, FACEBOOK, NAVER, KAKAO)
     */
    @GetMapping("/{socialLoginType}")
    public void socialLoginType(@PathVariable(name = "socialLoginType") SocialLoginType socialLoginType){
        log.info("사용자로 부터 SNS 로그인 요청을 받음 :: {} Social Login", socialLoginType);
        socialService.request(socialLoginType);
    }
}
