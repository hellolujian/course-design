package cn.edu.ujs.VO;

import java.util.Date;

/**
 * Created by DELL on 2018/1/11.
 */
public class BlacklistVO {

    private Integer id;

    /**读者编号*/
    private String userId;

    /**读者姓名*/
    private String userName;

    /**违规类别*/
    private String inobservanceTypeName;

    /**违规次数*/
    private Integer count = 1;

    /**创建时间*/
    private Date createTime;

    /**修改时间*/
    private Date updateTime;

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

    public String getInobservanceTypeName() {
        return inobservanceTypeName;
    }

    public void setInobservanceTypeName(String inobservanceTypeName) {
        this.inobservanceTypeName = inobservanceTypeName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
