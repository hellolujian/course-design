package cn.edu.ujs.controller;

import cn.edu.ujs.VO.ResultVO;
import cn.edu.ujs.enums.ResultEnum;
import cn.edu.ujs.service.UserService;
import cn.edu.ujs.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by DELL on 2018/1/3.
 */
@RestController("/login")
public class UserController {

    @Autowired
    private UserService userService;

    //@Autowired
    //private StringRedisTemplate stringRedisTemplate;

    @RequestMapping
    public ResultVO login(@RequestParam String userId, @RequestParam(required = false) String password) {

        ResultVO resultVO = null;
        boolean flag = userService.isExist(userId, password);
        if (flag) {
            resultVO = ResultVOUtil.success(flag, ResultEnum.SUCCESS_LOGIN.getCode(),ResultEnum.SUCCESS_LOGIN.getMessage());
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

    @RequestMapping(value = "/login_out")
    public ResultVO loginOut(String userId, HttpSession httpSession) {

        //System.out.println("before:"+httpSession.getAttribute(userId).toString());
        if (!StringUtils.isEmpty(httpSession.getAttribute(userId))) {
            httpSession.removeAttribute("user");
            return ResultVOUtil.success(null,ResultEnum.SUCCESS_LOGIN_OUT.getCode(),
                    ResultEnum.SUCCESS_LOGIN_OUT.getMessage());
        }

        //System.out.println("after:"+httpSession.getAttribute(userId).toString());
        return null;
    }
}
