package cn.edu.ujs.repository;

import cn.edu.ujs.entity.Blacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Created by DELL on 2017/12/26.
 */
@Component
public interface BlacklistRepository extends JpaRepository<Blacklist,Integer> {

    Blacklist findByUserIdAndInobservanceTypeId(String UserId, Integer inobservanceTypeId);

    List<Blacklist> findByUserId(String userId);

    Integer deleteByUserIdAndInobservanceTypeId(String userId, Integer inobservanceTypeId);

    //@Transactional
    Integer deleteByCount(Integer count);

    Integer deleteByIdIn(List<Integer> idList);
}
