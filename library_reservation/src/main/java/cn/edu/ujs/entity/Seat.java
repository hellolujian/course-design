package cn.edu.ujs.entity;


import antlr.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 座位
 * Created by DELL on 2017/12/25.
 */
@Entity
public class Seat {

    /**座位编号*/
    @Id
    private String seatId;

    /**座位使用状态*/
    private Integer seatStatus = 0;

    /**座位所属楼层*/
    private String readingRoomId;

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public Integer getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(Integer seatStatus) {
        this.seatStatus = seatStatus;
    }

    public String getReadingRoomId() {
        return readingRoomId;
    }

    public void setReadingRoomId(String readingRoomId) {
        this.readingRoomId = readingRoomId;
    }

    public Integer getLastSeatId() {
        String id = this.getSeatId().substring(5);
        return new Integer(id);
    }
}
