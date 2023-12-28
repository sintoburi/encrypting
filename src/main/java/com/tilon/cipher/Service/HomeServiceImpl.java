package com.tilon.cipher.Service;

import com.tilon.cipher.Mapper.HomeMapper;
import com.tilon.cipher.VO.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl implements HomeService{

    @Autowired
    HomeMapper mapper;

    @Override
    public void saveUserInfo(UserVO uVO) {

    }

    @Override
    public String getSalt(String userid) {
        return mapper.getSalt(userid);
    }

    @Override
    public String getUserPwd(String userid) {
        return mapper.getUserPwd(userid);
    }

    @Override
    public int updateUserPwd(String encryptPassword, String userid) {
        return mapper.updateUserPwd(encryptPassword, userid);
    }

    @Override
    public UserVO login(String userid, String encryptPwd) {
        return mapper.login(userid, encryptPwd);
    }
}
