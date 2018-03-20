package cn.edu.ujs.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2018/1/14.
 */
@Component
public interface FloorMapper {

    //找出所有的楼层，提供前台下拉列表数据
    @Select("select floor_id,floor_name from floor")
    @Results({
            @Result(column = "floor_id", property = "floorId"),
            @Result(column = "floor_name", property = "floorName")
    })
    List<Map<String,String>> getFloorName();
}
