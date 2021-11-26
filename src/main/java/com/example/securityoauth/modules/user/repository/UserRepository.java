package com.example.securityoauth.modules.user.repository;

import com.example.securityoauth.modules.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findById(String id);

    @Query(value = "SELECT * FROM TB_USER WHERE social_idx=?1",nativeQuery = true)
    Optional<UserEntity> findBySocialIdx(Integer socialIdx);
}
