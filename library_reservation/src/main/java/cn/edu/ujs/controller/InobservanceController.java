package cn.edu.ujs.controller;

import cn.edu.ujs.entity.Inobservance;
import cn.edu.ujs.service.InobservanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by DELL on 2018/1/5.
 */
@RestController
@RequestMapping(value = "/inobservance")
public class InobservanceController {

    @Autowired
    private InobservanceService inobservanceService;

    @RequestMapping(value = "/record")
    public List<Inobservance> findInobservanceRecord(@RequestParam(required = false) String userId) {
        List<Inobservance> inobservanceList;
        if (userId == null) {
            inobservanceList = inobservanceService.findAll();
        }
        else {
            inobservanceList = inobservanceService.findByUserId(userId);
        }
        return inobservanceList;
    }
}
