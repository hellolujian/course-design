package cn.edu.ujs.service;

import cn.edu.ujs.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by DELL on 2018/1/3.
 */
public interface UserService {

    public User findByUserIdAndUserPassword(String userId, String userPassword);

    public boolean isExist(String userId, String password);

    public boolean isLogin(String userId, HttpSession httpSession);

    public User findByUserId(String userId);
}
