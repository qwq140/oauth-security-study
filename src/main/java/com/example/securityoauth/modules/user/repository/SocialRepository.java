package com.example.securityoauth.modules.user.repository;

import com.example.securityoauth.modules.user.entity.SocialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialRepository extends JpaRepository<SocialEntity, Integer> {

    Optional<SocialEntity> findBySub(String sub);
}
