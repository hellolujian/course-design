package cn.edu.ujs.mapper;

import cn.edu.ujs.VO.SeatVO;
import cn.edu.ujs.entity.Seat;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by DELL on 2018/1/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SeatMapperTest {

    @Autowired
    private SeatMapper seatMapper;

    @Test
    public void testGetSeat() throws Exception {

        List<SeatVO> seatVOList = seatMapper.getSeat();
        Assert.assertNotNull(seatVOList);
    }

    @Test
    public void testUpdateSeatStatus() throws Exception {
    }

    @Test
    public void testFindLastSeat() throws Exception {
        Seat seat = seatMapper.findLastSeat("1-01");
        Assert.assertNotNull(seat);
    }

    @Test
    public void testAddOneSeat() throws Exception {
        Integer result = seatMapper.addOneSeat("1-01-009","1-01");
        Assert.assertNotNull(result);
    }

    @Test
    public void testFastGetSeat() throws Exception {
        SeatVO seatVO = seatMapper.fastGetSeat();
        Assert.assertNotNull(seatVO);
    }

    @Test
    public void testDeleteSeatsOfReadingRoom() throws Exception {
        Integer integer = seatMapper.deleteSeatsOfReadingRoom("1-03");
        Assert.assertNotNull(integer);
    }

}