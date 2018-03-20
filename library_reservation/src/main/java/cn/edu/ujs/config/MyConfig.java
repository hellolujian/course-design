package cn.edu.ujs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by DELL on 2017/12/28.
 */
@Component
@ConfigurationProperties(prefix = "library")
@PropertySource("classpath:/config/myConfig.properties")
public class MyConfig {

    private String openTime;

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }
}
