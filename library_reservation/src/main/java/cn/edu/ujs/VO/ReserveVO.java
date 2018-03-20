package cn.edu.ujs.VO;

import cn.edu.ujs.util.TimeUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2018/1/9.
 */
public class ReserveVO {

    /**索引*/
    private Integer id;

    /**读者编号*/
    private String userId;

    /**读者姓名*/
    private String userName;

    /**座位编号*/
    private String seatId;

    /**预约时间*/
    private Date reserveTime;

    /**创建时间*/
    private Date createTime;

    /**预约时间标签*/
    private String tag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public static List<ReserveVO> setTag(List<ReserveVO> reserveVOList) {
        Date today = new Date();
        Date tomorrow = TimeUtil.addOneDay(today);
        Date dayAfterTomorrow = TimeUtil.addOneDay(tomorrow);
        for (ReserveVO reserveVO : reserveVOList) {
            String reserveDay = TimeUtil.dateToShortString(reserveVO.getReserveTime());
            if (reserveDay.equals(TimeUtil.dateToShortString(today))) {
                reserveVO.setTag("今天");
            }
            else if (reserveDay.equals(TimeUtil.dateToShortString(tomorrow))) {
                reserveVO.setTag("明天");
            }
            else if (reserveDay.equals(TimeUtil.dateToShortString(dayAfterTomorrow))) {
                reserveVO.setTag("后天");
            }
            else {
                reserveVO.setTag("今天前");
            }

        }
        return reserveVOList;
    }
}
