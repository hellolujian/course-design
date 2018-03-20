package cn.edu.ujs.service.impl;

import cn.edu.ujs.entity.User;
import cn.edu.ujs.repository.UserRepository;
import cn.edu.ujs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;

/**
 * Created by DELL on 2018/1/3.
 */
@Service
public class UserSericeImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUserIdAndUserPassword(String userId, String userPassword) {
        return userRepository.findByUserIdAndPassword(userId,userPassword);
    }

    @Override
    public boolean isExist(String userId, String password) {
        User user = userRepository.findByUserIdAndPassword(userId, password);
        if (user != null)
            return true;
        return false;
    }

    @Override
    public boolean isLogin(String userId, HttpSession httpSession) {
        if (StringUtils.isEmpty(httpSession.getAttribute("user")))
            return true;
        return false;
    }

    @Override
    public User findByUserId(String userId) {
        return userRepository.findOne(userId);
    }


}
