package cn.edu.ujs.thread;

import cn.edu.ujs.entity.UserInfo;
import cn.edu.ujs.enums.SignInStatusEnum;
import cn.edu.ujs.service.InobservanceService;
import cn.edu.ujs.service.SeatService;
import cn.edu.ujs.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.util.Date;

/**
 * Created by DELL on 2017/12/29.
 */
@Async
public class MyRunnable implements Runnable{

    private String userId;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private InobservanceService inobservanceService;



    /**
     * 判断该读者有没有在规定时间内签到(第一次签到)
     */
    public void run() {
            //System.out.println(Thread.currentThread().getName());
        UserInfo userInfo = userInfoService.findOne(userId);
        if(userInfo.getSignInStatus() == SignInStatusEnum.NOT_SIGN_IN.getCode()) {
            //更新座位表


        }
    }
}
