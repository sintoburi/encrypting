package com.tilon.cipher.Controller;

import com.tilon.cipher.Service.AesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
    @Autowired
    AesService service;

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();

        System.out.println(service.sel("a"));

        mv.setViewName("index");

        return mv;
    }


}
