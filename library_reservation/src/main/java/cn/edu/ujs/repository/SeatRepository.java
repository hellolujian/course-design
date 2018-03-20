package cn.edu.ujs.repository;

import cn.edu.ujs.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by DELL on 2017/12/25.
 */
@Component
public interface SeatRepository extends JpaRepository<Seat, String> {

    /**根据状态查询座位列表*/
    List<Seat> findBySeatStatus(Integer seatStatus);

    /**根据阅览室查询座位*/
    List<Seat> findByReadingRoomId(String readingRoomId);

    /**查询不是某一状态的座位列表*/
    List<Seat> findBySeatStatusNot(Integer seatStatus);

    /**查询第一条记录,可用的座位*/
    Seat findTopBySeatStatus(Integer seatStatus);

    /**根据阅览室查询第一条记录*/
    Seat findTopByReadingRoomId(String readingRoomId);

    /**查询阅览室可用的座位数量*/
    //注：Seat是class名，并不是表名
    @Query("select count(s) from Seat s where s.seatStatus=?1 and s.readingRoomId=?2")
    Integer findUsableCountByReadingRoom(Integer seatStatuse, String readingRoomId);

    /**查询某个阅览室的最后一个座位的编号
    @Query("select * from Seat s where s.readingRoomId =?1 order by s.seatId desc limit 1")
    Seat findLastSeat(String readRoomId);*/
}
