package com.example.securityoauth.modules.user.service;

import com.example.securityoauth.enum_package.type.RoleType;
import com.example.securityoauth.modules.user.dto.request.UserJoinDTO;
import com.example.securityoauth.modules.user.dto.response.UserDetailDTO;
import com.example.securityoauth.modules.user.entity.SocialEntity;
import com.example.securityoauth.modules.user.entity.UserEntity;
import com.example.securityoauth.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public UserDetailDTO save(UserJoinDTO userJoinDTO){
        UserEntity userEntity = userJoinDTO.toEntity();

        Set<RoleType> roleTypes = new HashSet<>();
        roleTypes.add(RoleType.USER);

        userEntity.setRoles(roleTypes);
        userEntity.setPassword(encoder.encode(userJoinDTO.getPassword()));
        return userRepository.save(userEntity).toUserDetailDTO();
    }

    @Transactional
    public UserEntity joinSocial(UserJoinDTO userJoinDTO){
        UserEntity userEntity = userJoinDTO.toEntity();

        Set<RoleType> roleTypes = new HashSet<>();
        roleTypes.add(RoleType.USER);

        SocialEntity socialEntity = new SocialEntity();
        socialEntity.setIdx(userJoinDTO.getSocialIdx());

        userEntity.setRoles(roleTypes);
        userEntity.setSocialEntity(socialEntity);
        userEntity.setPassword(encoder.encode(UUID.randomUUID().toString()));

        return userRepository.save(userEntity);
    }

    public UserEntity findBySocialIdx(Integer socialIdx){
         Optional<UserEntity> userEntityOptional =userRepository.findBySocialIdx(socialIdx);
         if (userEntityOptional.isEmpty()){
             return null;
         }
         return userEntityOptional.get();
    }
}
