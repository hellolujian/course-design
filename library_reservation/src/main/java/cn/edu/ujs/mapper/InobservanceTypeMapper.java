package cn.edu.ujs.mapper;

import cn.edu.ujs.entity.InobservanceType;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by DELL on 2018/1/11.
 */
@Component
public interface InobservanceTypeMapper {

    @Select("select * from inobservance_type")
    @Results({
            @Result(column = "inobservance_type_id", property = "inobservanceTypeId"),
            @Result(column = "inobservance_type_name", property = "inobservanceTypeName")
    })
    List<InobservanceType> getInobservanceType();
}
