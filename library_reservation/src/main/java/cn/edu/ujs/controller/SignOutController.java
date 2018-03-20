package cn.edu.ujs.controller;

import cn.edu.ujs.VO.ResultVO;
import cn.edu.ujs.component.DynamicTisk;
import cn.edu.ujs.entity.Reserve;
import cn.edu.ujs.entity.Seat;
import cn.edu.ujs.entity.SignOut;
import cn.edu.ujs.entity.UserInfo;
import cn.edu.ujs.enums.*;
import cn.edu.ujs.mapper.ReserveMapper;
import cn.edu.ujs.service.*;
import cn.edu.ujs.thread.CheckSignInRunnable;
import cn.edu.ujs.util.CronUtil;
import cn.edu.ujs.util.ResultVOUtil;
import cn.edu.ujs.util.ThreadUtil;
import cn.edu.ujs.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2017/12/28.
 */
@RestController
@RequestMapping(value = "/sign_out")
public class SignOutController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private SignOutService signOutService;

    @Autowired
    private SignInService signInService;

    @Autowired
    private ReserveService reserveService;

    @Autowired
    private ReserveMapper reserveMapper;

    @Autowired
    private CheckSignInRunnable checkSignInRunnable;

    @Autowired
    private DynamicTisk dynamicTisk;

    @Value("${library.reservedTime.temporaryLeave}")
    private Integer reservedTimeForTemporaryLeave;

    @RequestMapping(value = "/record")
    public List<SignOut> findSignOutRecord(@RequestParam(required = false) String userId) {
        List<SignOut> signOutList;
        if (userId == null) {
            signOutList = signOutService.findAll();
        }
        else {
            signOutList = signOutService.findByUserId(userId);
        }
        return signOutList;
    }
    @RequestMapping(value = "/temporary")
    public ResultVO temporarySignOut(@RequestParam(required = false) String userId) {
        //userId = httpSession.getAttribute("user").toString();
        ResultVO resultVO = null;
        UserInfo userInfo = userInfoService.findOne(userId);
        Reserve todayReserve = null;
        SignOut signOut = null;
        Seat seat = null;
        if (userInfo != null) {
            //判断当天是否预约
            if (userInfo.getReserveStatus() == ReserveStatusEnum.NOT_RESERVED.getCode()) {
                resultVO = ResultVOUtil.error(ResultEnum.NOT_RESERVED.getCode(),
                                              ResultEnum.NOT_RESERVED.getMessage());
              //  return resultVO;
            }
            else {
                //判断当天是否签到
                if (userInfo.getSignInStatus() == SignInStatusEnum.NOT_SIGN_IN.getCode()) {
                    resultVO = ResultVOUtil.error(ResultEnum.NOT_SIGN_IN.getCode(),
                                                  ResultEnum.NOT_SIGN_IN.getMessage());
                }
                else {
                    //判断当天是否签离
                    if (userInfo.getSignOutStatus() == SignOutStatusEnum.NOT_SIGN_OUT.getCode()) {
                        //更新读者信息表
                        userInfo.setSignOutStatus(SignOutStatusEnum.TEMPORARY_SIGN_OUT.getCode());
                        userInfoService.save(userInfo);
                        //更新签离表
                        todayReserve = readTodayReservation(userId);
                        signOut = updateSignOut(todayReserve.getUserId(),
                                                        todayReserve.getSeatId(),
                                                        todayReserve.getReserveTime(),
                                                        SignOutStatusEnum.TEMPORARY_SIGN_OUT.getCode());

                        //更新座位表
                        seat = seatService.findOne(signOut.getSeatId());
                        seat.setSeatStatus(SeatStatusEnum.BEING_TEMPORARY_SIGN_OUT.getCode());
                        seatService.save(seat);

                        // TODO: 2017/12/28 开启线程等待该读者的签到
                        Date date = new Date();
                        String seatId = todayReserve.getSeatId();
                        checkSignInRunnable.setUserId(userId);
                        checkSignInRunnable.setSeatId(seatId);
                        checkSignInRunnable.setReserveTime(date);
                        //生成cron表达式
                        //date = TimeUtil.addMinute(1);
                        date = TimeUtil.addSecond(reservedTimeForTemporaryLeave);
                        String cron = CronUtil.getCron(TimeUtil.getSecondByDate(date),
                                TimeUtil.getMinuteByDate(date),
                                TimeUtil.getHourByDate(date),
                                TimeUtil.getDayByDate(date),
                                TimeUtil.getMonthByDate(date),
                                TimeUtil.getYearByDate(date));
                        dynamicTisk.setCronTrigger(cron);
                        dynamicTisk.setRunnable(checkSignInRunnable);
                        dynamicTisk.startCron();
                        //ThreadUtil.getAllThread();


                        //生成视图对象
                        resultVO = ResultVOUtil.success(signOut,
                                                        ResultEnum.SUCCESS_TEMPORARY_SIGN_OUT.getCode(),
                                                        ResultEnum.SUCCESS_TEMPORARY_SIGN_OUT.getMessage());
                    }
                    else if (userInfo.getSignOutStatus() == SignOutStatusEnum.TEMPORARY_SIGN_OUT.getCode()) {
                        resultVO = ResultVOUtil.error(ResultEnum.ALREADY_TEMPORARY_SIGN_OUT.getCode(),
                                                      ResultEnum.ALREADY_TEMPORARY_SIGN_OUT.getMessage());
                    }
                    else if (userInfo.getSignOutStatus() == SignOutStatusEnum.FINAL_SIGN_OUT.getCode()) {
                        resultVO = ResultVOUtil.error(ResultEnum.ALREADY_FINAL_SIGN_OUT.getCode(),
                                                      ResultEnum.ALREADY_FINAL_SIGN_OUT.getMessage());
                    }
                }
            }
        }
        return resultVO;
    }

    @RequestMapping(value = "/final")
    public ResultVO finalSignOut(@RequestParam(required = false) String userId) {
        //userId = httpSession.getAttribute("user").toString();
        ResultVO resultVO = null;
        UserInfo userInfo = userInfoService.findOne(userId);
        Reserve todayReserve = null;
        SignOut signOut = null;
        Seat seat = null;
        if (userInfo != null) {
            //判断当天是否预约
            if (userInfo.getReserveStatus() == ReserveStatusEnum.NOT_RESERVED.getCode()) {
                resultVO = ResultVOUtil.error(ResultEnum.NOT_RESERVED.getCode(),
                        ResultEnum.NOT_RESERVED.getMessage());
                //  return resultVO;
            }
            else {
                //判断当天是否签到
                if (userInfo.getSignInStatus() == SignInStatusEnum.NOT_SIGN_IN.getCode()) {
                    resultVO = ResultVOUtil.error(ResultEnum.NOT_SIGN_IN.getCode(),
                            ResultEnum.NOT_SIGN_IN.getMessage());
                }
                else {
                    //判断当天是否临时签离
                    if (userInfo.getSignOutStatus() == SignOutStatusEnum.TEMPORARY_SIGN_OUT.getCode()) {
                        // TODO: 2017/12/28 终止临时签离的线程
                    }

                    //更新读者信息表（回归原始状态，可以继续预约）
                    userInfo.setReserveStatus(ReserveStatusEnum.NOT_RESERVED.getCode());
                    userInfo.setSignInStatus(SignInStatusEnum.NOT_SIGN_IN.getCode());
                    userInfo.setSignOutStatus(SignOutStatusEnum.NOT_SIGN_OUT.getCode());
                    userInfoService.save(userInfo);
                    //更新签离表
                    todayReserve = readTodayReservation(userId);
                    signOut = updateSignOut(todayReserve.getUserId(),
                            todayReserve.getSeatId(),
                            todayReserve.getReserveTime(),
                            SignOutStatusEnum.FINAL_SIGN_OUT.getCode());

                    //更新座位表
                    seat = seatService.findOne(signOut.getSeatId());
                    seat.setSeatStatus(SeatStatusEnum.NOT_BEING_RESERVED.getCode());
                    seatService.save(seat);

                    //生成视图对象
                    resultVO = ResultVOUtil.success(signOut,
                            ResultEnum.SUCCESS_SIGN_OUT.getCode(),
                            ResultEnum.SUCCESS_SIGN_OUT.getMessage());
                }
            }
        }
        return resultVO;
    }

    /**
     * 读取今天的预约信息
     * @param userId
     * @return
     */
    // TODO: 2018/1/17 预约信息有多个，找出最新的一个 
    public Reserve readTodayReservation(String userId) {
        //Reserve todayReserve = reserveService.findByUserIdAndReserveTimeBetween(userId,
                //TimeUtil.stringToDate(TimeUtil.getDateShort()),
                //TimeUtil.addOneDay(TimeUtil.getDateShort()));
        Reserve todayReserve = reserveMapper.findByUserIdAndReserveTime(userId,TimeUtil.getDateShort()).get(0);
        return todayReserve;
    }

    /**
     * 更新签离表
     * @param userId
     * @param seatId
     * @param reserveTime
     * @param signOutType
     * @return
     */
    public SignOut updateSignOut(String userId, String seatId, Date reserveTime, Integer signOutType) {
        SignOut signOut = new SignOut();
        signOut.setUserId(userId);
        signOut.setSeatId(seatId);
        signOut.setReserveTime(reserveTime);
        signOut.setSignOutType(signOutType);
        return signOutService.update(signOut);
    }

    public void updateSeat(String seatId) {


    }
}
