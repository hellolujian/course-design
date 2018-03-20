package cn.edu.ujs.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by DELL on 2017/12/26.
 */
@Entity
public class UserInfo {

    /**读者编号*/
    @Id
    private String userId;

    /**预约状态，默认0未预约*/
    private int reserveStatus = 0;

    /**签到状态，默认0未签到*/
    private int signInStatus = 0;

    /**签离状态，默认0未签离*/
    private int signOutStatus = 0;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getReserveStatus() {
        return reserveStatus;
    }

    public void setReserveStatus(int reserveStatus) {
        this.reserveStatus = reserveStatus;
    }

    public int getSignInStatus() {
        return signInStatus;
    }

    public void setSignInStatus(int signInStatus) {
        this.signInStatus = signInStatus;
    }

    public int getSignOutStatus() {
        return signOutStatus;
    }

    public void setSignOutStatus(int signOutStatus) {
        this.signOutStatus = signOutStatus;
    }
}
