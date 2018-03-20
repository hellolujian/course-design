package cn.edu.ujs.VO;

import cn.edu.ujs.entity.ReadingRoom;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by DELL on 2018/1/6.
 */
public class FloorVO {

    /**楼层名称*/
    @JsonProperty("name")
    private String floorName;

    /**阅览室列表*/
    @JsonProperty("readingRooms")
    private List<ReadingRoomVO> readingRoomVOList;

    /**座位总数*/
    private Integer count;

    /**可用座位数量*/
    private Integer usableCount;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getUsableCount() {
        return usableCount;
    }

    public void setUsableCount(Integer usableCount) {
        this.usableCount = usableCount;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public List<ReadingRoomVO> getReadingRoomVOList() {
        return readingRoomVOList;
    }

    public void setReadingRoomVOList(List<ReadingRoomVO> readingRoomVOList) {
        this.readingRoomVOList = readingRoomVOList;
    }
}
