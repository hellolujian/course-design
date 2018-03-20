package cn.edu.ujs.service;

import cn.edu.ujs.entity.Inobservance;
import cn.edu.ujs.repository.InobservanceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by DELL on 2017/12/29.
 */
public interface InobservanceService {

    /**根据读者编号查找违规记录*/
    public List<Inobservance> findByUserId(String userId);

    /**添加违规记录*/
    public Inobservance save(Inobservance inobservance);

    /**查找所有违规记录*/
    public List<Inobservance> findAll();
}
