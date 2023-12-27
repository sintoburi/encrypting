package com.tilon.cipher.Service;

import com.tilon.cipher.Mapper.AesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface AesService {

    String sel(String input);


}
