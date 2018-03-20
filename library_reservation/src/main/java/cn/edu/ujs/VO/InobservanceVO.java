package cn.edu.ujs.VO;

import java.util.Date;

/**
 * Created by DELL on 2018/1/11.
 */
public class InobservanceVO {

    private Integer id;

    /**读者编号*/
    private String userId;

    /**读者姓名*/
    private String userName;

    /**座位编号*/
    private String seatId;

    /**预约时间*/
    private Date reserveTime;

    /**违规类别*/
    private String inobservanceTypeName;

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

    public String getInobservanceTypeName() {
        return inobservanceTypeName;
    }

    public void setInobservanceTypeName(String inobservanceTypeName) {
        this.inobservanceTypeName = inobservanceTypeName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
