package com.example.securityoauth.config;

import com.example.securityoauth.modules.user.entity.UserEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PrincipalDetails implements UserDetails {
    private UserEntity userEntity;

    public PrincipalDetails(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collector = new ArrayList<>();
        List<String> roles = userEntity.getRoles()
                .stream()
                .map( roleType-> "ROLE_" + roleType.toString())
                .collect(Collectors.toList());

        roles.forEach( roleType -> collector.add(() -> roleType));
        return collector;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    public String getId(){
        return userEntity.getId();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
