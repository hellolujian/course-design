package cn.edu.ujs.controller;

import cn.edu.ujs.entity.Blacklist;
import cn.edu.ujs.service.BlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by DELL on 2017/12/26.
 */
@RestController
@RequestMapping(value = "/blacklist")
public class BlacklistController {

    @Autowired
    BlacklistService blacklistService;

    @RequestMapping(value = "/show")
    public List<Blacklist> showBlackList() {
        return blacklistService.findAll();
    }

    /*
    @RequestMapping(value = "/save")
    public void save(@RequestParam String userId, @RequestParam Integer inobservanceTypeId){
        Blacklist blacklist = blacklistService.findByUserIdAndInobservanceTypeId(userId,inobservanceTypeId);
        if(blacklist == null) {
            blacklist.setUserId(userId);

            blacklist.setInobservanceTypeId(inobservanceTypeId);
            blacklistService.save(blacklist);
        }
        else {
            blacklist.setCount(blacklist.getCount()+1);
        }
    }
    */
}
