package cn.edu.ujs.entity;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * 黑名单
 * Created by DELL on 2017/12/26.
 */
@Entity
@DynamicUpdate
public class Blacklist {

    /**黑名单id*/
    @Id
    @GeneratedValue
    private Integer id;

    /**读者编号*/
    private String userId;

    /**违规类别*/
    private Integer inobservanceTypeId;

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

    public Integer getInobservanceTypeId() {
        return inobservanceTypeId;
    }

    public void setInobservanceTypeId(Integer inobservanceTypeId) {
        this.inobservanceTypeId = inobservanceTypeId;
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

    @Override
    public String toString() {
        return "Blacklist{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", inobservanceTypeId=" + inobservanceTypeId +
                ", count=" + count +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
