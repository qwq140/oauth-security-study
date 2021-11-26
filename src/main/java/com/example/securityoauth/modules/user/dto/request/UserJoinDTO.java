package com.example.securityoauth.modules.user.dto.request;

import com.example.securityoauth.modules.user.entity.UserEntity;
import lombok.Data;

@Data
public class UserJoinDTO {
    private String id;
    private String username;
    private String password;
    private String email;
    private String hp;
    private Integer socialIdx;

    public UserEntity toEntity(){
        return UserEntity.builder()
                .id(id)
                .username(username)
                .email(email)
                .hp(hp)
                .build();
    }

}
