package com.tilon.cipher.Mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AesMapper {

    String sel (String input);
    int add (String padding_val, String inputText, String result, String category);


}
