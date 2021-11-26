package com.example.securityoauth.modules.user;


import com.example.securityoauth.config.PrincipalDetails;
import com.example.securityoauth.modules.user.service.SocialService;
import com.example.securityoauth.enum_package.type.SocialLoginType;
import com.example.securityoauth.config.oauth.SocialUserInfo;
import com.example.securityoauth.modules.user.dto.response.SocialDTO;
import com.example.securityoauth.modules.user.entity.SocialEntity;
import com.example.securityoauth.modules.user.entity.UserEntity;
import com.example.securityoauth.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
@Slf4j
public class SocialController {

    private final SocialService socialService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    /**
     * Social Login API Server 요청에 의한 callback 처리
     * @param socialLoginType (GOOGLE, FACEBOOK, NAVER, KAKAO)
     * @param code API Server 로 부터 넘어오는 code
     * @return
     */
    @GetMapping("/{socialLoginType}/callback")
    public String callback(@PathVariable(name = "socialLoginType") SocialLoginType socialLoginType, @RequestParam(name="code") String code, Model model){
        log.info("소셜 로그인 API서버로 부터 받은 code :: {}",code);
        String tokenInfo = socialService.requestAccessToken(socialLoginType, code);
        SocialUserInfo socialUserInfo = socialService.verifyAccessToken(socialLoginType, tokenInfo);
        SocialEntity socialEntity = socialService.findBySub(socialUserInfo.getId());
        if (socialEntity == null){
            // 소셜 테이블에 넣고 추가정보 입력 페이지 이동
            log.info("소셜 처음 인증하기");
            SocialEntity socialEntity1 = socialService.save(socialUserInfo, socialLoginType);
            SocialDTO socialDTO = socialUserInfo.toSocialDTO(socialEntity1.getIdx());
            model.addAttribute("socialDTO", socialDTO);
            return "user/join_social";
        } else {
            // 소셜 테이블에 있는 경우 소셜 테이블에 있는 access token 검증하기, 유효기간이 끝났다면 refresh token으로 재발급
            // 유저 테이블에 해당 소셜 idx를 가진 유저가 없으면 추가 정보 입력하는 페이지로 이동
            // 있으면 로그인 진행
            if (socialService.requestUserInfo(socialLoginType, socialEntity.getAccessToken()) == null){
                // access token 만료됨. refresh token으로 재발급 하고 진행
                log.info("액세스토큰 만료됨 재발급 받기");
                System.out.println(socialEntity.getRefreshToken());
                String accessToken = socialService.reissuanceAccessToken(socialLoginType, socialEntity.getRefreshToken());
                socialEntity = socialService.updateAccessToken(socialEntity.getIdx(),accessToken);
            }
            UserEntity userEntity = userService.findBySocialIdx(socialEntity.getIdx());
            if (userEntity == null){
                // 추가 정보 입력 페이지로 이동
                log.info("소셜 인증만 하고 회원가입 x -> 회원가입 페이지로 이동");
                model.addAttribute("socialDTO", socialUserInfo.toSocialDTO(socialEntity.getIdx()));
                return "user/join_social";
            }

            log.info("로그인 진행하기 위한 세션 생성");
            PrincipalDetails principalDetails = new PrincipalDetails(userEntity);

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(principalDetails,null,principalDetails.getAuthorities()));
            log.info("세션 생성 끝");

            return "redirect:/test";

        }
    }

}
