package com.example.securityoauth.modules.user;

import com.example.securityoauth.config.PrincipalDetails;
import com.example.securityoauth.modules.user.dto.request.UserJoinDTO;
import com.example.securityoauth.modules.user.dto.response.UserDetailDTO;
import com.example.securityoauth.modules.user.entity.UserEntity;
import com.example.securityoauth.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/join")
    public String joinForm(){
        return "user/join";
    }

    @PostMapping("/join")
    public String join(UserJoinDTO userJoinDTO){
        UserDetailDTO userDetailDTO = userService.save(userJoinDTO);
        return "user/login";
    }

    @GetMapping("/login")
    public String login(){
        return "user/login";
    }

    @PostMapping("/social")
    public String joinSocial(UserJoinDTO userJoinDTO){
        UserEntity userEntity = userService.joinSocial(userJoinDTO);
        PrincipalDetails principalDetails = new PrincipalDetails(userEntity);

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(principalDetails,null,principalDetails.getAuthorities()));
        return "redirect:/test";
    }
}
