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

@Controller
public class AesController {

    @Autowired
    AesService service;

    private static String KEY = "aaaaaaaaaabbbbbbbbbbcccccccccc12";
    private static String IV = "asdfasdfasdfasdf";

    //암호화
    @PostMapping("/aes256_encode")
    public ModelAndView aes256_encode(String inputText, String padding) throws Exception{

        ModelAndView mv = new ModelAndView();

        System.out.println("인코딩/들어온 값:"+inputText);
        System.out.println("padding : " + padding);

        String result="";

        try {

            String algorithms = "AES/CBC/" + padding;
            Cipher cipher = Cipher.getInstance(algorithms);

            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);

            byte[] encrypted = cipher.doFinal(inputText.getBytes(StandardCharsets.UTF_8));
            result = Base64.getEncoder().encodeToString(encrypted);

            System.out.println("인코딩 결과:" + result);
            mv.addObject("en_result", result);

            int db_insert = service.add(padding, inputText, result, "EN" );
            System.out.println("DB삽입결과"+db_insert);

        } catch (javax.crypto.IllegalBlockSizeException e) {

            String err = "입력 길이가 16바이트의 배가 되지 않음";
            mv.addObject("result", err);


        }catch(Exception e) {
            e.printStackTrace();
            String err = "형용할 수 없는 에러가 발생하였습니다";
            mv.addObject("result",err);
        }

        mv.setViewName("index");
        return mv;

    }

    //복호화
    @PostMapping ("/AES256_decode")
    public ModelAndView  decode (String inputText, String padding){
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

            mv.addObject("de_result", decode);

        } catch (java.lang.IllegalArgumentException e) {
            String err = "입력 바이트는 base64 바이트의 경우 2바이트 이상이어야 합니다";
            mv.addObject("result", err);

        } catch (javax.crypto.IllegalBlockSizeException e) {
            String err = "패딩된 암호로 복호화할 때 입력 길이는 16의 배수여야 합니다";
            mv.addObject("result", err);

        } catch (Exception e){
            System.out.println("ERROR!!");
            String err = "형용할 수 없는 에러가 발생했습니다";
            mv.addObject("result",err);
            e.printStackTrace();
        }

        mv.setViewName("index");

        return mv;

    }

}
