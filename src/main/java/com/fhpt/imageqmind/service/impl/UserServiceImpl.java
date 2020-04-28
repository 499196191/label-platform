package com.fhpt.imageqmind.service.impl;

import com.fhpt.imageqmind.domain.UserEntity;
import com.fhpt.imageqmind.objects.Result;
import com.fhpt.imageqmind.repository.UserRepository;

import com.fhpt.imageqmind.service.LoginService;
import com.fhpt.imageqmind.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 登录验证
     */
    @Override
    public Result<UserEntity> login(String userCode, String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserCode(userCode);
        userEntity.setPassword(password);
        StringBuffer sql = new StringBuffer("select t from UserEntity t where 1=1 ");
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.hasText(userCode)) {
            sql.append(" and t.userCode = :userCode");
            params.put("userCode", userCode);
        }
        Query query = entityManager.createQuery(sql.toString());
        params.forEach((k, v) -> {
            query.setParameter(k, v);
        });
        List<UserEntity> list = (List<UserEntity>)query.getResultList();
        Result<UserEntity> result = new Result<>();
        if (list.size()>0) {
            result.data = list.get(0);
            result.code = 0;
            result.msg = "登录成功";
        } else {
            result.data = null;
            result.code = 1;
            result.msg = "用户名或密码不对";
        }
        return result;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public int insert(String userCode,String userName, String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserCode(userCode);
        userEntity.setUserName(userName);
        userEntity.setPassword(password);
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
    public boolean update(String userCode,String userName, String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserCode(userCode);
        userEntity.setUserName(userName);
        userEntity.setPassword(password);
        Example<UserEntity> userEntityExample = Example.of(userEntity);
        Optional<UserEntity> userEntityOptional = userRepository.findOne(userEntityExample);
        if (userEntityOptional.isPresent()) {
            UserEntity user = userEntityOptional.get();
            user.setUserName(userName);
            user.setPassword(password);
            userRepository.save(user);
        }
        return true;
    }

    @Override
    public UserEntity getById(Long userId) {
        return userRepository.getOne(userId);
    }
}
