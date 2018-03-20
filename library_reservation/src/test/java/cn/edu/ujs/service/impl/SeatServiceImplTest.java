package cn.edu.ujs.service.impl;

import cn.edu.ujs.entity.Seat;
import cn.edu.ujs.enums.SeatStatusEnum;
import cn.edu.ujs.service.SeatService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by DELL on 2017/12/29.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SeatServiceImplTest {

    @Autowired
    private SeatService seatService;

    @Test
    public void testSave() throws Exception {

    }

    @Test
    public void testUpdateSeatStatus() throws Exception {

    }

    @Test
    public void testFindOne() throws Exception {
        Seat seat = seatService.findOne("101-1001-10001");
        Assert.assertNotNull(seat);
    }

    @Test
    public void testFindBySeatStatus() throws Exception {

        List<Seat> seatList = seatService.findBySeatStatus(1);
        Assert.assertNotEquals(0,seatList.size());
    }

    @Test
    public void testFindBySeatStatusNot() throws Exception {

        List<Seat> seatList = seatService.findBySeatStatusNot(SeatStatusEnum.BEING_SIGN_IN.getCode());
        Assert.assertNotEquals(0,seatList.size());
    }

    @Test
    public void testGetOneSeat() throws Exception {
        Seat seat = seatService.getOneSeat(0);
        Assert.assertNotNull(seat);
    }

    @Test
    public void testFindCountBySeatStatus() throws Exception {
        Integer count = seatService.findUsableCountByReadingRoom(SeatStatusEnum.NOT_BEING_RESERVED.getCode(),"101-1001");
        Assert.assertNotEquals(new Integer(0),count);
    }
}