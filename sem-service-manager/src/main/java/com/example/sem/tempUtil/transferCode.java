package com.example.sem.tempUtil;

import com.example.sem.vo.UserVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sun.corba.se.spi.ior.ObjectKey;
import jdk.nashorn.internal.runtime.JSONFunctions;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class transferCode {

    public static void main(String[] args) throws JsonProcessingException {
//        File file =new File("");
//
//        String tem ="Temperature de conception (?)";
//        try {
//            byte[] bytes =tem.getBytes("text/tab-separated-values;charset=iso-8859-1");
//            System.out.println(new String(bytes));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        System.out.println();‘
        List<UserVO> userVOList =new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Double  random = Math.random()*100;
            UserVO vo=new UserVO();
                    vo.setAddress(i+"sheng");
                    vo.setName("huangman");
                    vo.setAge(random.intValue());
                    userVOList.add(vo);




        }
        ObjectMapper objectMapper = new ObjectMapper();
              Optional<UserVO> userVO = userVOList.stream().filter(e->{return e.getName().equals("huangman");}).findAny();
        ObjectWriter objectWriter = objectMapper.writer();
        System.out.println(objectWriter.writeValueAsString(userVO.get()));

        try {
            BufferedWriter bufferedWriter =new BufferedWriter(new FileWriter(new File("temp_f")));
            BufferedReader bufferedReader =new BufferedReader(new FileReader(new File("info.scp-inventory-service.log")));
            String temp = "";
            for (int i = 0; i < 10000000; i++) {

           ;
//                System.out.println(temp);
                if(! StringUtils.isEmpty(temp = bufferedReader.readLine())&&temp.contains("推送PRM")){
                    bufferedWriter.write(temp+"\n");
                };

            }

            bufferedWriter.flush();
            bufferedWriter.close();
            bufferedReader.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
