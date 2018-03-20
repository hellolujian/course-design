package cn.edu.ujs.mapper;

import cn.edu.ujs.VO.ReserveVO;
import cn.edu.ujs.VO.SignInVO;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by DELL on 2018/1/11.
 */
@Component
public interface SignInMapper {

    //查询所有签到记录
    @Select("select sign_in.id,sign_in.user_id,user.user_name,sign_in.seat_id,sign_in.reserve_time,sign_in.sign_in_time,sign_in_type.sign_in_type_name from sign_in,sign_in_type,user where sign_in.sign_in_type=sign_in_type.sign_in_type_id and user.user_id=sign_in.user_id")
    @Results({
            @Result(column = "user_id", property = "userId"),
            @Result(column = "user_name", property = "userName"),
            @Result(column = "seat_id", property = "seatId"),
            @Result(column = "reserve_time", property = "reserveTime"),
            @Result(column = "sign_in_time", property = "signInTime"),
            @Result(column = "sign_in_type_name", property = "signInTypeName")
    })
    List<SignInVO> getSignInRecord();
}
