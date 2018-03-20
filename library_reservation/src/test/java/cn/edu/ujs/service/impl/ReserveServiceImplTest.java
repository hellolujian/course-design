package cn.edu.ujs.service.impl;

import cn.edu.ujs.entity.Reserve;
import cn.edu.ujs.service.ReserveService;
import cn.edu.ujs.util.TimeUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by DELL on 2017/12/27.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReserveServiceImplTest {

    @Autowired
    private ReserveService reserveService;

    @Test
    public void testInsert() throws Exception {

    }

    @Test
    public void testFindByUserId() throws Exception {

        System.out.println(reserveService.findByUserId("3140602023"));
    }

    @Test
    public void testFindByUserIdAndReserveTime() throws Exception {

       Assert.assertNotNull(reserveService.findByUserIdAndReserveTime("3140602023", TimeUtil.longStrToDate("2017-12-26 10:11:09")));
    }

    @Test
    public void testfindByReserveTimeStartingWith() throws Exception {
        //Date date = TimeUtil.stringToDate("2017-12-28");
       // Reserve reserve = reserveService.findByReserveTimeStartingWith(date);
        //Assert.assertNotNull(reserve);
    }

    @Test
    public void testfindByReserveTimeGreaterThan() throws Exception {
        //Date date = TimeUtil.stringToDate("2017-12-29");
        //Reserve reserve = reserveService.findByReserveTimeGreaterThan(date);
        //Assert.assertNotNull(reserve);
    }

    @Test
    public void testFindByReserveTimeBetween() throws Exception {
        Date startTime = TimeUtil.stringToDate("2017-12-28");
        Date endTime = TimeUtil.stringToDate("2017-12-29");
        List<Reserve> reserveList = reserveService.findByReserveTimeBetween(startTime,endTime);
        Assert.assertNotEquals(0,reserveList.size());
    }

    @Test
    public void testFindByUserIdAndReserveTimeBetween() throws Exception {
        Reserve reserve = reserveService.findByUserIdAndReserveTimeBetween("3140602023",TimeUtil.stringToDate("2017-12-28"),TimeUtil.addOneDay("2017-12-28"));
        Assert.assertNotNull(reserve);
    }

    @Test
    public void testFindByReserveTime() throws Exception {
        List<Reserve> reserveList = reserveService.findByReserveTime(TimeUtil.longStrToDate("2017-12-29 17:55:14"));
        Assert.assertNotEquals(0,reserveList.size());
    }
/*
    @Test
    //@Transactional
    public void testDeleteByIdIn() throws Exception {
        List<Integer> integers = Arrays.asList(189,190);
        Integer result = reserveService.deleteByIdIn(integers);
        Assert.assertNotNull(result);
    }
    */
}