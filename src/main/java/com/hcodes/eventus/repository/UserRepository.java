package com.hcodes.eventus.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hcodes.eventus.entity.UserEntity;
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    void deleteByUsername(String username);
}
