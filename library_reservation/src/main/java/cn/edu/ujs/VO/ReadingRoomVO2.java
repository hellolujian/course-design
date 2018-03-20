package cn.edu.ujs.VO;

/**
 * Created by DELL on 2018/1/14.
 */
public class ReadingRoomVO2 {

    private String readingRoomId;

    /**阅览室名称*/
    private String readingRoomName;

    /**楼层名称*/
    private String floorName;

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

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public Integer getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(Integer seatCount) {
        this.seatCount = seatCount;
    }
}
