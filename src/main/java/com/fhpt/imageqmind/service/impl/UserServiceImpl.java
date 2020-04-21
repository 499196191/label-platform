package com.fhpt.imageqmind.service.impl;

import com.fhpt.imageqmind.domain.UserEntity;
import com.fhpt.imageqmind.objects.Result;
import com.fhpt.imageqmind.repository.UserRepository;

import com.fhpt.imageqmind.service.LoginService;
import com.fhpt.imageqmind.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


/**
 * 用户登录业务实现类
 *
 * @author xieGuanglin
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoginService loginService;


    /**
     * 登录验证
     */
    @Override
    public Result<UserEntity> login(String userName, String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userName);
        userEntity.setPasswd(password);
        Example<UserEntity> userEntityExample = Example.of(userEntity);
        Optional<UserEntity> optionalUserEntity = userRepository.findOne(userEntityExample);
        Result<UserEntity> result = new Result<>();
        if (optionalUserEntity.isPresent()) {
            result.data = optionalUserEntity.get();
            result.code = 0;
            result.msg = "登录成功";
            loginService.login(optionalUserEntity.get().getId());
        } else {
            result.data = optionalUserEntity.get();
            result.code = 1;
            result.msg = "用户名或密码不对";
        }
        return result;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public int insert(String userName, String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userName);
        userEntity.setPasswd(password);
        Example<UserEntity> userEntityExample = Example.of(userEntity);
        boolean exist = userRepository.exists(userEntityExample);
        if (exist) {
            return 1;
        }
        userRepository.save(userEntity);
        return 0;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean update(String userName, String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userName);
        userEntity.setPasswd(password);
        Example<UserEntity> userEntityExample = Example.of(userEntity);
        Optional<UserEntity> userEntityOptional = userRepository.findOne(userEntityExample);
        if (userEntityOptional.isPresent()) {
            UserEntity user = userEntityOptional.get();
            user.setUserName(userName);
            user.setPasswd(password);
            userRepository.save(user);
        }
        return true;
    }

    @Override
    public UserEntity getById(Long userId) {
        return userRepository.getOne(userId);
    }
}
