package cn.edu.ujs.service;

import cn.edu.ujs.entity.Floor;

import java.util.List;

/**
 * Created by DELL on 2018/1/6.
 */
public interface FloorService {

    /**查询所有楼层*/
    public List<Floor> findAll();

}
