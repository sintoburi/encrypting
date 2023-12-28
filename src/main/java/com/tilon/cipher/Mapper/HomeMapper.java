package com.tilon.cipher.Mapper;

import com.tilon.cipher.VO.UserVO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface HomeMapper {
    void saveUserInfo(UserVO uVO);

    String getSalt(String userid);


    String getUserPwd(String userid);

    int updateUserPwd(String encryptPassword, String userid);

    UserVO login(String userid, String encryptPwd);
}
