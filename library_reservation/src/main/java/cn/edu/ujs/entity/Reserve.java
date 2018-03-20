package cn.edu.ujs.entity;

import cn.edu.ujs.util.serializer.Date2LongSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by DELL on 2017/12/26.
 */
@Entity
@Table(name = "reservation")
public class Reserve {

    /**主键*/
    @Id
    @GeneratedValue
    private Integer id;

    /**读者编号*/
    private String userId;

    /**座位编号*/
    private String seatId;

    /**预约时间*/
    private Date reserveTime;

    /**创建时间*/
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public Date getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(Date reserveTime) {
        this.reserveTime = reserveTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Reserve{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", seatId='" + seatId + '\'' +
                ", reserveTime=" + reserveTime +
                ", createTime=" + createTime +
                '}';
    }
}
