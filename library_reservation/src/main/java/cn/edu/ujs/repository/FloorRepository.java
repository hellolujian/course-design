package cn.edu.ujs.repository;

import cn.edu.ujs.entity.Floor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * Created by DELL on 2018/1/6.
 */
@Component
public interface FloorRepository extends JpaRepository<Floor,String> {


}
