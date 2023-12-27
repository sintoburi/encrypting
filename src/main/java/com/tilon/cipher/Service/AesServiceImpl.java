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
        System.out.println("input : " + input);
        return mapper.sel(input);
    }
}
