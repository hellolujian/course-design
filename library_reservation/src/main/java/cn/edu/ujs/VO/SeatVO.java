package cn.edu.ujs.VO;

import javax.persistence.Id;

/**
 * Created by DELL on 2018/1/14.
 */
public class SeatVO {

    /**座位编号*/
    private String seatId;

    /**座位使用状态*/
    private String seatStatusName;

    /**座位所属阅览室*/
    private String readingRoomName;

    /**座位所属楼层*/
    private String FloorName;

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public String getSeatStatusName() {
        return seatStatusName;
    }

    public void setSeatStatusName(String seatStatusName) {
        this.seatStatusName = seatStatusName;
    }

    public String getReadingRoomName() {
        return readingRoomName;
    }

    public void setReadingRoomName(String readingRoomName) {
        this.readingRoomName = readingRoomName;
    }

    public String getFloorName() {
        return FloorName;
    }

    public void setFloorName(String floorName) {
        FloorName = floorName;
    }
}
