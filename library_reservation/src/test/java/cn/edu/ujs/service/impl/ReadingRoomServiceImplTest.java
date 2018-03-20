package cn.edu.ujs.service.impl;

import cn.edu.ujs.service.ReadingRoomService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by DELL on 2018/1/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReadingRoomServiceImplTest {

    @Autowired
    private ReadingRoomService readingRoomService;

    @Test
    public void testFindAll() throws Exception {

    }

    @Test
    public void testFindByFloorId() throws Exception {

    }

    @Test
    public void testAddSeat2ReadingRoom() throws Exception {

        readingRoomService.addSeat2ReadingRoom("1-02",10);
        readingRoomService.addSeat2ReadingRoom("2-01",10);
        readingRoomService.addSeat2ReadingRoom("2-02",10);
        readingRoomService.addSeat2ReadingRoom("2-03",10);
        readingRoomService.addSeat2ReadingRoom("3-01",10);
        readingRoomService.addSeat2ReadingRoom("3-02",10);
        readingRoomService.addSeat2ReadingRoom("3-03",10);
        readingRoomService.addSeat2ReadingRoom("4-01",10);
        readingRoomService.addSeat2ReadingRoom("4-02",10);
        readingRoomService.addSeat2ReadingRoom("5-01",10);
        readingRoomService.addSeat2ReadingRoom("5-02",10);
        readingRoomService.addSeat2ReadingRoom("5-03",10);
        readingRoomService.addSeat2ReadingRoom("6-01",10);
        readingRoomService.addSeat2ReadingRoom("6-02",10);


        System.out.println("dsf");
    }

    @Test
    public void testAddOneReadingRoom() throws Exception {
        readingRoomService.addOneReadingRoom("sdf","1");
        System.out.println("sdf");
    }
}