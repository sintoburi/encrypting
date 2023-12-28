package com.example.springtest.Controller;

import com.example.springtest.Mapper.HomeMapper;
import com.example.springtest.VO.UserVO;
import com.example.springtest.securePassword;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;

@Controller
public class HomeController {
    private final HomeMapper homeMapper;

    public HomeController(HomeMapper homeMapper) {
        this.homeMapper = homeMapper;
    }

    @GetMapping("/")
    public String Home(){

        return "home";
    }

    @GetMapping("signup")
    public String signup () {

        return "signup";
    }
    @GetMapping("login")
    public String login () {

        return "login";
    }
    @PostMapping("loginOk")
    public ModelAndView loginOk(HttpSession session , UserVO input) throws NoSuchAlgorithmException {
        ModelAndView mav = new ModelAndView();
        String UserSalt = homeMapper.getSalt(input.getUserid());

        if(UserSalt == null) {
            mav.addObject("msg","일치하는 정보가 없습니다");
            mav.setViewName("alert_page");
            return mav;
        }
        String password = new securePassword().getEncrypt(input.getUserpwd(), homeMapper.getSalt(input.getUserid()));
        if(!password.equals(homeMapper.getUserPwd(input.getUserid()))) {
            System.out.println(homeMapper.getUserPwd(input.getUserid()));
            mav.addObject("msg", "비밀번호가 다릅니다");
            mav.setViewName("alert_page");
            return mav;
        }

        String encryptPwd = new securePassword().getEncrypt(input.getUserpwd(), UserSalt);
        UserVO uVO = homeMapper.login(input.getUserid(), encryptPwd);

        System.out.println(uVO);
        mav.setViewName("home");
        return mav;
    }

    @GetMapping("board")
    public String board () {

        return "board";
    }
    @PostMapping ("signupOk")
    public ModelAndView signup(UserVO uVO) throws NoSuchAlgorithmException {
        ModelAndView mav = new ModelAndView();
        securePassword sp = new securePassword();
        String salt = sp.salt(uVO.getUserpwd());
        System.out.println(salt);
        uVO.setSalt(salt);
        homeMapper.saveUserInfo(uVO);
        String encryptPassword = sp.getEncrypt(homeMapper.getUserPwd(uVO.getUserid()), homeMapper.getSalt(uVO.getUserid()));
        int result = homeMapper.updateUserPwd(encryptPassword, uVO.getUserid());

        System.out.println(salt);
        System.out.println(encryptPassword);
        mav.setViewName("signupOk");
        return mav ;
    }



}
