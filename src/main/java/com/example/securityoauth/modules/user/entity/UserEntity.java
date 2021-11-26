package com.example.securityoauth.modules.user.entity;

import com.example.securityoauth.enum_package.type.RoleType;
import com.example.securityoauth.modules.user.dto.response.UserDetailDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name="TB_USER")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column(unique = true)
    private String id;

    private String username;

    private String password;
    private String email;
    private String hp;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<RoleType> roles;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = SocialEntity.class)
    @JoinColumn(name = "social_idx", referencedColumnName = "idx")
    private SocialEntity socialEntity;

    @CreationTimestamp
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;

    public UserDetailDTO toUserDetailDTO(){
        return UserDetailDTO.builder()
                .id(id)
                .password(password)
                .email(email)
                .hp(hp)
                .createDate(createDate)
                .updateDate(updateDate)
                .build();
    }

}
