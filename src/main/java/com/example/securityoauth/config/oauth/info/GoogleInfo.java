package com.example.securityoauth.config.oauth.info;

import com.example.securityoauth.modules.user.entity.SocialEntity;
import lombok.Data;

import java.util.Map;

@Data
public class GoogleInfo{
    private String id;
    private String email;
    private String name;
}
