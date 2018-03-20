package cn.edu.ujs.controller;

import cn.edu.ujs.VO.*;
import cn.edu.ujs.entity.*;
import cn.edu.ujs.enums.ResultEnum;
import cn.edu.ujs.mapper.*;
import cn.edu.ujs.repository.FloorRepository;
import cn.edu.ujs.service.BlacklistService;
import cn.edu.ujs.service.ReadingRoomService;
import cn.edu.ujs.service.ReserveService;
import cn.edu.ujs.service.SeatService;
import cn.edu.ujs.util.AdminUtil;
import cn.edu.ujs.util.ResultVOUtil;
import cn.edu.ujs.util.StringUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2018/1/8.
 */
@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private ReserveMapper reserveMapper;

    @Autowired
    private SignInMapper signInMapper;

    @Autowired
    private SignOutMapper signOutMapper;

    @Autowired
    private ReserveService reserveService;

    @Autowired
    private BlacklistMapper blacklistMapper;

    @Autowired
    private BlacklistService blacklistService;

    @Autowired
    private InobservanceTypeMapper inobservanceTypeMapper;

    @Autowired
    private InobservanceMapper inobservanceMapper;

    @Autowired
    private SeatMapper seatMapper;

    @Autowired
    private ReadingRoomMapper readingRoomMapper;

    @Autowired
    private FloorMapper floorMapper;

    @Autowired
    private ReadingRoomService readingRoomService;

    @Autowired
    private SeatService seatService;

    @RequestMapping(value = "/adminLogin")
    public ResultVO login(@RequestParam String userAccount,
                          @RequestParam String password) {

        ResultVO resultVO = null;
        boolean flag = userAccount.equals("admin")&&password.equals("admin");
        if (flag) {
            resultVO = ResultVOUtil.success(flag, ResultEnum.SUCCESS_LOGIN.getCode(),
                    ResultEnum.SUCCESS_LOGIN.getMessage());
            //session.setAttribute(userId,true);
            //request.getSession().setAttribute(userId,userId);
            //stringRedisTemplate.opsForValue().set(userId,userId);
            //httpSession.setAttribute("user",userId);
            //System.out.println(httpSession.getAttribute("user").toString()+"登录成功");
        }
        else {
            resultVO = ResultVOUtil.error(flag,ResultEnum.ERROR_LOGIN.getCode(),ResultEnum.ERROR_LOGIN.getMessage());
        }
        //System.out.println("\nhahahahahah"+request.getSession().getAttribute(userId).toString());
        return resultVO;
    }

    @RequestMapping(value = "/countCollege")
    public List countCollege() {
        return reserveMapper.countByCollege();
    }

    @RequestMapping(value = "/countDegree")
    public List countDegree() {
        return reserveMapper.countByDegree();
    }

    @RequestMapping(value = "/countSeat")
    public List countSeat() {
        return reserveMapper.countBySeat();
    }

    @RequestMapping(value = "/countReadingRoom")
    public List countReadingRoom() {
        return reserveMapper.countByReadingRoom();
    }

    @RequestMapping(value = "/countByWeek")
    public List countByWeek() {
        return reserveMapper.countByWeek();
    }

    @RequestMapping(value = "/countByTime")
    public List countByTime(@RequestParam String reserveTime) {
        return reserveMapper.countByTime(reserveTime);
    }

    @RequestMapping(value = "/reserveRecord")
    public List<ReserveVO> getReserveRecord() {
        List<ReserveVO> reserveVOList = reserveMapper.getReserveRecord();
        return ReserveVO.setTag(reserveVOList);
    }

    @RequestMapping(value = "/deleteOneReserve")
    public Integer deleteOne(@RequestParam Integer id) {
        return reserveMapper.deleteById(id);
    }

    @RequestMapping(value = "/deleteReserve")
    public Integer deleteReserve(@RequestParam String idList) {

        //将前台传过来的数组字符串封装成List
        String str = idList.substring(1,idList.length()-1);
        String[] ids = idList.substring(1,idList.length()-1).split(",");
        List<Integer> integerList = new ArrayList<>();
        for(String id : ids) {
            integerList.add(new Integer(id));
        }
        // TODO: 2018/1/15 已封装好方法，后续直接调用
        //integerList = StringUtil.getListByString(idList);
        Integer result = reserveService.deleteByIdIn(integerList);
        return result;
        //return null;
    }

    @RequestMapping(value = "/signInRecord")
    public List<SignInVO> getSignInRecord() {
        List<SignInVO> signInVOList = signInMapper.getSignInRecord();
        return SignInVO.setTag(signInVOList);
    }

    @RequestMapping(value = "/signOutRecord")
    public List<SignOutVO> getSignOutRecord() {
        List<SignOutVO> signOutVOList = signOutMapper.getSignOutRecord();
        return SignOutVO.setTag(signOutVOList);
    }

    @RequestMapping(value = "/blacklist")
    public List<BlacklistVO> getBlacklist() {
        List<BlacklistVO> blacklistVOList = blacklistMapper.getBlacklist();
        return blacklistVOList;
    }

    @RequestMapping(value = "/deleteBlacklist")
    public Integer deleteBlacklist(@RequestParam String idList) {
        Integer result = blacklistService.deleteByIdIn(AdminUtil.stringToList(idList));
        return result;
    }

    @RequestMapping(value = "/inobservanceType")
    public List<InobservanceType> getInobservanceType() {
        return inobservanceTypeMapper.getInobservanceType();
    }

    @RequestMapping(value = "/inobservance")
    public List<InobservanceVO> getInobservance() {
        List<InobservanceVO> inobservanceVOList = inobservanceMapper.getInobservance();
        return inobservanceVOList;
    }

    @RequestMapping(value = "/seatInfo")
    public List<SeatVO> getSeat() {
        List<SeatVO> seatVOList = seatMapper.getSeat();
        return seatVOList;
    }

    @RequestMapping(value = "/readingRoomInfo")
    public List<ReadingRoomVO2> getReadingRoomInfo() {
        List<ReadingRoomVO2> readingRoomVO2List = readingRoomMapper.getReadingRoom();
        return readingRoomVO2List;
    }

    @RequestMapping(value = "/floorInfo")
    public List<Map<String,String>> getFloorInfo() {
        List<Map<String,String>> floorList = floorMapper.getFloorName();
        return floorList;
    }

    @RequestMapping(value = "/updateReadingRoom")
    public ResultVO updateReadingRoom(@RequestParam("readingRoomId") String readingRoomId,
                                      @RequestParam("readingRoomName") String readingRoomName) {
        Integer result = readingRoomMapper.updateReadingRoom(readingRoomName,readingRoomId);
        ResultVO resultVO = ResultVOUtil.success(result);
        return resultVO;
    }

    @RequestMapping(value = "/addOneReadingRoom")
    public ReadingRoom addOneReadingRoom(@RequestParam("readingRoomName") String readingRoomName,
                                         @RequestParam("floorId") String floorId,
                                         @RequestParam("seatCount") Integer seatCount) {
        ReadingRoom readingRoom = new ReadingRoom();
        readingRoom.setReadingRoomName(readingRoomName);
        readingRoom.setSeatCount(0);
        // TODO: 2018/1/17  设置完数量之后，还要自动生成座位号

        readingRoom.setFloorId(floorId);
        readingRoom = readingRoomService.addOneReadingRoom(readingRoom);
        readingRoomService.addSeat2ReadingRoom(readingRoom.getReadingRoomId(),seatCount);

        return readingRoom;
    }

    @RequestMapping(value = "/deleteOneReadingRoom")
    public Integer deleteOneReadingRoom(@RequestParam("readingRoomId") String readingRoomId) {

        // TODO: 2018/1/17 需要先山删除座位再删除阅览室，因为外键因素 
        //删除阅览室所在座位
        seatMapper.deleteSeatsOfReadingRoom(readingRoomId);

        //删除阅览室
        Integer result = readingRoomMapper.deleteReadingRoom(readingRoomId);
        //删除阅览室所在座位
        seatMapper.deleteSeatsOfReadingRoom(readingRoomId);
        return result;
    }

    @RequestMapping(value = "/getReadingRoomByFloor")
    public List getReadingRoomByFloor(@RequestParam String floorId) {
        return readingRoomMapper.getReadingRoomByFloor(floorId);
    }

    @RequestMapping(value = "/deleteOneSeat")
    public Integer deleteOneSeat(@RequestParam("seatId") String seatId) {
        Integer result = seatService.deleteOneSeat(seatId);
        return result;
    }

    @RequestMapping(value = "/deleteManySeats")
    public Integer deleteManySeats(@RequestParam String seatIds) {

        List<String> seatIdList = StringUtil.getListBySeatString(seatIds);
        Integer result = seatService.deleteManySeats(seatIdList);
        return result;
    }

    @RequestMapping(value = "/addSeat")
    public Integer addSeat(@RequestParam("readingRoomId") String readingRoomId,
                           @RequestParam("count") Integer count) {
        Integer result = readingRoomService.addSeat2ReadingRoom(readingRoomId, count);
        return result;
    }
    // TODO: 2018/1/14 阅览室和楼层都可以添加一个是否开放字段
}
