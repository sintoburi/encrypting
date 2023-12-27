package com.tilon.cipher.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Controller
public class AesController {

    private static String KEY = "aaaaaaaaaabbbbbbbbbbcccccccccc12";
    private static String IV = "asdfasdfasdfasdf";

    @PostMapping("/aes256_encode")
    public ModelAndView aes256_encode(String inputText) throws Exception{

        ModelAndView mv = new ModelAndView();

        System.out.println("인코딩/들어온 값:"+inputText);

        String result="";

        String algorithms = "AES/CBC/PKCS5Padding";
        Cipher cipher = Cipher.getInstance(algorithms);

        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes());

        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);

        byte[] encrypted = cipher.doFinal(inputText.getBytes(StandardCharsets.UTF_8));
        result = Base64.getEncoder().encodeToString(encrypted);

        System.out.println(result);

        mv.setViewName("index");
        mv.addObject("result",result);

        return mv;


    }

    @PostMapping ("/AES256_decode")
    public ModelAndView  decode (String inputText){
        ModelAndView mv = new ModelAndView();

        System.out.println("디코딩/들어온 값:" + inputText);
        String result="";

        String algorithms = "AES/CBC/PKCS5Padding";

        try {

            Cipher cipher = Cipher.getInstance(algorithms);
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);
            String decode = new String(cipher.doFinal(Base64.getDecoder().decode(inputText)), StandardCharsets.UTF_8);
            System.out.println(decode);

            mv.addObject("result", decode);

        } catch (Exception e){
            System.out.println("ERROR!!");
            e.printStackTrace();
        }

        mv.setViewName("index");

        return mv;

    }

}
