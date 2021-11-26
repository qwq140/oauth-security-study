package com.example.securityoauth;

import com.example.securityoauth.config.PrincipalDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/test")
    public String test(@AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println(principalDetails.getUserEntity().getId());
        return "test/test";
    }
}
