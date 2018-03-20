package cn.edu.ujs.mapper;

import cn.edu.ujs.VO.SeatVO;
import cn.edu.ujs.entity.Seat;
import org.apache.ibatis.annotations.*;
import org.hibernate.annotations.SortComparator;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * Created by DELL on 2018/1/14.
 */
@Component
public interface SeatMapper {

    @Select("select seat.seat_id,seat.seat_status,seat_status.seat_status_name,reading_room.reading_room_name,floor.floor_name from seat,seat_status,reading_room,floor where seat.seat_status = seat_status.seat_status and reading_room.reading_room_id=seat.reading_room_id and floor.floor_id=reading_room.floor_id")
    @Results({
            @Result(column = "seat_id", property = "seatId"),
            @Result(column = "seat_status_name", property = "seatStatusName"),
            @Result(column = "reading_room_name", property = "readingRoomName"),
            @Result(column = "floor_name", property = "floorName")
    })
    List<SeatVO> getSeat();

    @Update("update seat set seat_status=#{seatStatus} where seat_id=#{seatId}")
    Integer updateSeatStatus(@Param("seatStatus") String seatStatus,
                             @Param("seatId") String seatId) ;

    //找出某个阅览室最后一个座位编号
    @Select("select * from seat where reading_room_id=#{readingRoomId} order by seat_id desc limit 1")
    @Results({
            @Result(column = "seat_id", property = "seatId")
    })
    Seat findLastSeat(String readingRoomId);

    //添加一个座位
    @Insert("insert into seat(seat_id,reading_room_id) values(#{seatId},#{readingRoomId})")
    Integer addOneSeat(@Param("seatId") String seatId,
                       @Param("readingRoomId") String readingRoomId);

    //删除一个座位
    @Delete("delete from seat where seat_id=#{seatId}")
    Integer deleteOneSeat(String seatId);

    //找出该座位所属阅览室
    @Select("select reading_room_id from seat where seat_id=#{seatId}")
    @Results({
            @Result(column = "reading_room_id", property = "readingRoomId")
    })
    String getReadingRoomBySeatId(String seatId);

    //快速预约时找出第一个空闲的座位
    @Select("select seat_id,reading_room.reading_room_name,floor.floor_name from seat,reading_room,floor where seat_status=0 and seat.reading_room_id=reading_room.reading_room_id and reading_room.floor_id=floor.floor_id limit 1")
    @Results({
            @Result(column = "seat_id", property = "seatId"),
            @Result(column = "reading_room_name", property = "readingRoomName"),
            @Result(column = "floor_name", property = "floorName")
    })
    SeatVO fastGetSeat();

    //删除某个阅览室的所有座位
    @Delete("delete from seat where reading_room_id=#{readingRoomId}")
    Integer deleteSeatsOfReadingRoom(String readingRoomId);

}
