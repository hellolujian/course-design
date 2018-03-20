package cn.edu.ujs.mapper;

import cn.edu.ujs.VO.ReserveVO;
import cn.edu.ujs.entity.Reserve;
import cn.edu.ujs.util.TimeUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Time;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by DELL on 2018/1/2.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReserveMapperTest {

    @Autowired
    private ReserveMapper reserveMapper;

    @Test
    public void testFindByUserId() throws Exception {

        List<Reserve> reserveList = reserveMapper.findByUserIdOrderByDesc("3140602023");
        Assert.assertNotEquals(0,reserveList.size());
    }

    @Test
    public void testFindByUserIdAndReserveTime() throws Exception {

        List<Reserve> reserveList = reserveMapper.findByUserIdAndReserveTime("3130602032","2018-01-17");
        Assert.assertNotEquals(0,reserveList.size());
    }

    @Test
    public void testFindByReserveTime() throws Exception {
        List<Reserve> reserveList = reserveMapper.findByReserveTime("2018-01-08");
        Assert.assertNotEquals(0,reserveList.size());
    }

    @Test
    public void testCountByCollege() throws Exception {
        List<Map<String,Integer>> maps = reserveMapper.countByCollege();
        System.out.println(maps);
        Assert.assertNotNull(maps);
    }

    @Test
    public void testCountByDegree() throws Exception {
        List<Map<String, Integer>> maps = reserveMapper.countByDegree();
        Assert.assertNotNull(maps);
    }

    @Test
    public void testCountBySeat() throws Exception {
        List<Map<String, Integer>> maps = reserveMapper.countBySeat();
        Assert.assertNotNull(maps);
    }

    @Test
    public void testCountByReadingRoom() throws Exception {
        List<Map<String,Integer>> maps = reserveMapper.countByReadingRoom();
        Assert.assertNotNull(maps);
    }

    @Test
    public void testGetReserveRecord() throws Exception {
        List<ReserveVO> reserveVOList = reserveMapper.getReserveRecord();
        List<ReserveVO> reserveVOs = ReserveVO.setTag(reserveVOList);
        Assert.assertNotNull(reserveVOList);
    }

    @Test
    public void testDeleteById() throws Exception {
        Integer result = reserveMapper.deleteById(187);
        //Assert.assertNotNull(result);
    }

    @Test
    public void testDeleteByIdList() throws Exception {
        List<Integer> integers = Arrays.asList(189,190);

        Integer result = reserveMapper.deleteByIdList(integers);
        Assert.assertNotNull(result);
    }

    @Test
    public void testFindByLike() throws Exception {
        List<Reserve> reserveList = reserveMapper.findByLike("%19%");
        Assert.assertNotNull(reserveList);
    }

    @Test
    public void testCountByTime() throws Exception {
        List<Map<String,Integer>> maps = reserveMapper.countByTime("2018-01-12");
        Assert.assertNotNull(maps);
    }
}