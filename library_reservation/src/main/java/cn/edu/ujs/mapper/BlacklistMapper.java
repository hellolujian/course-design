package cn.edu.ujs.mapper;

import cn.edu.ujs.VO.BlacklistVO;
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
public interface BlacklistMapper {

    //查询所有黑名单
    @Select("select blacklist.id,blacklist.user_id,user.user_name,inobservance_type.inobservance_type_name,count,update_time,create_time from blacklist,user,inobservance_type where blacklist.user_id=user.user_id and blacklist.inobservance_type_id=inobservance_type.inobservance_type_id")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "user_name", property = "userName"),
            @Result(column = "inobservance_type_name", property = "inobservanceTypeName"),
            @Result(column = "update_time", property = "updateTime"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "count", property = "count")
    })
    List<BlacklistVO> getBlacklist();
}
