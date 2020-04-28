package com.fhpt.imageqmind.service.impl;

import com.fhpt.imageqmind.domain.UserEntity;
import com.fhpt.imageqmind.service.LoginService;
import com.fhpt.imageqmind.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

/**
 * 登录业务
 * @author Marty
 */
@Service
public class LoginServiceImpl implements LoginService {

    private final String USER_ID = "USER_ID";

    @Autowired
    private UserService userService;

    @Override
    public UserEntity getLoginUser() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        Long userId = (Long) request.getSession().getAttribute(USER_ID);
        if (userId != null) {
            return userService.getById(userId);
        } else {
            return null;
        }
    }

    @Override
    public String getLoginUserName() {
        UserEntity userEntity = getLoginUser();
        if (userEntity != null) {
            return userEntity.getUserName();
        }
        return "";
    }

    @Override
    public boolean isLogin() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        return request.getSession().getAttribute(USER_ID) != null;
    }

    @Override
    public void login(int userId) {

    }

    @Override
    public void logout(int userId) {

    }

}
