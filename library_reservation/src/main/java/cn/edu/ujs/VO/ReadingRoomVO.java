package cn.edu.ujs.VO;

import cn.edu.ujs.entity.Seat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by DELL on 2018/1/6.
 */
public class ReadingRoomVO {

    /**阅览室名称*/
    @JsonProperty("name")
    private String readingRoomName;

    /**阅览室座位总数*/
    @JsonProperty("count")
    private Integer count;

    /**阅览室座位可用数量*/
    @JsonProperty("usableCount")
    private Integer usableCount;

    /**座位列表*/
    @JsonProperty("seats")
    private List<Seat> seatList;

    public String getReadingRoomName() {
        return readingRoomName;
    }

    public void setReadingRoomName(String readingRoomName) {
        this.readingRoomName = readingRoomName;
    }

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

    public List<Seat> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<Seat> seatList) {
        this.seatList = seatList;
    }
}
