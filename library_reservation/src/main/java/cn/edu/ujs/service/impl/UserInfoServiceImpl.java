package cn.edu.ujs.service.impl;

import cn.edu.ujs.entity.UserInfo;
import cn.edu.ujs.repository.UserInfoRepository;
import cn.edu.ujs.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by DELL on 2017/12/26.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService{


    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfo save(UserInfo userInfo) {
        return userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfo findOne(String userId) {
        return userInfoRepository.findOne(userId);
    }
}
