package cn.edu.ujs.repository;

import cn.edu.ujs.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * Created by DELL on 2017/12/26.
 */
@Component
public interface UserInfoRepository extends JpaRepository<UserInfo,String> {

    //public UserInfo findByUserId(String userId);
}
