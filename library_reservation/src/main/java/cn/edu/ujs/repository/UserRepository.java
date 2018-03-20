package cn.edu.ujs.repository;

import cn.edu.ujs.entity.User;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * Created by DELL on 2018/1/3.
 */
@Component
public interface UserRepository extends JpaRepository<User,String> {

    public User findByUserIdAndPassword(String userId, String userPassword);
}
