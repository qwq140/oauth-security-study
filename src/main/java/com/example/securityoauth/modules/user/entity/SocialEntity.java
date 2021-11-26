package com.example.securityoauth.modules.user.entity;

import com.example.securityoauth.enum_package.type.SocialLoginType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="TB_SOCIAL")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column(unique = true)
    private String sub;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "refresh_token")
    private String refreshToken;

    private SocialLoginType provider;

    @CreationTimestamp
    private LocalDateTime createDate;

}
