package cn.edu.ujs.controller;

import cn.edu.ujs.VO.ResultVO;
import cn.edu.ujs.VO.SeatVO;
import cn.edu.ujs.component.DynamicTisk;
import cn.edu.ujs.entity.*;
import cn.edu.ujs.enums.ReserveStatusEnum;
import cn.edu.ujs.enums.ResultEnum;
import cn.edu.ujs.enums.SeatStatusEnum;
import cn.edu.ujs.service.*;
import cn.edu.ujs.thread.CheckSignInRunnable;
import cn.edu.ujs.util.CronUtil;
import cn.edu.ujs.util.ResultVOUtil;
import cn.edu.ujs.util.ThreadUtil;
import cn.edu.ujs.util.TimeUtil;
import org.apache.ibatis.annotations.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.ManagedProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2017/12/25.
 */
@RestController
@RequestMapping(value = "/reserve")
public class ReserveController {

    private static final Logger logger = LoggerFactory.getLogger(ReserveController.class);
    @Autowired
    private ReserveService reserveService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private BlacklistService blackListService;

    @Autowired
    private UserService userService;

    @Value("${opentime}")
    private String opentime;

    @Value("${library.reservedTime.FirstSignIn2}")
    private Integer reservedTimeForFirstSignIn;

    @Value("${library.reserve.forbidTime}")
    private Integer forbidTimeForReserve;

    @Autowired
    private DynamicTisk dynamicTisk;

    @Autowired
    private CheckSignInRunnable checkSignInRunnable;

    @RequestMapping(value = "/record")
    public List<Reserve> getReserveRecord(@RequestParam(required = false) String userId) {
        List<Reserve> reserveList = reserveService.findByUserId(userId);
        return reserveList;
    }

    @RequestMapping(value = "/record2")
    public List<Reserve> getPageReserveRecord(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                              @RequestParam(value = "size", defaultValue = "2") Integer size) {
        PageRequest pageRequest = new PageRequest(page,size);
        Page<Reserve> reservePage = reserveService.findAll(pageRequest);
        return reservePage.getContent();
    }

    @RequestMapping(value = "/record3")
    public List<Reserve> getAllReserveRecord(@RequestParam(required = false) String userId) {
        List<Reserve> reserveList;
        if (userId == null) {
            reserveList = reserveService.findAll();
        }
        else {
            reserveList = reserveService.findByUserId(userId);
        }

        return reserveList;
    }

    /*
    @RequestMapping(value = "/fast")
    public ResultVO fastReserve(@RequestParam String userId) {
        User user = userService.findByUserId(userId);
        ResultVO resultVO = null;
        Map map = new HashMap<>();
        //获取可预约的座位
        SeatVO seatVO = reserveService.fastReserveSeat();
        //更新座位表
        updateSeat(seatVO.getSeatId());

        //判断预约时间是否为当天8:00后
        //更新预约表
        Reserve reserve = null;
        String time = TimeUtil.getDateShort()+" "+opentime;
        if (TimeUtil.isDateAfter(TimeUtil.getDateShort()+" "+opentime)) {
            reserve = updateReservation(userId,seatVO.getSeatId());
        }
        else {
            reserve = updateReservation(userId,seatVO.getSeatId(),TimeUtil.getDateShort());
        }
        //更新读者信息表
        updateUserInfo(userId);
        map.put("userId",userId);
        map.put("seatId",seatVO.getSeatId());
        map.put("readingRoomName",seatVO.getReadingRoomName());
        map.put("floorName",seatVO.getFloorName());
        //map.put("reserveTime",reserve.getReserveTime());
        map.put("signInTime",TimeUtil.addMinute(reserve.getReserveTime(),reservedTimeForFirstSignIn));

        resultVO = ResultVOUtil.success(map);
        return resultVO;
    }
    */
    @RequestMapping
    public ResultVO reserve(@RequestParam(required = false) String userId,
                            @RequestParam String seatId,
                            @RequestParam String reserveTime) {

        // TODO: 2017/12/29 还需要验证是否临时签离未刷卡

        //System.out.println(httpSession.getAttribute("user").toString());
        //userId = httpSession.getAttribute("user").toString();
        ResultVO resultVO = null;
        Reserve reserve = null;
        Seat seat = null;
        UserInfo userInfo = null;
        Blacklist blackList = blackListService.isForbidden(userId);
        //检查黑名单是否被禁止预约
        if (blackList != null) {
            //计算最后一次违规的日期与当前日期的小时差
            long hours = TimeUtil.countHours(new Date(),blackList.getUpdateTime());
            //如果还在禁止范围时间内，相应提示
            if (hours <= forbidTimeForReserve) {
                resultVO = ResultVOUtil.error(ResultEnum.FORBID_RESERVE.getCode(),
                        ResultEnum.FORBID_RESERVE.getMessage()+","+(forbidTimeForReserve-hours)+"小时后方可预约");
                return resultVO;
            }
            //如果禁止时间已到，消除黑名单相应数据
            blackListService.deleteByUserIdAndInobservanceTypeId(userId,blackList.getInobservanceTypeId());

        }

        //如果预约的是当天的座位
        //String todaytime = TimeUtil.getDateShort();
        if (reserveTime.equals(TimeUtil.getDateShort())) {
            seat = seatService.findOne(seatId);
            //如果该座位不可用，直接返回
            if (seat.getSeatStatus() != SeatStatusEnum.NOT_BEING_RESERVED.getCode()) {
                resultVO = ResultVOUtil.error(ResultEnum.BEING_RESERVED.getCode(),
                        ResultEnum.BEING_RESERVED.getMessage());
                return resultVO;
            }
            //如果该读者当天有预约，直接返回
            userInfo = userInfoService.findOne(userId);
            if (userInfo != null && userInfo.getReserveStatus() == ReserveStatusEnum.RESERVED.getCode()) {
                resultVO = ResultVOUtil.error(ResultEnum.ALREADY_RESERVE.getCode(),
                        ResultEnum.ALREADY_RESERVE.getMessage());
                return resultVO;
            }
            //如果上述条件都满足，可以预约
            //判断预约时间是否为当天8:00后
            String time = TimeUtil.getDateShort()+" "+opentime;
            if (TimeUtil.isDateAfter(TimeUtil.getDateShort()+" "+opentime)) {
                reserve = updateReservation(userId,seatId);//将预约信息写入预约表
                //// TODO: 2017/12/26 开启线程
                // TODO: 2017/12/30 此定时任务不是异步执行的，需要改进
                logger.info("我要开启计时线程了");
                Date date = new Date();
                checkSignInRunnable.setUserId(userId);
                checkSignInRunnable.setSeatId(seatId);
                checkSignInRunnable.setReserveTime(date);
                //生成cron表达式
                //date = TimeUtil.addMinute(30);
                date = TimeUtil.addSecond(reservedTimeForFirstSignIn);
                String cron = CronUtil.getCron(TimeUtil.getSecondByDate(date),
                        TimeUtil.getMinuteByDate(date),
                        TimeUtil.getHourByDate(date),
                        TimeUtil.getDayByDate(date),
                        TimeUtil.getMonthByDate(date),
                        TimeUtil.getYearByDate(date));
                dynamicTisk.setCronTrigger(cron);
                dynamicTisk.setRunnable(checkSignInRunnable);
                dynamicTisk.startCron();
                ThreadUtil.getAllThread();

            }
            else {
                reserve = updateReservation(userId,seatId,reserveTime);
            }
            updateUserInfo(userId);//更新用户状态信息
            updateSeat(seatId);//更新座位表信息
            resultVO = ResultVOUtil.success(reserve,ResultEnum.SUCCESS_RESERVE.getCode(),
                    ResultEnum.SUCCESS_RESERVE.getMessage());
            return resultVO;

        }
        //如果预约的不是当天
        else {
            //判断那天你是否预约
            reserve = reserveService.findByUserIdAndReserveTime(userId,TimeUtil.longStrToDate(reserveTime+" "+opentime));
            if (reserve != null) {
                resultVO = ResultVOUtil.error(ResultEnum.ALREADY_RESERVE.getCode(),
                        ResultEnum.ALREADY_RESERVE.getMessage());
                return resultVO;
            }

            //判断那天的位子是否可预约
            List<Reserve> reserveList = reserveService.findBySeatIdAndReserveTimeBetween(seatId,TimeUtil.stringToDate(reserveTime), TimeUtil.addOneDay(TimeUtil.stringToDate(reserveTime)));
            if (reserveList.size() != 0) {
                resultVO = ResultVOUtil.error(ResultEnum.BEING_RESERVED.getCode(),
                        ResultEnum.BEING_RESERVED.getMessage());
                return resultVO;
            }
            //如果上述条件都满足，可以预约
            //将预约信息写入预约表
            reserve = updateReservation(userId,seatId,reserveTime);
            resultVO = ResultVOUtil.success(reserve,ResultEnum.SUCCESS_RESERVE.getCode(),
                    ResultEnum.SUCCESS_RESERVE.getMessage());
            return resultVO;
        }





        // TODO: 2017/12/28 判断是否有其他人预约了，可以由前台判断，如果被预约则该座位不可选
        // TODO: 2017/12/30 判断该座位是否被其他人预约，不应该查询预约表，因为可能被别人预约又签离了，而是应该查询座位表，后面需要改进
        /*
        List<Reserve> reserveList = reserveService.findBySeatIdAndReserveTimeBetween(seatId,TimeUtil.stringToDate(reserveTime),TimeUtil.addOneDay(reserveTime));
        //如果那天的位子已经被
        if (reserveList.size() != 0) {
            reserve = reserveList.get(0);
        }
        //判断该座位那天是否已被预约
        seat = seatService.findOne(seatId);
        if (seat.getSeatStatus() != SeatStatusEnum.NOT_BEING_RESERVED.getCode()) {
            resultVO = ResultVOUtil.error(ResultEnum.BEING_RESERVED.getCode(),
                    ResultEnum.BEING_RESERVED.getMessage());
            return resultVO;
        }
        /*
        if (reserve != null) {
            if (reserve.getUserId().equals(userId)) {
                resultVO = ResultVOUtil.error(ResultEnum.ALREADY_RESERVE.getCode(),
                                              ResultEnum.ALREADY_RESERVE.getMessage());
            }
            else {
                resultVO = ResultVOUtil.error(ResultEnum.RESERVED_BY_OTHERS.getCode(),
                        ResultEnum.RESERVED_BY_OTHERS.getMessage());
            }
            return resultVO;
        }
        */


        /*
        else {
            //如果预约的是当天
            // TODO: 2017/12/30 预约时间不能当天之前，后续改进
            if (reserveTime.equals(TimeUtil.getDateShort())) {

                //判断预约时间是否为当天8:00后
                String time = TimeUtil.getDateShort()+" "+opentime;
                if (TimeUtil.isDateAfter(TimeUtil.getDateShort()+" "+opentime)) {
                    reserve = updateReservation(userId,seatId);//将预约信息写入预约表
                    //// TODO: 2017/12/26 开启线程
                    logger.info("我要开启计时线程了");
                    Date date = new Date();
                    checkSignInRunnable.setUserId(userId);
                    checkSignInRunnable.setSeatId(seatId);
                    checkSignInRunnable.setReserveTime(date);
                    //生成cron表达式
                    //date = TimeUtil.addMinute(30);
                    date = TimeUtil.addSecond(reservedTimeForFirstSignIn);
                    String cron = CronUtil.getCron(TimeUtil.getSecondByDate(date),
                                                   TimeUtil.getMinuteByDate(date),
                                                   TimeUtil.getHourByDate(date),
                                                   TimeUtil.getDayByDate(date),
                                                   TimeUtil.getMonthByDate(date),
                                                   TimeUtil.getYearByDate(date));
                    dynamicTisk.setCronTrigger(cron);
                    dynamicTisk.setRunnable(checkSignInRunnable);
                    dynamicTisk.startCron();
                    ThreadUtil.getAllThread();

                }
                else {
                    reserve = updateReservation(userId,seatId,reserveTime);
                }
                updateUserInfo(userId);//更新用户状态信息
                updateSeat(seatId);//更新座位表信息
                resultVO = ResultVOUtil.success(reserve,ResultEnum.SUCCESS_RESERVE.getCode(),
                        ResultEnum.SUCCESS_RESERVE.getMessage());
                return resultVO;
            }
            //如果预约的不是当天
            else {
                //将预约信息写入预约表
                reserve = updateReservation(userId,seatId,reserveTime);
                resultVO = ResultVOUtil.success(reserve,ResultEnum.SUCCESS_RESERVE.getCode(),
                        ResultEnum.SUCCESS_RESERVE.getMessage());
                return resultVO;

                }
            }
            */
    }


    @RequestMapping(value = "/fast")
    public ResultVO reserve(@RequestParam(required = false) String userId,
                            @RequestParam String reserveTime) {

        // TODO: 2017/12/29 还需要验证是否临时签离未刷卡

        //System.out.println(httpSession.getAttribute("user").toString());
        //userId = httpSession.getAttribute("user").toString();
        ResultVO resultVO = null;
        Reserve reserve = null;
        Seat seat = null;
        UserInfo userInfo = null;
        Blacklist blackList = blackListService.isForbidden(userId);
        //检查黑名单是否被禁止预约
        if (blackList != null) {
            //计算最后一次违规的日期与当前日期的小时差
            long hours = TimeUtil.countHours(new Date(), blackList.getUpdateTime());
            //如果还在禁止范围时间内，相应提示
            if (hours <= forbidTimeForReserve) {
                resultVO = ResultVOUtil.error(ResultEnum.FORBID_RESERVE.getCode(),
                        ResultEnum.FORBID_RESERVE.getMessage() + "," + (forbidTimeForReserve - hours) + "小时后方可预约");
                return resultVO;
            }
            //如果禁止时间已到，消除黑名单相应数据
            blackListService.deleteByUserIdAndInobservanceTypeId(userId, blackList.getInobservanceTypeId());

        }

        //如果预约的是当天的座位
        //String todaytime = TimeUtil.getDateShort();
        SeatVO seatVO = reserveService.fastReserveSeat();
        String seatId = seatVO.getSeatId();
        if (reserveTime.equals(TimeUtil.getDateShort())) {
            seat = seatService.findOne(seatId);
            //如果该座位不可用，直接返回
            if (seat.getSeatStatus() != SeatStatusEnum.NOT_BEING_RESERVED.getCode()) {
                resultVO = ResultVOUtil.error(ResultEnum.BEING_RESERVED.getCode(),
                        ResultEnum.BEING_RESERVED.getMessage());
                return resultVO;
            }
            //如果该读者当天有预约，直接返回
            userInfo = userInfoService.findOne(userId);
            if (userInfo != null && userInfo.getReserveStatus() == ReserveStatusEnum.RESERVED.getCode()) {
                resultVO = ResultVOUtil.error(ResultEnum.ALREADY_RESERVE.getCode(),
                        ResultEnum.ALREADY_RESERVE.getMessage());
                return resultVO;
            }
            //如果上述条件都满足，可以预约
            //判断预约时间是否为当天8:00后
            String time = TimeUtil.getDateShort() + " " + opentime;
            if (TimeUtil.isDateAfter(TimeUtil.getDateShort() + " " + opentime)) {
                reserve = updateReservation(userId, seatId);//将预约信息写入预约表
                //// TODO: 2017/12/26 开启线程
                // TODO: 2017/12/30 此定时任务不是异步执行的，需要改进
                logger.info("我要开启计时线程了");
                Date date = new Date();
                checkSignInRunnable.setUserId(userId);
                checkSignInRunnable.setSeatId(seatId);
                checkSignInRunnable.setReserveTime(date);
                //生成cron表达式
                //date = TimeUtil.addMinute(30);
                date = TimeUtil.addSecond(reservedTimeForFirstSignIn);
                String cron = CronUtil.getCron(TimeUtil.getSecondByDate(date),
                        TimeUtil.getMinuteByDate(date),
                        TimeUtil.getHourByDate(date),
                        TimeUtil.getDayByDate(date),
                        TimeUtil.getMonthByDate(date),
                        TimeUtil.getYearByDate(date));
                dynamicTisk.setCronTrigger(cron);
                dynamicTisk.setRunnable(checkSignInRunnable);
                dynamicTisk.startCron();
                ThreadUtil.getAllThread();

            } else {
                reserve = updateReservation(userId, seatId, reserveTime);
            }
            updateUserInfo(userId);//更新用户状态信息
            updateSeat(seatId);//更新座位表信息
            resultVO = ResultVOUtil.success(reserve, ResultEnum.SUCCESS_RESERVE.getCode(),
                    ResultEnum.SUCCESS_RESERVE.getMessage());
            return resultVO;

        }
        //如果预约的不是当天
        else {
            //判断那天你是否预约
            reserve = reserveService.findByUserIdAndReserveTime(userId, TimeUtil.longStrToDate(reserveTime + " " + opentime));
            if (reserve != null) {
                resultVO = ResultVOUtil.error(ResultEnum.ALREADY_RESERVE.getCode(),
                        ResultEnum.ALREADY_RESERVE.getMessage());
                return resultVO;
            }

            //判断那天的位子是否可预约
            List<Reserve> reserveList = reserveService.findBySeatIdAndReserveTimeBetween(seatId, TimeUtil.stringToDate(reserveTime), TimeUtil.addOneDay(TimeUtil.stringToDate(reserveTime)));
            if (reserveList.size() != 0) {
                resultVO = ResultVOUtil.error(ResultEnum.BEING_RESERVED.getCode(),
                        ResultEnum.BEING_RESERVED.getMessage());
                return resultVO;
            }
            //如果上述条件都满足，可以预约
            //将预约信息写入预约表
            reserve = updateReservation(userId, seatId, reserveTime);
            resultVO = ResultVOUtil.success(reserve, ResultEnum.SUCCESS_RESERVE.getCode(),
                    ResultEnum.SUCCESS_RESERVE.getMessage());
            return resultVO;
        }
    }



    /**
     * 更新用户状态信息为当天已预约
     * @param userId
     */
    public void updateUserInfo(String userId) {
        UserInfo userInfo = userInfoService.findOne(userId);
        if (userInfo == null) {

            userInfo = new UserInfo();
            userInfo.setUserId(userId);
        }
        userInfo.setReserveStatus(ReserveStatusEnum.RESERVED.getCode());
        userInfoService.save(userInfo);
    }

    /**
     * 更新座位信息为当天已被预约
     * @param seatId
     */
    public void updateSeat(String seatId) {
        Seat seat = seatService.findOne(seatId);
        seat.setSeatStatus(SeatStatusEnum.BEING_RESERVED_WITHOUT_SIGN_IN.getCode());
        seatService.save(seat);
    }

    /**
     * 将预约信息写入预约表(预约的不是当天或者是当天8:00前预约的
     * @param userId
     * @param seatId
     * @param reserveTime
     */
    public Reserve updateReservation(String userId, String seatId, String reserveTime) {
        Reserve reserve = new Reserve();
        reserve.setUserId(userId);
        reserve.setSeatId(seatId);
        reserve.setReserveTime(TimeUtil.longStrToDate(reserveTime+" "+opentime));
        return reserveService.insert(reserve);
    }

    /**
     * 当天8:00后预约的
     * @param userId
     * @param seatId
     * @return
     */
    public Reserve updateReservation(String userId, String seatId) {
        Reserve reserve = new Reserve();
        reserve.setUserId(userId);
        reserve.setSeatId(seatId);
        return reserveService.insert(reserve);
    }
}


/*
//如果预约的是当天
        if (reserveTime.equals(TimeUtil.getDateShort())) {
            //判断当天是否已预约
            UserInfo userInfo = userInfoService.findOne(userId);
            if (userInfo != null && userInfo.getReserveStatus() == ReserveStatusEnum.RESERVED.getCode()) {
                resultVO = ResultVOUtil.error(ResultEnum.ALREADY_RESERVE.getCode(),
                        ResultEnum.ALREADY_RESERVE.getMessage());
                return resultVO;
            }
            else {
                //判断预约时间是否为当天8:00后
                if (TimeUtil.isDateAfter(TimeUtil.getDateShort()+" "+opentime)) {
                    reserve = updateReservation(userId,seatId);//将预约信息写入预约表
                    //// TODO: 2017/12/26 开启线程
                    logger.info("我要开启计时线程了");
                }
                reserve = updateReservation(userId,seatId,reserveTime);
                updateUserInfo(userId);//更新用户状态信息
                updateSeat(seatId);//更新座位表信息
                resultVO = ResultVOUtil.success(reserve,ResultEnum.SUCCESS_RESERVE.getCode(),
                        ResultEnum.SUCCESS_RESERVE.getMessage());
                return resultVO;
            }
        }
        //如果预约的不是当天，判断那天是否已预约
        else {
            reserve = reserveService.findByUserIdAndReserveTime(userId,TimeUtil.longStrToDate(reserveTime+" "+opentime));
            if (reserve != null) {
                resultVO = ResultVOUtil.error(ResultEnum.ALREADY_RESERVE.getCode(),
                        ResultEnum.ALREADY_RESERVE.getMessage());
                return resultVO;
            }
            else {
                //将预约信息写入预约表
                reserve = updateReservation(userId,seatId,reserveTime);
                resultVO = ResultVOUtil.success(reserve,ResultEnum.SUCCESS_RESERVE.getCode(),
                                                        ResultEnum.SUCCESS_RESERVE.getMessage());
                return resultVO;
            }
        }
 */