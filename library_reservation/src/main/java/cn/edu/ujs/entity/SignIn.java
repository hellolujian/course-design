package cn.edu.ujs.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by DELL on 2017/12/26.
 */
@Entity
public class SignIn {

    @Id
    @GeneratedValue
    private int id;

    /**用户编号*/
    private String userId;

    /**座位编号*/
    private String seatId;

    /**预约时间*/
    private Date reserveTime;

    /**签到时间*/
    private Date signInTime;

    /**签到类别*/
    private int signInType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Date getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(Date signInTime) {
        this.signInTime = signInTime;
    }

    public int getSignInTpye() {
        return signInType;
    }

    public void setSignInTpye(int signInTpye) {
        this.signInType = signInTpye;
    }
}
