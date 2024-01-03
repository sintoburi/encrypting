package com.tilon.cipher.VO;

import lombok.Data;

import java.util.Date;

@Data
public class AesVO {

    String padding_val;
    String TEXT;
    String result;
    Date time;
    String category;

}
