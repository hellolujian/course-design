package cn.edu.ujs.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Time;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by DELL on 2017/12/27.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TimeUtilTest {

    private static final Logger logger = LoggerFactory.getLogger(TimeUtil.class);

    @Test
    public void testGetDateShort() throws Exception {

    }

    @Test
    public void testStringToDate() throws Exception {

    }

    @Test
    public void testLongStrToDate() throws Exception {

    }

    @Test
    public void testIsDateBefore() throws Exception {

    }

    @Test
    public void testIsDate1BeforeDate2() throws Exception {

        Assert.assertEquals(false,TimeUtil.isDate1BeforeDate2("2018-11-11 11:11:11","2018-11-11 11:11:11"));
    }

    @Test
    public void testIsDateAfter() throws Exception {

    }

    @Test
    public void testAddOneDay() throws Exception {

        Date tomorrow = TimeUtil.addOneDay("2017-12-31");
        logger.info(tomorrow.toString());
    }

    @Test
    public void testAddMinute() throws Exception {
        Date date = TimeUtil.addMinute(30);
        logger.info("现在时间是："+TimeUtil.dateToLongStr(date));
        logger.info("时："+TimeUtil.getHourByDate(date)+" 分："+TimeUtil.getMinuteByDate(date)+" 秒："+ TimeUtil.getSecondByDate(date)
        +" 年："+TimeUtil.getYearByDate(date)+" 月："+TimeUtil.getMonthByDate(date)+" 日："+TimeUtil.getDayByDate(date));
    }

    @Test
    public void testIsEqual() throws Exception {
        boolean flag = TimeUtil.isEqual("2017-12-12 12:12:12","2017-12-12 12:12:13");
        Assert.assertEquals(flag,true);
    }

    @Test
    public void testCountDays() throws Exception {
        long days = TimeUtil.countDays(TimeUtil.longStrToDate("2017-12-12 12:12:20"),TimeUtil.longStrToDate("2017-12-13 12:12:12"));
                Assert.assertNotEquals(0,days);
    }

    @Test
    public void testCountHours() throws Exception {
        long hours = TimeUtil.countHours(TimeUtil.longStrToDate("2017-12-12 12:12:21"),
                TimeUtil.longStrToDate("2017-12-13 12:12:20"));
        Assert.assertNotEquals(0,hours);
    }

    @Test
    public void testAddMinute2() throws Exception {
        Date date = TimeUtil.addMinute(TimeUtil.stringToDate("2018-01-01"),30);
        Assert.assertNotNull(date);
    }

}