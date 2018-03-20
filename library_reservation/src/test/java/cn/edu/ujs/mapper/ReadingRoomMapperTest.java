package cn.edu.ujs.mapper;

import cn.edu.ujs.entity.ReadingRoom;
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
public class ReadingRoomMapperTest {

    @Autowired
    private ReadingRoomMapper readingRoomMapper;

    @Test
    public void testGetReadingRoom() throws Exception {

    }

    @Test
    public void testAddOneReadingRoom() throws Exception {
        Integer readingRoom = readingRoomMapper.addOneReadingRoom("15","ds","1");
        Assert.assertNotNull(readingRoom);
    }

    @Test
    public void testFindLastReadingRoom() throws Exception {
        String readingRoomId = readingRoomMapper.findLastReadingRoom("1");
        Assert.assertNotNull(readingRoomId);
    }
}