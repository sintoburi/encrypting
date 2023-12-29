package com.tilon.cipher.Controller;

import com.tilon.cipher.Service.AesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

@Controller
public class AesController {

    @Autowired
    AesService service;

    private static final String DEFAULT_KEY = "aaaaaaaaaabbbbbbbbbbcccccccccc12";
    private static final String DEFAULT_IV = "asdfasdfasdfasdf";

    //암호화
    @PostMapping("/aes256_encode")
    public ModelAndView aes256_encode(String inputText, String padding, String sel_cat, String key, String iv){

        ModelAndView mv = new ModelAndView();

        if (iv.equals("d") || iv == null || iv.isEmpty()) {
            iv = DEFAULT_IV;
        }

        if (key.equals("d") || key == null || key.isEmpty() ) {
            key = DEFAULT_KEY;
        }
        String result="";

        try {

            String algorithms = "AES/" + sel_cat + "/" + padding;
//            String algorithms = "AES/CBC/" + padding;
            Cipher cipher = Cipher.getInstance(algorithms);

            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");

            if (Objects.equals(sel_cat, "CBC")) {
                IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
                cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            }

            byte[] encrypted = cipher.doFinal(inputText.getBytes(StandardCharsets.UTF_8));
            result = Base64.getEncoder().encodeToString(encrypted);

            System.out.println("인코딩 결과:" + result);
            mv.addObject("result", result);

            int db_insert = service.add(padding, inputText, result, "EN", sel_cat );
            System.out.println("DB삽입결과"+db_insert);

        } catch (javax.crypto.IllegalBlockSizeException e) {
            e.printStackTrace();
            String err = "입력 길이가 16바이트의 배가 되지 않음";
            mv.addObject("err_msg", err);


        }catch(Exception e) {
            e.printStackTrace();
            String err = "형용할 수 없는 에러가 발생하였습니다";
            mv.addObject("err_msg",err);
        }

        mv.setViewName("index");
        return mv;

    }

    //복호화
    @PostMapping ("/AES256_decode")
    public ModelAndView  decode (String inputText, String padding, String sel_cat, String key, String iv){
        ModelAndView mv = new ModelAndView();

        if (iv.equals("d") || iv == null || iv.isEmpty()) {
            iv = DEFAULT_IV;
        }

        if (key.equals("d") || key == null || key.isEmpty() ) {
            key = DEFAULT_KEY;
        }

        System.out.println("디코딩/들어온 값:" + inputText);
        String result="";

//        String algorithms = "AES/CBC/PKCS5Padding";
        String algorithms = "AES/" + sel_cat + "/" + padding;

        try {

            Cipher cipher = Cipher.getInstance(algorithms);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");

            if (sel_cat.equals("CBC")) {
                IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
                cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, keySpec);
            }

            String decode = new String(cipher.doFinal(Base64.getDecoder().decode(inputText)), StandardCharsets.UTF_8);
            System.out.println(decode);

            mv.addObject("result", decode);
            int db_insert = service.add(padding, inputText, decode, "DE",sel_cat );

        } catch (java.lang.IllegalArgumentException e) {
            e.printStackTrace();
            String err = "입력 바이트는 base64 바이트의 경우 2바이트 이상이어야 합니다";
            mv.addObject("err_msg", err);

        } catch (javax.crypto.IllegalBlockSizeException e) {
            e.printStackTrace();
            String err = "패딩된 암호로 복호화할 때 입력 길이는 16의 배수여야 합니다";
            mv.addObject("err_msg", err);

        } catch (javax.crypto.BadPaddingException e) {
            e.printStackTrace();
            String err = "최종 블록이 제대로 패딩되지 않았습니다. 페딩 방식을 확인해주세요";
            mv.addObject("err_msg",err);


        } catch (Exception e){
            e.printStackTrace();
            System.out.println("ERROR!!");
            String err = "형용할 수 없는 에러가 발생했습니다";
            mv.addObject("err_msg",err);
        }

        mv.setViewName("index");

        return mv;

    }

}
