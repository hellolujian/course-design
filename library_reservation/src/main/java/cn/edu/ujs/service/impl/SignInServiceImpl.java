package cn.edu.ujs.service.impl;

import cn.edu.ujs.entity.SignIn;
import cn.edu.ujs.repository.SignInRepository;
import cn.edu.ujs.service.SignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by DELL on 2017/12/26.
 */
@Service
public class SignInServiceImpl implements SignInService{

    @Autowired
    private SignInRepository signInRepository;

    @Override
    public List<SignIn> findAll() {
        return signInRepository.findAll();
    }

    @Override
    public List<SignIn> findByUserId(String userId) {
        return signInRepository.findByUserId(userId);
    }

    @Override
    public SignIn save(SignIn signIn) {
        return signInRepository.save(signIn);
    }
}
