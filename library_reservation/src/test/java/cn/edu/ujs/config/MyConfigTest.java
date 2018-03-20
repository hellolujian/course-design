package cn.edu.ujs.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by DELL on 2018/1/7.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MyConfigTest {

    @Autowired
    private MyConfig myConfig;

    @Test
    public void testGetOpenTime() throws Exception {
        String openTime = myConfig.getOpenTime();
        Assert.assertNotNull(openTime);
    }

    @Test
    public void testSetOpenTime() throws Exception {

        myConfig.setOpenTime("9:00");
        String openTime = myConfig.getOpenTime();
        Assert.assertNotNull(openTime);
    }
}