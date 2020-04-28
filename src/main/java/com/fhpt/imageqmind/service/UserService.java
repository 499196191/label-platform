package com.fhpt.imageqmind.service;

import com.fhpt.imageqmind.domain.UserEntity;
import com.fhpt.imageqmind.objects.Result;

/**
 * Created by xie
 */
public interface UserService {

    Result<UserEntity> login(String userCode, String password);

    int insert(String userCode,String userName, String password);

    boolean update(String userCode,String userName, String password);

    UserEntity getById(Long userId);
}
