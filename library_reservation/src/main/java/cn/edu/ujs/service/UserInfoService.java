package cn.edu.ujs.service;

import cn.edu.ujs.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by DELL on 2017/12/26.
 */
public interface UserInfoService {

    /**更新用户状态信息*/
    public UserInfo save(UserInfo userInfo);

    /**根据读者编号查询*/
    public UserInfo findOne(String userId);
}
