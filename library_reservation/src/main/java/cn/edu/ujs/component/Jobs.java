package cn.edu.ujs.component;

import cn.edu.ujs.entity.*;
import cn.edu.ujs.enums.*;
import cn.edu.ujs.service.*;
import cn.edu.ujs.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2017/12/26.
 */
@Component
@PropertySource("classpath:/config/myConfig.properties")
public class Jobs {

    private static final Logger logger = LoggerFactory.getLogger(Jobs.class);

    @Autowired
    ReserveService reserveService;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    SeatService seatService;

    @Autowired
    InobservanceService inobservanceService;

    @Autowired
    BlacklistService blacklistService;

    @Value("${library.openTime}")
    private String openTime;

    /**每天8:30定时执行任务检查需要在8:30之前签到的读者*/
    @Scheduled(cron = "0 30 8 * * ?")
    public void checkSignIn() {
        String dateStr = TimeUtil.getDateShort() + ' ' + openTime;
        List<Reserve> reserveList = reserveService.findByReserveTime(TimeUtil.longStrToDate(dateStr));
        UserInfo userInfo = null;
        String userId = null;
        String seatId = null;
        Date reserveTime = null;
        for (Reserve reserve : reserveList) {
            userId = reserve.getUserId();
            seatId = reserve.getSeatId();
            reserveTime = reserve.getReserveTime();
            userInfo = userInfoService.findOne(userId);
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
                logger.warn(userId + "没有及时签到！");
            }
        }
    }

    /**每天零点设置读者状态*/
    @Scheduled(cron = "0 0 0 * * ?")
    public void setUserInfo() {
        Date date = new Date();
        readReservation(date);
    }

    /**读取今天有预约的读者，设置其状态信息*/
    public void readReservation(Date date) {
        List<Reserve> reserveList = reserveService.findByReserveTimeBetween(date,TimeUtil.addOneDay(date));
        String userId = null;
        UserInfo userInfo = null;
        for (Reserve reserve : reserveList) {
            userId = reserve.getUserId();
            userInfo = userInfoService.findOne(userId);
            userInfo.setReserveStatus(ReserveStatusEnum.RESERVED.getCode());
            userInfoService.save(userInfo);
            logger.info(userId + "设置状态信息成功");
        }
    }

    //定时任务清场
    @Scheduled(cron = "${clearTime.schedule}")
    public void clearLibrary() {
        // TODO: 2017/12/30 涉及到多表查询，可以考虑使用mybatis，后续改进
        //找出没有被释放的座位
        List<Seat> seatList = seatService.findBySeatStatusNot(SeatStatusEnum.NOT_BEING_RESERVED.getCode());
        //根据座位号查询预约表，找出预约了该座位的读者
        List<Reserve> reserveList = null;
        for (Seat seat : seatList) {
            String seatId = seat.getSeatId();
            //找出今天预约了位子的读者，可能有多个
            reserveList = reserveService.findBySeatIdAndReserveTimeBetween(seatId,TimeUtil.stringToDate(TimeUtil.getDateShort()),new Date());
            //继续查找读者状态信息表，找出没有签离的读者
            for (Reserve reserve : reserveList) {
                String userId = reserve.getUserId();
                UserInfo userInfo = userInfoService.findOne(userId);
                Date reserveTime = reserve.getReserveTime();
                if (userInfo.getReserveStatus() != ReserveStatusEnum.NOT_RESERVED.getCode()) {
                    //生成违规记录
                    Inobservance inobservance = new Inobservance();
                    inobservance.setUserId(userId);
                    inobservance.setSeatId(seatId);
                    inobservance.setReserveTime(reserveTime);
                    inobservance.setInobservanceTypeId(InobservanceTypeEnum.NOT_SIGN_OUT_BEFORE_FINAL_LEAVE.getCode());
                    inobservanceService.save(inobservance);
                    //更新黑名单
                    blacklistService.save(userId,InobservanceTypeEnum.NOT_SIGN_OUT_BEFORE_FINAL_LEAVE.getCode());
                    //更新用户状态信息
                    userInfo.setReserveStatus(ReserveStatusEnum.NOT_RESERVED.getCode());
                    userInfo.setSignInStatus(SignInStatusEnum.NOT_SIGN_IN.getCode());
                    userInfo.setSignOutStatus(SignOutStatusEnum.NOT_SIGN_OUT.getCode());
                    userInfoService.save(userInfo);
                    logger.info(userId + "最后离开没有签离");
                }
            }
            //释放座位
            seat.setSeatStatus(SeatStatusEnum.NOT_BEING_RESERVED.getCode());

        }
    }
}
