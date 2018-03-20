package cn.edu.ujs.mapper;

import cn.edu.ujs.VO.ReserveVO;
import cn.edu.ujs.entity.Reserve;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.ognl.ObjectElementsAccessor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2018/1/2.
 */
@Component
public interface ReserveMapper {

    @Select("select * from reservation where user_id=#{userId} order by reserve_time asc")
    @Results({
            @Result(column = "user_id", property = "userId"),
            @Result(column = "seat_id", property = "seatId"),
            @Result(column = "reserve_time", property = "reserveTime")
    })
    List<Reserve> findByUserIdOrderByDesc(String userId);

    @Select("select * from reservation where user_id=#{userId} and date_format(reserve_time,'%Y-%m-%d')=#{reserveTime} order by reserve_time desc")
    @Results({
            @Result(column = "user_id", property = "userId"),
            @Result(column = "seat_id", property = "seatId"),
            @Result(column = "reserve_time", property = "reserveTime")
    })
    List<Reserve> findByUserIdAndReserveTime(@Param("userId") String userId,
                                             @Param("reserveTime") String reserveTime);

    @Select("select * from reservation where date_format(reserve_time,'%Y-%m-%d')=#{reserveTime}")
    @Results({
            @Result(column = "user_id", property = "userId"),
            @Result(column = "seat_id", property = "seatId"),
            @Result(column = "reserve_time", property = "reserveTime")
    })
    List<Reserve> findByReserveTime(@Param("reserveTime") String reserveTime);

    //select user.user_id,user.college_id,college.college_name from user,reservation,college where user.user_id = reservation.user_id and user.college_id=college.college_id;
    @Select("select college.college_name as name,count(user.college_id) as value from user,reservation,college where user.user_id = reservation.user_id and user.college_id=college.college_id group by user.college_id")
    List<Map<String,Integer>> countByCollege();

    @Select("select degree.degree_name as name,count(user.college_id) as value from user,reservation,degree where user.user_id = reservation.user_id and user.degree_id=degree.degree_id group by user.degree_id")
    List<Map<String,Integer>> countByDegree();

    //根据阅览室
    // TODO: 2018/1/9 这里是通过截取字符串的形式来获得阅览室编号，如果后续编号长度改变，此SQL语句也需要改变
    @Select("select reading_room_name as name,count(left(seat_id,4)) as value from reservation,reading_room where reading_room.reading_room_id=left(seat_id,4) group by left(seat_id,4)")
    List<Map<String,Integer>> countByReadingRoom();

    //根据座位
    @Select("select seat_id as name,count(seat_id) as value from reservation group by seat_id")
    List<Map<String,Integer>> countBySeat();

    //查询所有预约记录
    @Select("select reservation.id,user.user_id,user.user_name,seat_id,reserve_time,create_time from reservation,user where reservation.user_id=user.user_id")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "user_name", property = "userName"),
            @Result(column = "seat_id", property = "seatId"),
            @Result(column = "reserve_time", property = "reserveTime"),
            @Result(column = "create_time", property = "createTime")
    })
    List<ReserveVO> getReserveRecord();

    //删除一条记录
    @Delete("delete from reservation where id=#{id}")
    Integer deleteById(Integer id);

    //删除多条记录
    // TODO: 2018/1/10 此方法是错的 
    @Delete("delete from reservation where id in #{idList}")
    Integer deleteByIdList(List<Integer> idList);

    //模糊查询
    @Select("select reservation.id,user.user_id,user.user_name,seat_id,reserve_time,create_time from reservation,user where reservation.user_id=user.user_id and id like #{content} or user_id like #{content}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "user_name", property = "userName"),
            @Result(column = "seat_id", property = "seatId"),
            @Result(column = "reserve_time", property = "reserveTime"),
            @Result(column = "create_time", property = "createTime")
    })
    List<Reserve> findByLike(@Param("content") String content);

    //根据阅览室select reading_room.reading_room_name,reserve_time from reservation,seat,reading_room where reservation.seat_id=seat.seat_id and seat.reading_room_id=reading_room.reading_room_id order by reservation.id

    //分星期统计
    // TODO: 2018/1/16 没有预约的那一天不会显示，后续与my_week左连接改进 
    @Select("select (case date_format(reserve_time,'%w') when 1 then '周一' when 2 then '周二' when 3 then '周三' when 4 then '周四' when 5 then '周五' when 6 then '周六' when 0 then '周日' end) as name,count(date_format(reserve_time,'%w')) as value  from reservation group by name order by date_format(reserve_time,'%w')")
    @Results({
            @Result(column = "name", property = "name"),
            @Result(column = "value", property = "value")
    })
    List<Map<String,Integer>> countByWeek2();
    
    //那一周的某天没有会显示0
    // TODO: 2018/1/16 后续改成范围在一段时间内
    @Select("select mw.week as name,count(r.id)value " +
            "from my_week mw left join reservation r " +
            "on date_format(r.reserve_time,'%w')=mw.id group by mw.id")
    @Results({
            @Result(column = "name", property = "name"),
            @Result(column = "value", property = "value")
    })
    List<Map<String,Integer>> countByWeek();

    //显示某一天的时间段（没有自动补0）
    /*
    @Select("select concat(LPAD(hour(reserve_time),2,'0'),':00') as name,count(*) as value from reservation where date(reserve_time) = date_sub('2018-01-13',interval 0 day)")
    @Results({
            @Result(column = "name", property = "name"),
            @Result(column = "value", property = "value")
    })
    List<Map<String,Integer>> countByTime(String reserveTime);
    */

    //显示某一天的时间段（自动补0）
    @Select("select concat(date_format(md.date,'%H'),':00') name,count(r.id)value from my_date md left join (select * from reservation where date_format(reserve_time,'%Y-%m-%d') = #{reserveTime}) r on date_format(md.date,'%H') = date_format(r.reserve_time,'%H') group by name")
    @Results({
            @Result(column = "name", property = "name"),
            @Result(column = "value", property = "value")
    })
    List<Map<String,Integer>> countByTime(String reserveTime);

    //统计每个阅览室每周的七天预约了多少座位：
    // select reading_room_id id,reading_room_name name,date_format(reserve_time,'%w')week,count(*)value from reservation,reading_room where reading_room.reading_room_id=left(seat_id,4) group by name,week

}
