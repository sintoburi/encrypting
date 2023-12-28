package com.tilon.cipher.VO;

import lombok.Data;
import lombok.ToString;

@Data @ToString
public class UserVO {
    private int no;
    private String userid;
    private String userpwd;
    private String salt;
}
