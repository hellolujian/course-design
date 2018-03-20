package cn.edu.ujs.service.impl;

import cn.edu.ujs.entity.Floor;
import cn.edu.ujs.entity.ReadingRoom;
import cn.edu.ujs.entity.Seat;
import cn.edu.ujs.mapper.ReadingRoomMapper;
import cn.edu.ujs.mapper.SeatMapper;
import cn.edu.ujs.repository.FloorRepository;
import cn.edu.ujs.repository.ReadingRoomRepository;
import cn.edu.ujs.service.ReadingRoomService;
import cn.edu.ujs.service.SeatService;
import cn.edu.ujs.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by DELL on 2018/1/6.
 */
@Service
public class ReadingRoomServiceImpl implements ReadingRoomService {

    @Autowired
    private ReadingRoomRepository readingRoomRepository;

    @Autowired
    private SeatMapper seatMapper;

    @Autowired
    private FloorRepository floorRepository;

    @Autowired
    private ReadingRoomMapper readingRoomMapper;

    @Override
    public List<ReadingRoom> findAll() {
        return readingRoomRepository.findAll();
    }

    @Override
    public List<ReadingRoom> findByFloorId(String floorId) {
        return readingRoomRepository.findByFloorId(floorId);
    }

    @Override
    public Integer addSeat2ReadingRoom(String readingRoomId, Integer count) {

        ReadingRoom readingRoom = readingRoomRepository.findOne(readingRoomId);
        Seat seat = seatMapper.findLastSeat(readingRoomId);
        Integer lastSeatId = null;
        //判断该阅览室有没有座位
        if (seat == null) {
            lastSeatId = 0;
        }
        else {
            lastSeatId = seat.getLastSeatId();
        }

        String newSeatId = null;
        for (int i = 0; i<count; i++) {
            lastSeatId++;
            //%03d表示座位号自动补0
            newSeatId = readingRoomId + "-" + StringUtil.getStringFormat("%03d",lastSeatId);
            seatMapper.addOneSeat(newSeatId,readingRoomId);
        }
        //更新阅览室表seat_count字段
        readingRoom.setSeatCount(readingRoom.getSeatCount()+count);
        readingRoomRepository.save(readingRoom);
        return count;
    }

    @Override
    public void addOneReadingRoom(String readingRoomName, String floorId) {
        Floor floor = floorRepository.findOne(floorId);
        String readingRoomId = readingRoomMapper.findLastReadingRoom(floorId);
        Integer lastReadingRoomId = null;
        if (readingRoomId == null) {
            lastReadingRoomId = 0;
        }
        else {
            lastReadingRoomId = new Integer(readingRoomId.substring(2));
        }

        lastReadingRoomId++;
        //获取新的阅览室编号
        String newReadingRoomId = floorId + "-" + StringUtil.getStringFormat("%02d",lastReadingRoomId);
        readingRoomMapper.addOneReadingRoom(newReadingRoomId,readingRoomName,floorId);
    }

    @Override
    public ReadingRoom addOneReadingRoom(ReadingRoom readingRoom) {
        String floorId = readingRoom.getFloorId();
        Floor floor = floorRepository.findOne(floorId);
        String readingRoomId = readingRoomMapper.findLastReadingRoom(floorId);
        Integer lastReadingRoomId = null;
        if (readingRoomId == null) {
            lastReadingRoomId = 0;
        }
        else {
            lastReadingRoomId = new Integer(readingRoomId.substring(2));
        }

        lastReadingRoomId++;
        //获取新的阅览室编号
        String newReadingRoomId = floorId + "-" + StringUtil.getStringFormat("%02d",lastReadingRoomId);
        readingRoom.setReadingRoomId(newReadingRoomId);
        readingRoom = readingRoomRepository.save(readingRoom);
        //生成相应数量的作为编号
        //addSeat2ReadingRoom(readingRoom.getReadingRoomId(),readingRoom.getSeatCount());
        return readingRoom;
    }
}
