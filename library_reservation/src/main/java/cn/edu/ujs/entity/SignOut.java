package cn.edu.ujs.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by DELL on 2017/12/28.
 */
@Entity
public class SignOut {

    @Id
    @GeneratedValue
    private Integer id;

    /**用户编号*/
    private String userId;

    /**座位编号*/
    private String seatId;

    /**预约时间*/
    private Date reserveTime;

    /**签离类别*/
    private int signOutType;

    /**签离时间*/
    private Date signOutTime;

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

    public int getSignOutType() {
        return signOutType;
    }

    public void setSignOutType(int signOutType) {
        this.signOutType = signOutType;
    }

    public Date getSignOutTime() {
        return signOutTime;
    }

    public void setSignOutTime(Date signOutTime) {
        this.signOutTime = signOutTime;
    }
}
