package cn.edu.ujs.service;

import cn.edu.ujs.entity.Seat;

import java.util.List;

/**
 * Created by DELL on 2017/12/25.
 */
public interface SeatService {

    /**添加(更新）一个座位*/
    Seat save(Seat seat);

    /**修改座位状态信息*/
    void updateSeatStatus(String SeatId, Integer seatStatus);

    /**根据座位号查询*/
    public Seat findOne(String seatId);

    /**根据座位状态查询*/
    public List<Seat> findBySeatStatus(Integer seatStatus);

    /**查询没有被释放的座位*/
    public List<Seat> findBySeatStatusNot(Integer seatStatus);

    /**系统自动分配一个座位*/
    public Seat getOneSeat(Integer seatStatus);

    /**系统根据阅览室自动分配一个座位*/
    public Seat getOneSeatByReadingRoom(String readingRoomId);

    /**查询所有座位*/
    public List<Seat> findAll();

    /**根据阅览室编号查询*/
    public List<Seat> findByReadingRoomId(String readingRoomId);

    /**查询阅览室里可用的座位数量*/
    public Integer findUsableCountByReadingRoom(Integer seatStatus, String readingRoomId);

    /**删除一个座位*/
    public Integer deleteOneSeat(String seatId);

    /**批量删除座位*/
    public Integer deleteManySeats(List<String> seatIdList);

}
