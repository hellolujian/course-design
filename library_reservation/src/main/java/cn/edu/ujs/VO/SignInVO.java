package cn.edu.ujs.VO;

import cn.edu.ujs.entity.SignIn;
import cn.edu.ujs.util.TimeUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2018/1/11.
 */
public class SignInVO {

    private Integer id;

    /**读者编号*/
    private String userId;

    /**读者姓名*/
    private String userName;

    /**座位编号*/
    private String seatId;

    /**预约时间*/
    private Date reserveTime;

    /**签到时间*/
    private Date signInTime;

    /**签到类别*/
    private String signInTypeName;

    /**签到时间标签*/
    private String tag;

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

    public Date getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(Date signInTime) {
        this.signInTime = signInTime;
    }

    public String getSignInTypeName() {
        return signInTypeName;
    }

    public void setSignInTypeName(String signInTypeName) {
        this.signInTypeName = signInTypeName;
    }

    public static List<SignInVO> setTag(List<SignInVO> signInVOList) {
        Date today = new Date();
        Date tomorrow = TimeUtil.addOneDay(today);
        Date dayAfterTomorrow = TimeUtil.addOneDay(tomorrow);
        for (SignInVO signInVO : signInVOList) {
            String signInDay = TimeUtil.dateToShortString(signInVO.getSignInTime());
            if (signInDay.equals(TimeUtil.dateToShortString(today))) {
                signInVO.setTag("今天");
            }
            else if (signInDay.equals(TimeUtil.dateToShortString(tomorrow))) {
                signInVO.setTag("明天");
            }
            else if (signInDay.equals(TimeUtil.dateToShortString(dayAfterTomorrow))) {
                signInVO.setTag("后天");
            }
            else {
                signInVO.setTag("今天前");
            }

        }
        return signInVOList;
    }
}
