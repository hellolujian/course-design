package cn.edu.ujs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Created by DELL on 2017/12/26.
 */
@SpringBootApplication
@EnableAsync
@EnableScheduling
@MapperScan(basePackages = "cn.edu.ujs.mapper")
//@EnableRedisHttpSession
public class LibraryReserveApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryReserveApplication.class, args);
    }
}
