package cn.edu.ujs.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by DELL on 2018/1/6.
 */
@Entity
public class Floor {

    /**楼层编号*/
    @Id
    private String floorId;

    /**楼层名称*/
    private String floorName;

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }
}
