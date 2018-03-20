package cn.edu.ujs.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by DELL on 2018/1/3.
 */
@Entity
public class User {

    /**读者编号*/
    @Id
    private String userId;

    /**读者密码*/
    @Column(name = "user_password")
    private String password;

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
