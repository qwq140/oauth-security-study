package com.example.securityoauth.modules.user.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SocialDTO {
    private Integer socialIdx;
    private String email;
    private String name;
}
