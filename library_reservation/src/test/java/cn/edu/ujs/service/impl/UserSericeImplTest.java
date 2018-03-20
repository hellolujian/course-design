package cn.edu.ujs.service.impl;

import cn.edu.ujs.entity.User;
import cn.edu.ujs.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by DELL on 2018/1/3.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserSericeImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void testFindByUserIdAndUserPassword() throws Exception {
        User user = userService.findByUserIdAndUserPassword("3140602023","123456");
        Assert.assertNotNull(user);
    }
}