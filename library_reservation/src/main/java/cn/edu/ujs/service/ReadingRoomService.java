package cn.edu.ujs.service;

import cn.edu.ujs.entity.ReadingRoom;

import java.util.List;

/**
 * Created by DELL on 2018/1/14.
 */
public interface ReadingRoomService {

    /**查询所有阅览室*/
    public List<ReadingRoom> findAll();

    /**根据阅楼层编号查询*/
    public List<ReadingRoom> findByFloorId(String floorId);

    /**向阅览室添加座位*/
    public Integer addSeat2ReadingRoom(String readingRoomId, Integer count);

    /**添加一个阅览室*/
    public void addOneReadingRoom(String readingRoomName, String floorId);

    /**添加一个阅览室*/
    public ReadingRoom addOneReadingRoom(ReadingRoom readingRoom);
}
