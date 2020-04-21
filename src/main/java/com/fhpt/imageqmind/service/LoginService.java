package com.fhpt.imageqmind.service;

import com.fhpt.imageqmind.domain.UserEntity;

/**
 * 登录业务类
 * @author Marty
 */
public interface LoginService {

    UserEntity getLoginUser();

    String getLoginUserName();

    boolean isLogin();

    void login(Long userId);

    void logout(Long userId);
}
