package cn.edu.ujs.service.impl;

import cn.edu.ujs.entity.SignIn;
import cn.edu.ujs.service.SignInService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by DELL on 2017/12/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SignInServiceImplTest {

    @Autowired
    private SignInService signInService;

    @Test
    public void testFindAll() throws Exception {

        List<SignIn> signInList = signInService.findAll();

        System.out.println(signInList.get(0).getSignInTime());
    }

    @Test
    public void testFindByUserId() throws Exception {

        signInService.findByUserId("3140602023");
    }
}