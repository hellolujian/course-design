package cn.edu.ujs.service.impl;

import cn.edu.ujs.entity.Floor;
import cn.edu.ujs.repository.FloorRepository;
import cn.edu.ujs.service.FloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by DELL on 2018/1/6.
 */
@Service
public class FloorServiceImpl implements FloorService {

    @Autowired
    private FloorRepository floorRepository;

    @Override
    public List<Floor> findAll() {
        return floorRepository.findAll();
    }
}
