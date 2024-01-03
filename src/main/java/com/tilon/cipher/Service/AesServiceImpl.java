package com.tilon.cipher.Service;

import com.tilon.cipher.Mapper.AesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AesServiceImpl implements AesService{

    @Autowired
    AesMapper mapper;

    @Override
    public String sel(String input) {
//        System.out.println("input : " + input);
        return mapper.sel(input);
    }

    @Override
    public int add(String padding_val, String inputText, String result, String category, String encryption) {
        return mapper.add(padding_val, inputText, result, category,encryption);
    }
}
