package com.example.springtest.VO;

import lombok.Data;
import lombok.ToString;

@Data @ToString
public class UserVO {
    private int no;
    private String userid;
    private String userpwd;
    private String salt;
}
