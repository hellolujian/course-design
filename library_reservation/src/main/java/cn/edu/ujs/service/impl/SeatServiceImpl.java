package cn.edu.ujs.service.impl;

import cn.edu.ujs.entity.ReadingRoom;
import cn.edu.ujs.entity.Seat;
import cn.edu.ujs.mapper.SeatMapper;
import cn.edu.ujs.repository.ReadingRoomRepository;
import cn.edu.ujs.repository.SeatRepository;
import cn.edu.ujs.service.SeatService;
import com.sun.prism.ReadbackRenderTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by DELL on 2017/12/25.
 */
@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ReadingRoomRepository readingRoomRepository;

    @Autowired
    private SeatMapper seatMapper;

    @Override
    public Seat save(Seat seat) {
        return seatRepository.save(seat);
    }

    @Override
    public void updateSeatStatus(String seatId, Integer seatStatus) {
        Seat seat = seatRepository.findOne(seatId);
        seat.setSeatStatus(seatStatus);
        seatRepository.save(seat);
    }

    @Override
    public Seat findOne(String seatId) {
        return seatRepository.findOne(seatId);
    }

    @Override
    public List<Seat> findBySeatStatus(Integer code) {
        return seatRepository.findBySeatStatus(code);
    }

    @Override
    public List<Seat> findBySeatStatusNot(Integer seatStatus) {
        return seatRepository.findBySeatStatusNot(seatStatus);
    }

    @Override
    public Seat getOneSeat(Integer seatStatus) {
        return seatRepository.findTopBySeatStatus(seatStatus);
    }

    @Override
    public Seat getOneSeatByReadingRoom(String readingRoomId) {
        return seatRepository.findTopByReadingRoomId(readingRoomId);
    }

    @Override
    public List<Seat> findAll() {
        return seatRepository.findAll();
    }

    @Override
    public List<Seat> findByReadingRoomId(String readingRoomId) {
        return seatRepository.findByReadingRoomId(readingRoomId);
    }

    @Override
    public Integer findUsableCountByReadingRoom(Integer seatStatus, String readingRoomId) {
        return seatRepository.findUsableCountByReadingRoom(seatStatus, readingRoomId);
    }

    @Override
    public Integer deleteOneSeat(String seatId) {
        Seat seat = seatRepository.findOne(seatId);
        ReadingRoom readingRoom = readingRoomRepository.findOne(seat.getReadingRoomId());
        Integer result = seatMapper.deleteOneSeat(seatId);
        if (result == 1) {
            readingRoom.setSeatCount(readingRoom.getSeatCount()-1);
            readingRoomRepository.save(readingRoom);
        }
        return result;
    }

    @Override
    public Integer deleteManySeats(List<String> seatIdList) {
        for (String seatId : seatIdList) {
            deleteOneSeat(seatId);
        }
        return seatIdList.size();
    }


}
