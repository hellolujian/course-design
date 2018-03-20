package cn.edu.ujs.repository;

import cn.edu.ujs.entity.Blacklist;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Created by DELL on 2018/1/1.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BlacklistRepositoryTest {

    @Autowired
    private BlacklistRepository blacklistRepository;

    @Test
    //@Transactional
    public void testDeleteByCount() throws Exception {
        int result = blacklistRepository.deleteByCount(2);
        Assert.assertNotNull(result);
    }
}