package cn.edu.ujs.service;

import cn.edu.ujs.entity.SignIn;

import java.util.List;

/**
 * Created by DELL on 2017/12/26.
 */
public interface SignInService {

    /**显示所有签到信息*/
    public List<SignIn> findAll();

    /**根据读者编号查询签到信息*/
    public List<SignIn> findByUserId(String userId);

    /**更新签到表*/
    public SignIn save(SignIn signIn);
}
