package cn.edu.ujs.repository;

import cn.edu.ujs.entity.SignIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by DELL on 2017/12/26.
 */
@Component
public interface SignInRepository extends JpaRepository<SignIn,Integer> {

    public List<SignIn> findByUserId(String userId);
}
