package com.tilon.cipher.Controller;

import com.tilon.cipher.VO.UserVO;
import com.tilon.cipher.securePassword;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.tilon.cipher.Mapper.HomeMapper;

import java.security.NoSuchAlgorithmException;

@Controller
public class HomeController {

    @Autowired
    private final HomeMapper homeMapper;

    public HomeController(HomeMapper homeMapper) {
        this.homeMapper = homeMapper;
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
            mav.addObject("sha_result","일치하는 정보가 없습니다");
            mav.setViewName("index");
            return mav;
        }
        String password = new securePassword().getEncrypt(input.getUserpwd(), homeMapper.getSalt(input.getUserid()));
        if(!password.equals(homeMapper.getUserPwd(input.getUserid()))) {
            System.out.println(homeMapper.getUserPwd(input.getUserid()));
            mav.addObject("sha_result", "비밀번호가 다릅니다");
            mav.setViewName("index");
            return mav;
        }

        String encryptPwd = new securePassword().getEncrypt(input.getUserpwd(), UserSalt);
        UserVO uVO = homeMapper.login(input.getUserid(), encryptPwd);
        mav.addObject("sha_result", "로그인 성공");
        session.setAttribute("userid",uVO.getUserid());

        mav.setViewName("index");

        return mav;
    }

    @PostMapping ("signupOk")
    public ModelAndView signup(UserVO uVO) throws NoSuchAlgorithmException {

        ModelAndView mav = new ModelAndView();

        try {
            securePassword sp = new securePassword();
            String salt = sp.salt(uVO.getUserpwd());
            System.out.println(salt);
            uVO.setSalt(salt);
            homeMapper.saveUserInfo(uVO);
            String encryptPassword = sp.getEncrypt(homeMapper.getUserPwd(uVO.getUserid()), homeMapper.getSalt(uVO.getUserid()));
            int result = homeMapper.updateUserPwd(encryptPassword, uVO.getUserid());
            System.out.println("회원가입 여부" + result);
            System.out.println(salt);
            System.out.println(encryptPassword);

            if (result == 1) {
                mav.addObject("sha_result", "회원가입 성공");
            } else {
                mav.addObject("sha_result", "회원가입 실패");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mav.addObject("sha_result", "이미 존재하는 아이디입니다");
        }


//        mav.setViewName("index");

        mav.setViewName("index");

        return mav ;

    }



}
