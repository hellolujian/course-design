package cn.edu.ujs.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by DELL on 2018/1/10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReserveRepositoryTest {

    @Autowired
    private ReserveRepository reserveRepository;

    @Test
    @Transactional
    public void testDeleteById() throws Exception {

        Integer integer = reserveRepository.deleteById(189);
        Assert.assertNotNull(integer);
    }
/*
    @Test
    //@Transactional
    public void testDeleteByIdIn() throws Exception {
        Integer integer = reserveRepository.deleteByIdIn(Arrays.asList(189,190));
        Assert.assertNotNull(integer);
    }
    */
}