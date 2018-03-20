package cn.edu.ujs.mapper;

import cn.edu.ujs.VO.SignInVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by DELL on 2018/1/11.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SignInMapperTest {

    @Autowired
    private SignInMapper signInMapper;

    @Test
    public void testGetSignInRecord() throws Exception {

        List<SignInVO> signInVOList = signInMapper.getSignInRecord();
        Assert.assertNotNull(signInVOList);
    }
}