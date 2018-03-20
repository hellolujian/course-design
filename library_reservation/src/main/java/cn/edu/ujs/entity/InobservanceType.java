package cn.edu.ujs.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by DELL on 2017/12/29.
 */
@Entity
public class InobservanceType {

    /**违规类别编号*/
    @Id
    private Integer inobservanceTypeId;

    /**违规类别名称*/
    private String inobservanceTypeName;

    public Integer getInobservanceTypeId() {
        return inobservanceTypeId;
    }

    public void setInobservanceTypeId(Integer inobservanceTypeId) {
        this.inobservanceTypeId = inobservanceTypeId;
    }

    public String getInobservanceTypeName() {
        return inobservanceTypeName;
    }

    public void setInobservanceTypeName(String inobservanceTypeName) {
        this.inobservanceTypeName = inobservanceTypeName;
    }
}
