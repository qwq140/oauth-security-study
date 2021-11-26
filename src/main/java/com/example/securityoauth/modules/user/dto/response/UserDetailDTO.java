package com.example.securityoauth.modules.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDTO {
    private String id;
    private String password;
    private String email;
    private String hp;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
