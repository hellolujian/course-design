package cn.edu.ujs.service.impl;

import cn.edu.ujs.entity.Blacklist;
import cn.edu.ujs.enums.InobservanceTypeEnum;
import cn.edu.ujs.service.BlacklistService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.SystemProfileValueSource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by DELL on 2017/12/27.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BlacklistServiceImplTest {

    @Autowired
    BlacklistService blacklistService;

    @Test
    public void testFindAll() throws Exception {

    }

    @Test
    public void testSave() throws Exception {

        Blacklist blacklist = blacklistService.save("3140602024", InobservanceTypeEnum.NOT_FIRST_SIGN_IN.getCode());
        Assert.assertNotNull(blacklist);
    }

    @Test
    public void testFindByUserIdAndInobservanceTypeId() throws Exception {

    }

    @Test
    public void testFindByUserId() throws Exception {

        List<Blacklist> blacklistList = blacklistService.findByUserId("3140602023");
        System.out.println("\n"+blacklistList.size());
    }

    @Test
    public void testIsForbidden() throws Exception {

    }

    @Test
    public void testDeleteByUserIdAndInobservanceTypeId() throws Exception {
        Integer blacklist = blacklistService.deleteByUserIdAndInobservanceTypeId("3140602023",1);
        Assert.assertNotNull(blacklist);
    }
}