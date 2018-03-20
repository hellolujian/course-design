package cn.edu.ujs.service;

import cn.edu.ujs.VO.SeatVO;
import cn.edu.ujs.entity.Reserve;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2017/12/26.
 */
public interface ReserveService {

    /**插入预约记录*/
    Reserve insert(Reserve reserve);

    /**根据读者编号查询预约记录*/
    List<Reserve> findByUserId(String userId);

    /**根据读者编号与预约时间查询预约记录*/
    Reserve findByUserIdAndReserveTime(String userId, Date reserveTime);

    /**根据座位编号与预约时间判断该座位是否被预约*/
    Reserve findByReserveTimeAndSeatId(Date reserveTime, String seatId);

    /**找出预约当天的读者*/
    Reserve findByReserveTimeStartingWith(Date reserveTime);

    Reserve findByReserveTimeGreaterThan(Date reserveTime);

    List<Reserve> findByReserveTimeBetween(Date startTime, Date endTime);

    Reserve findByUserIdAndReserveTimeBetween(String userId, Date startTime, Date endTime);

    /**找出指定日期的座位有没有被预约*/
    // TODO: 2017/12/29 应该是List，后面找出使用了这一方法的类，需要修改 
    List<Reserve> findBySeatIdAndReserveTimeBetween(String seatId, Date startTime, Date endTime);

    List<Reserve> findByReserveTime(Date date);

    Page<Reserve> findAll(Pageable pageable);

    List<Reserve> findAll();

    /**批量删除*/
    @Transactional
    Integer deleteByIdIn(List<Integer> integers);

    //快速预约
    SeatVO fastReserveSeat();
}
