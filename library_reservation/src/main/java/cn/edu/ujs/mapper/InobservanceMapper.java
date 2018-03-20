package cn.edu.ujs.mapper;

import cn.edu.ujs.VO.InobservanceVO;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by DELL on 2018/1/11.
 */
@Component
public interface InobservanceMapper {

    @Select("select id,inobservance.user_id,user.user_name,seat_id,reserve_time,inobservance_type.inobservance_type_name,create_time from inobservance,user,inobservance_type where user.user_id=inobservance.user_id and inobservance.inobservance_type_id=inobservance_type.inobservance_type_id")
    @Results({
            @Result(column = "user_id", property = "userId"),
            @Result(column = "user_name", property = "userName"),
            @Result(column = "seat_id", property = "seatId"),
            @Result(column = "reserve_time", property = "reserveTime"),
            @Result(column = "inobservance_type_name", property = "inobservanceTypeName"),
            @Result(column = "create_time", property = "createTime")
    })
    List<InobservanceVO> getInobservance();
}
