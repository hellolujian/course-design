package cn.edu.ujs.repository;

import cn.edu.ujs.entity.Inobservance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by DELL on 2017/12/29.
 */
@Component
public interface InobservanceRepository extends JpaRepository<Inobservance,Integer> {

    public List<Inobservance> findByUserId(String userId);


}
