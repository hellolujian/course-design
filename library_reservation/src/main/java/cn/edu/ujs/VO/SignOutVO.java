package cn.edu.ujs.VO;

import cn.edu.ujs.util.TimeUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2018/1/11.
 */
public class SignOutVO {

    private Integer id;

    /**用户编号*/
    private String userId;

    /**读者姓名*/
    private String userName;

    /**座位编号*/
    private String seatId;

    /**预约时间*/
    private Date reserveTime;

    /**签离类别*/
    private String signOutTypeName;

    /**签离时间*/
    private Date signOutTime;

    /**签离时间标签*/
    private String tag;

    public String getSignOutTypeName() {
        return signOutTypeName;
    }

    public void setSignOutTypeName(String signOutTypeName) {
        this.signOutTypeName = signOutTypeName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Date getSignOutTime() {
        return signOutTime;
    }

    public void setSignOutTime(Date signOutTime) {
        this.signOutTime = signOutTime;
    }

    public static List<SignOutVO> setTag(List<SignOutVO> signOutVOList) {
        Date today = new Date();
        Date tomorrow = TimeUtil.addOneDay(today);
        Date dayAfterTomorrow = TimeUtil.addOneDay(tomorrow);
        for (SignOutVO signOutVO : signOutVOList) {
            String signOutDay = TimeUtil.dateToShortString(signOutVO.getSignOutTime());
            if (signOutDay.equals(TimeUtil.dateToShortString(today))) {
                signOutVO.setTag("今天");
            }
            else if (signOutDay.equals(TimeUtil.dateToShortString(tomorrow))) {
                signOutVO.setTag("明天");
            }
            else if (signOutDay.equals(TimeUtil.dateToShortString(dayAfterTomorrow))) {
                signOutVO.setTag("后天");
            }
            else {
                signOutVO.setTag("今天前");
            }

        }
        return signOutVOList;
    }
}
