package cn.edu.ujs.mapper;

import cn.edu.ujs.VO.SignInVO;
import cn.edu.ujs.VO.SignOutVO;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by DELL on 2018/1/11.
 */
@Component
public interface SignOutMapper {

    //查询所有签离记录
    @Select("select sign_out.id,sign_out.user_id,user.user_name,sign_out.seat_id,sign_out.reserve_time,sign_out.sign_out_time,sign_out_type.sign_out_type_name from sign_out,sign_out_type,user where sign_out.sign_out_type=sign_out_type.sign_out_type_id and user.user_id=sign_out.user_id order by sign_out.id")
    @Results({
            @Result(column = "user_id", property = "userId"),
            @Result(column = "user_name", property = "userName"),
            @Result(column = "seat_id", property = "seatId"),
            @Result(column = "reserve_time", property = "reserveTime"),
            @Result(column = "sign_out_time", property = "signOutTime"),
            @Result(column = "sign_out_type_name", property = "signOutTypeName")
    })
    List<SignOutVO> getSignOutRecord();
}
