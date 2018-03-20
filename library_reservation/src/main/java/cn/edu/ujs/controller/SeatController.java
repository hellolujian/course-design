package cn.edu.ujs.controller;

import cn.edu.ujs.VO.FloorVO;
import cn.edu.ujs.VO.ReadingRoomVO;
import cn.edu.ujs.VO.ResultVO;
import cn.edu.ujs.entity.Floor;
import cn.edu.ujs.entity.ReadingRoom;
import cn.edu.ujs.entity.Reserve;
import cn.edu.ujs.entity.Seat;
import cn.edu.ujs.enums.SeatStatusEnum;
import cn.edu.ujs.mapper.ReserveMapper;
import cn.edu.ujs.service.FloorService;
import cn.edu.ujs.service.ReadingRoomService;
import cn.edu.ujs.service.SeatService;
import cn.edu.ujs.util.ResultVOUtil;
import cn.edu.ujs.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2017/12/25.
 */
@RestController
@RequestMapping(value = "/seat")
public class SeatController {


    @Autowired
    private SeatService seatService;

    @Autowired
    private ReadingRoomService readingRoomService;

    @Autowired
    private FloorService floorService;

    @Autowired
    private ReserveMapper reserveMapper;

    @RequestMapping(value = "/query")
    public ResultVO showSeat(@RequestParam(required = false) String reserveTime) {

        if (StringUtils.isEmpty(reserveTime)) {
            // TODO: 2018/1/7 前台初始化传过来的reserveTime为empty，需要改进
            reserveTime = TimeUtil.getDateShort();
            System.out.println("reserveTime:empty");
        }
        ResultVO resultVO;
        List<FloorVO> floorVOList = new ArrayList<FloorVO>();
        List<Seat> allSeats = seatService.findAll();
        //if (reserveTime != null)
        if (!reserveTime.equals(TimeUtil.getDateShort()))
            allSeats = getReserveTimeSeats(reserveTime,allSeats);
        for (Floor floor : floorService.findAll()) {
            FloorVO floorVO = new FloorVO();
            List<ReadingRoomVO> readingRoomVOList = new ArrayList<ReadingRoomVO>();
            Integer count = 0;
            Integer usableCount = 0;
            for (ReadingRoom readingRoom : readingRoomService.findByFloorId(floor.getFloorId())) {
                ReadingRoomVO readingRoomVO = new ReadingRoomVO();
                readingRoomVO.setReadingRoomName(readingRoom.getReadingRoomName());
                List<Seat> seatList = null;
                if (reserveTime.equals(TimeUtil.getDateShort())) {
                    seatList = seatService.findByReadingRoomId(readingRoom.getReadingRoomId());
                    readingRoomVO.setUsableCount(seatService.findUsableCountByReadingRoom(SeatStatusEnum.NOT_BEING_RESERVED.getCode(),readingRoom.getReadingRoomId()));
                }
                else {
                    seatList = getSeatByReadingRoom(allSeats,readingRoom.getReadingRoomId());
                    readingRoomVO.setUsableCount(getSeatByStatus(seatList,SeatStatusEnum.NOT_BEING_RESERVED.getCode()));
                }
                readingRoomVO.setCount(seatList.size());
                readingRoomVO.setSeatList(seatList);
                readingRoomVOList.add(readingRoomVO);
                count += seatList.size();
                usableCount += readingRoomVO.getUsableCount();
            }
            floorVO.setFloorName(floor.getFloorName());
            floorVO.setCount(count);
            floorVO.setUsableCount(usableCount);
            floorVO.setReadingRoomVOList(readingRoomVOList);
            floorVOList.add(floorVO);
        }
        resultVO = ResultVOUtil.success(floorVOList,0,"成功");
        return resultVO;
    }

    @RequestMapping(value = "/save/{readingRoomId}/{seatId}")
    public void save(@PathVariable String readingRoomId, @PathVariable String seatId) {
        Seat seat = new Seat();
        seat.setReadingRoomId(readingRoomId);
        seat.setSeatId(seatId);
        seatService.save(seat);
    }

    @GetMapping(value = "/updateSeatStatus")
    public void updateSeatStatus(@RequestParam("seatId") String seatId, @RequestParam("seatStatus") Integer seatStatus) {
        seatService.updateSeatStatus(seatId,seatStatus);
    }

    /**
     * 找出某一阅览室的座位
     * @param seatList
     * @param readingRoomId
     * @return
     */
    public List<Seat> getSeatByReadingRoom(List<Seat> seatList, String readingRoomId) {
        List<Seat> seats = new ArrayList<>();
        for (Seat seat : seatList) {
            if (seat.getReadingRoomId().equals(readingRoomId)) {
                seats.add(seat);
            }
        }
        return seats;
    }

    /**
     * 找出可用的座位
     * @param seatList
     * @param seatStatus
     * @return
     */
    public Integer getSeatByStatus(List<Seat> seatList, Integer seatStatus) {
        List<Seat> seats = new ArrayList<>();
        for (Seat seat : seatList) {
            if (seat.getSeatStatus() == seatStatus) {
                seats.add(seat);
            }
        }
        return seats.size();
    }

    /**
     * 设置预约那天的座位状态
     * @param reserveTime
     * @param seatList
     * @return
     */
    public List<Seat> getReserveTimeSeats(String reserveTime, List<Seat> seatList) {
        List<Reserve> reserveList = reserveMapper.findByReserveTime(reserveTime);

        for(Seat seat : seatList) {
            seat.setSeatStatus(SeatStatusEnum.NOT_BEING_RESERVED.getCode());
            for (Reserve reserve : reserveList) {
                if (seat.getSeatId().equals(reserve.getSeatId())) {
                    seat.setSeatStatus(SeatStatusEnum.BEING_RESERVED_WITHOUT_SIGN_IN.getCode());
                    break;
                }
            }

        }

        return seatList;
    }
}
