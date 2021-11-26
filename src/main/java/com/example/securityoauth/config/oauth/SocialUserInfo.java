package com.example.securityoauth.config.oauth;

import com.example.securityoauth.modules.user.dto.response.SocialDTO;
import com.example.securityoauth.modules.user.entity.SocialEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialUserInfo {
    private String id;
    private String email;
    private String name;
    private String accessToken;
    private String refreshToken;

    public SocialEntity toEntity(){
        return SocialEntity.builder()
                .sub(id)
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .build();
    }

    public SocialDTO toSocialDTO(Integer socialIdx){
        return SocialDTO.builder()
                .socialIdx(socialIdx)
                .email(email)
                .name(name)
                .build();
    }
}
