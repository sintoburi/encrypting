package com.tilon.cipher.Service;

import com.tilon.cipher.VO.UserVO;

public interface HomeService {

    void saveUserInfo(UserVO uVO);

    String getSalt(String userid);


    String getUserPwd(String userid);

    int updateUserPwd(String encryptPassword, String userid);

    UserVO login(String userid, String encryptPwd);

}
