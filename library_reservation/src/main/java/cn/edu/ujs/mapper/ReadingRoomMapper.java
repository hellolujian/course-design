package cn.edu.ujs.mapper;

import cn.edu.ujs.VO.ReadingRoomVO2;
import cn.edu.ujs.entity.ReadingRoom;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2018/1/14.
 */
@Component
public interface ReadingRoomMapper {

    @Select("select reading_room_id,reading_room_name,floor_name,seat_count from reading_room,floor where reading_room.floor_id = floor.floor_id")
    @Results({
            @Result(column = "reading_room_id", property = "readingRoomId"),
            @Result(column = "reading_room_name", property = "readingRoomName"),
            @Result(column = "floor_name", property = "floorName"),
            @Result(column = "seat_count", property = "seatCount")
    })
    List<ReadingRoomVO2> getReadingRoom();

    //添加一个阅览室
    @Insert("insert into reading_room(reading_room_id,reading_room_name,floor_id) values(#{readingRoomId},#{readingRoomName},#{floorId})")
    @Results({
            @Result(column = "reading_room_id", property = "readingRoomId")
    })
    Integer addOneReadingRoom(@Param("readingRoomId") String readingRoomId,
                              @Param("readingRoomName") String readingRoomName,
                              @Param("floorId") String floorId);

    //找出某个楼层最后一个阅览室编号
    @Select("select reading_room_id from reading_room where floor_id=#{floorId} order by reading_room_id desc limit 1")
    @Results({
            @Result(column = "reading_room_id", property = "readingRoomId")
    })
    String findLastReadingRoom(String floorId);

    //更新阅览室名称
    @Update("update reading_room set reading_room_name=#{readingRoomName} where reading_room_id=#{readingRoomId}")
    Integer updateReadingRoom(@Param("readingRoomName") String readingRoomName,
                              @Param("readingRoomId") String readingRoomId);

    //删除阅览室
    @Delete("delete from reading_room where reading_room_id=#{readingRoomId}")
    Integer deleteReadingRoom(String readingRoomId);

    //根据楼层查找阅览室
    @Select("select reading_room_id,reading_room_name from reading_room where floor_id=#{floorId}")
    @Results({
            @Result(column = "reading_room_id", property = "readingRoomId"),
            @Result(column = "reading_room_name", property = "readingRoomName")
    })
    List<Map<String,String>> getReadingRoomByFloor(String floorId);
}
