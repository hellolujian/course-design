package cn.edu.ujs.service;

import cn.edu.ujs.entity.SignOut;

import java.util.List;

/**
 * Created by DELL on 2017/12/28.
 */
public interface SignOutService {

    /**更新签离表*/
    public SignOut update(SignOut signOut);

    /**根据读者编号查询签离记录*/
    public List<SignOut> findByUserId(String userId);

    /**查询所有签离记录*/
    public List<SignOut> findAll();
}
