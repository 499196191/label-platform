package com.fhpt.imageqmind.repository;

import com.fhpt.imageqmind.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户登录
 * @author xieguanglin
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
