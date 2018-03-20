package cn.edu.ujs.repository;

import cn.edu.ujs.entity.ReadingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by DELL on 2018/1/6.
 */
@Component
public interface ReadingRoomRepository extends JpaRepository<ReadingRoom,String> {

    public List<ReadingRoom> findByFloorId(String floorId);
}
