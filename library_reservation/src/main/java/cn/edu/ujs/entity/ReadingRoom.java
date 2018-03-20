package cn.edu.ujs.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by DELL on 2018/1/6.
 */
@Entity
public class ReadingRoom {

    /**阅览室编号*/
    @Id
    private String readingRoomId;

    /**阅览室名称*/
    private String readingRoomName;

    /**楼层编号*/
    private String floorId;

    /**座位总量*/
    private Integer seatCount;

    public String getReadingRoomId() {
        return readingRoomId;
    }

    public void setReadingRoomId(String readingRoomId) {
        this.readingRoomId = readingRoomId;
    }

    public String getReadingRoomName() {
        return readingRoomName;
    }

    public void setReadingRoomName(String readingRoomName) {
        this.readingRoomName = readingRoomName;
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public Integer getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(Integer seatCount) {
        this.seatCount = seatCount;
    }
}
