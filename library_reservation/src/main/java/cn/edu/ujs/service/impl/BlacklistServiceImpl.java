package cn.edu.ujs.service.impl;

import cn.edu.ujs.entity.Blacklist;
import cn.edu.ujs.repository.BlacklistRepository;
import cn.edu.ujs.service.BlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by DELL on 2017/12/26.
 */
@Service
public class BlacklistServiceImpl implements BlacklistService {

    @Autowired
    private BlacklistRepository blacklistRepository;

    @Value("${library.inobservanceCount}")
    private Integer inobservanceCount;

    @Override
    public List<Blacklist> findAll() {
        return blacklistRepository.findAll();
    }

    @Override
    public Blacklist save(String userId, Integer inobservanceTypeId) {

        Blacklist blacklist = findByUserIdAndInobservanceTypeId(userId,inobservanceTypeId);
        //如果不存在此记录，就添加，否则违规次数加一
        // TODO: 2017/12/29 还要判断是否到达3次，如果到达3次了，开启一个定时任务240小时后解除限制

        if(blacklist == null) {
            blacklist = new Blacklist();
            blacklist.setUserId(userId);
            blacklist.setInobservanceTypeId(inobservanceTypeId);
            //blacklist.setCount(1);
            //blacklistrepository.save(blacklist);
        }
        else {
            blacklist.setCount(blacklist.getCount()+1);
        }
        blacklist = blacklistRepository.save(blacklist);
        return blacklist;
    }

    @Override
    public Blacklist findByUserIdAndInobservanceTypeId(String userId, Integer inobservanceTypeId) {
        return blacklistRepository.findByUserIdAndInobservanceTypeId(userId,inobservanceTypeId);
    }

    @Override
    public List<Blacklist> findByUserId(String userId) {
        return blacklistRepository.findByUserId(userId);
    }

    @Override
    public Blacklist isForbidden(String userId) {
        List<Blacklist> blacklistList = findByUserId(userId);
        for (Blacklist blacklist : blacklistList) {
            // TODO: 2017/12/27 需要读取配置文件获取违次数的设定
            if (blacklist.getCount() >= inobservanceCount) {
                return blacklist;
            }
        }
        return null;
    }

    @Override
    public Blacklist findForbiddenRecord(String userId) {
        return null;
    }

    @Override
    @Transactional
    public Integer deleteByUserIdAndInobservanceTypeId(String userId, Integer inobservanceTypeId) {
        return blacklistRepository.deleteByUserIdAndInobservanceTypeId(userId,inobservanceTypeId);
    }

    @Override
    @Transactional
    public Integer deleteByIdIn(List<Integer> idList) {
        return blacklistRepository.deleteByIdIn(idList);
    }

}
