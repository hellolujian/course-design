package cn.edu.ujs.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.Times;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.SystemProfileValueSource;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.sql.Timestamp;

import static org.junit.Assert.*;

/**
 * Created by DELL on 2017/12/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReserveControllerTest {

    @Test
    public void testTime() throws Exception{
        System.out.println("hhhhsdfdsfsdfsdfsd");
        System.out.println("hhhhsdfdsfsdfsdfsd");
        Calendar now = Calendar.getInstance();
        System.out.println("年："+now.get(Calendar.YEAR));
        System.out.println("月："+now.get(Calendar.MONTH));
        System.out.println("日："+now.get(Calendar.DAY_OF_MONTH));

        Date d = new Date();
        System.out.println(d);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = simpleDateFormat.format(d);
        System.out.println("格式化后的日期："+dateNowStr);

        String str = "2018-1-1 12:12:12";
        Date today = simpleDateFormat.parse(str);
        System.out.println("字符串转成日期："+today);

    }
}