package cn.edu.ujs.service;

import cn.edu.ujs.entity.Blacklist;

import java.util.List;

/**
 * Created by DELL on 2017/12/26.
 */
public interface BlacklistService {

    /**查询黑名单*/
    List<Blacklist> findAll();

    /**插入黑名单*/
    Blacklist save(String userId,Integer inobservanceTypeId);

    /**根据读者编号、违规类别查询*/
    Blacklist findByUserIdAndInobservanceTypeId(String UserId, Integer inobservanceTypeId);

    /**根据读者编号查询*/
    List<Blacklist> findByUserId(String userId);

    /**判断该读者是否被禁止预约*/
    Blacklist isForbidden(String userId);

    /**找出被禁止的那一条记录*/
    Blacklist findForbiddenRecord(String userId);

    Integer deleteByUserIdAndInobservanceTypeId(String userId, Integer inobservanceTypeId);

    /**根据id批量删除*/
    Integer deleteByIdIn(List<Integer> idList);
}
