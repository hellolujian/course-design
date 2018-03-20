package cn.edu.ujs.thread;

import cn.edu.ujs.entity.Inobservance;
import cn.edu.ujs.entity.Seat;
import cn.edu.ujs.entity.UserInfo;
import cn.edu.ujs.enums.*;
import cn.edu.ujs.service.BlacklistService;
import cn.edu.ujs.service.InobservanceService;
import cn.edu.ujs.service.SeatService;
import cn.edu.ujs.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by DELL on 2017/12/29.
 */
@Component
public class CheckSignInRunnable implements Runnable {

    private String threadName;

    private Thread thread;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private InobservanceService inobservanceService;

    @Autowired
    private BlacklistService blacklistService;

    private String seatId;

    private String userId;

    private Date reserveTime;

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setReserveTime(Date reserveTime) {
        this.reserveTime = reserveTime;
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this,threadName);
            thread.start();
        }
    }

    public void run() {
        System.out.println(userId + "的定时任务开启");
        UserInfo userInfo = userInfoService.findOne(userId);
        //如果该读者在规定的时间内没签到
        if (userInfo.getSignInStatus() == SignInStatusEnum.NOT_SIGN_IN.getCode()) {
            //更新座位表，释放座位
            Seat seat = seatService.findOne(seatId);
            seat.setSeatStatus(SeatStatusEnum.NOT_BEING_RESERVED.getCode());
            seatService.save(seat);
            //生成违规记录
            Inobservance inobservance = new Inobservance();
            inobservance.setUserId(userId);
            inobservance.setSeatId(seatId);
            inobservance.setReserveTime(reserveTime);
            inobservance.setInobservanceTypeId(InobservanceTypeEnum.NOT_FIRST_SIGN_IN.getCode());
            inobservanceService.save(inobservance);
            //更新黑名单表
            blacklistService.save(userId,InobservanceTypeEnum.NOT_FIRST_SIGN_IN.getCode());
            //更新用户状态信息表
            userInfo.setReserveStatus(ReserveStatusEnum.NOT_RESERVED.getCode());
            userInfo.setSignInStatus(SignInStatusEnum.NOT_SIGN_IN.getCode());
            userInfo.setSignOutStatus(SignOutStatusEnum.NOT_SIGN_OUT.getCode());
            userInfoService.save(userInfo);
            System.out.println(userId + "预约后没及时签到！");
        }
        else {
            //如果是还是临时签离的状态
            if (userInfo.getSignOutStatus() == SignOutStatusEnum.TEMPORARY_SIGN_OUT.getCode()) {
                //更新座位表，释放座位
                Seat seat = seatService.findOne(seatId);
                seat.setSeatStatus(SeatStatusEnum.NOT_BEING_RESERVED.getCode());
                seatService.save(seat);
                //生成违规记录
                Inobservance inobservance = new Inobservance();
                inobservance.setUserId(userId);
                inobservance.setSeatId(seatId);
                inobservance.setReserveTime(reserveTime);
                inobservance.setInobservanceTypeId(InobservanceTypeEnum.NOT_RETURN_AFTER_TEMPORARY_LEAVE.getCode());
                inobservanceService.save(inobservance);
                //更新黑名单表
                blacklistService.save(userId,InobservanceTypeEnum.NOT_RETURN_AFTER_TEMPORARY_LEAVE.getCode());
                //更新用户状态信息表
                userInfo.setReserveStatus(ReserveStatusEnum.NOT_RESERVED.getCode());
                userInfo.setSignInStatus(SignInStatusEnum.NOT_SIGN_IN.getCode());
                userInfo.setSignOutStatus(SignOutStatusEnum.NOT_SIGN_OUT.getCode());
                userInfoService.save(userInfo);
                System.out.println(userId + "临时签离后没及时签到！");
            }
        }

    }
}
