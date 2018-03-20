package cn.edu.ujs.controller;

import cn.edu.ujs.VO.ResultVO;
import cn.edu.ujs.entity.*;
import cn.edu.ujs.enums.*;
import cn.edu.ujs.mapper.ReserveMapper;
import cn.edu.ujs.service.*;
import cn.edu.ujs.util.ResultVOUtil;
import cn.edu.ujs.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2017/12/26.
 */
@RestController
@RequestMapping(value = "/sign_in")
public class SignInController {

    private static final Logger logger = LoggerFactory.getLogger(SignInController.class);

    @Autowired
    private SignInService signInService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private ReserveService reserveService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private InobservanceService inobservanceService;

    @Autowired
    private BlacklistService blacklistService;

    @Autowired
    private ReserveMapper reserveMapper;

    @RequestMapping(value = "/record")
    public List<SignIn> findSignInRecord(@RequestParam(required = false) String userId) {
        List<SignIn> signInList;
        if (userId == null) {
            signInList = signInService.findAll();

        }

        else
            signInList = signInService.findByUserId(userId);
        return signInList;
    }

    @RequestMapping
    public ResultVO signIn(@RequestParam(required = false) String userId) {

        //userId = httpSession.getAttribute("user").toString();
        UserInfo userInfo = userInfoService.findOne(userId);
        ResultVO resultVO = null;
        SignIn signIn = null;
        Reserve reserve = null;

        if(userInfo != null) {
            //判断你当天是否预约
            if (userInfo.getReserveStatus() == ReserveStatusEnum.RESERVED.getCode()) {
                // TODO: 2018/1/2 需要注意的是当天可以预约多次，所以有多条记录
                Reserve todayReserve = reserveMapper.findByUserIdAndReserveTime(userId,TimeUtil.getDateShort()).get(0);
                //判断你当天是否签到
                if (userInfo.getSignInStatus() == SignInStatusEnum.NOT_SIGN_IN.getCode()) {
                    //更新签到信息
                    signIn = updateSignIn(userId,
                                          todayReserve.getSeatId(),
                                          SignInStatusEnum.FIRST_SIGN_IN.getCode(),
                                          todayReserve.getReserveTime());
                    //更新座位表
                    updateSeat(todayReserve.getSeatId());
                    //更新读者状态信息
                    updateUserInfo(userInfo,SignInStatusEnum.FIRST_SIGN_IN.getCode());
                    //返回视图对象
                    resultVO = ResultVOUtil.success(signIn,
                                                    ResultEnum.SUCCESS_SIGN_IN.getCode(),
                                                    ResultEnum.SUCCESS_SIGN_IN.getMessage());
                }
                
                // TODO: 2017/12/28 处理签离后又签到的逻辑
                //判断是否签离
                else {
                    if (userInfo.getSignOutStatus() == SignOutStatusEnum.TEMPORARY_SIGN_OUT.getCode()) {
                        //更新签到表信息
                        signIn = updateSignIn(userId,
                                              todayReserve.getSeatId(),
                                              SignInStatusEnum.SIGN_IN_AFTER_SIGN_OUT.getCode(),
                                              todayReserve.getReserveTime());
                        //更新读者状态信息表
                        userInfo.setSignInStatus(SignInStatusEnum.SIGN_IN_AFTER_SIGN_OUT.getCode());
                        userInfo.setSignOutStatus(SignOutStatusEnum.NOT_SIGN_OUT.getCode());
                        userInfoService.save(userInfo);
                        //更新座位表
                        updateSeat(todayReserve.getSeatId());
                        resultVO = ResultVOUtil.success(signIn,
                                                        ResultEnum.SUCCESS_SIGN_IN.getCode(),
                                                        ResultEnum.SUCCESS_SIGN_IN.getMessage());
                    }
                    // TODO: 2018/1/2 对于门禁系统的签到，要判断走之前是否临时签离，但是网络签到则不需要，后续可以添加标志判断该读者是通过何种方式签到

                    //如果没签离，生成违规记录
                    else {
                        //找出今天预约的第一条记录（最新记录）
                        reserve = reserveMapper.findByUserIdAndReserveTime(userId,TimeUtil.getDateShort()).get(0);
                        //生成违规记录
                        Inobservance inobservance = new Inobservance();
                        inobservance.setUserId(userId);
                        inobservance.setReserveTime(reserve.getReserveTime());
                        inobservance.setSeatId(reserve.getSeatId());
                        inobservance.setInobservanceTypeId(InobservanceTypeEnum.NOT_SIGN_OUT_BEFORE_TEMPORARY_LEAVE.getCode());
                        inobservanceService.save(inobservance);
                        //更新黑名单表
                        blacklistService.save(userId,InobservanceTypeEnum.NOT_SIGN_OUT_BEFORE_TEMPORARY_LEAVE.getCode());
                        //释放座位
                        Seat seat = seatService.findOne(reserve.getSeatId());
                        seat.setSeatStatus(SeatStatusEnum.NOT_BEING_RESERVED.getCode());
                        seatService.save(seat);
                        //更新读者信息表
                        userInfo.setReserveStatus(ReserveStatusEnum.NOT_RESERVED.getCode());
                        userInfo.setSignInStatus(SignInStatusEnum.NOT_SIGN_IN.getCode());
                        userInfo.setSignOutStatus(SignOutStatusEnum.NOT_SIGN_OUT.getCode());
                        userInfoService.save(userInfo);
                        resultVO = ResultVOUtil.error(ResultEnum.NOT_TEMPORARY_SIGN_OUT.getCode(),
                                ResultEnum.NOT_TEMPORARY_SIGN_OUT.getMessage());
                    }
                }

            }
            else {
                resultVO = ResultVOUtil.error(ResultEnum.NOT_RESERVED.getCode(),ResultEnum.NOT_RESERVED.getMessage());
            }
        }
        else {
            resultVO = ResultVOUtil.error(ResultEnum.NOT_RESERVED.getCode(),
                                          ResultEnum.NOT_RESERVED.getMessage());
        }
        return resultVO;
    }

    /**
     * 更新签到表信息
     * @param userId
     * @param seatId
     * @return
     */
    public SignIn updateSignIn(String userId, String seatId, Integer signInType, Date reserveTime) {
        SignIn signIn = new SignIn();
        signIn.setUserId(userId);
        signIn.setSeatId(seatId);
        signIn.setSignInTpye(signInType);
        signIn.setReserveTime(reserveTime);
        return signInService.save(signIn);
    }

    /**
     * 更新读者状态信息表
     * @param userInfo
     * @param code
     */
    public void updateUserInfo(UserInfo userInfo, Integer code) {
        userInfo.setSignInStatus(code);
        userInfoService.save(userInfo);
    }

    /**
     * 更新座位状态为被签到
     * @param seatId
     */
    public void updateSeat(String seatId) {
        Seat seat = seatService.findOne(seatId);
        seat.setSeatStatus(SeatStatusEnum.BEING_SIGN_IN.getCode());
        seatService.save(seat);
    }

    /**
     * 读取今天的预约信息
     * @param userId
     * @return
     */
    public Reserve readTodayReservation(String userId) {
        Reserve todayReserve = reserveService.findByUserIdAndReserveTimeBetween(userId,
                TimeUtil.stringToDate(TimeUtil.getDateShort()),
                TimeUtil.addOneDay(TimeUtil.getDateShort()));
        return todayReserve;
    }
}
