package com.example.esservice.service;

import com.example.esservice.util.Jsonutil;
import io.netty.util.internal.StringUtil;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class FileServiceImpl {


    public String parseFile(File file,String dir,String outfileName){
        BufferedWriter bufferedWriter=null;
        BufferedReader bufferedReader=null;
        try {

            File filedir =new File(dir);
            if(!filedir.exists()){
                log.info("create file directory filedir");
                filedir.mkdir() ;
            }
            int count =0;
            List  par=new ArrayList();
            FileReader fileReader =new FileReader(file);
             bufferedReader =new BufferedReader(fileReader);
            String temp = "";
            while (!StringUtil.isNullOrEmpty(temp= bufferedReader.readLine())){
                String[] s=temp.split(",");
                tempVO t= new tempVO();
                t.setPlatOrderSn(s[0]);
                t.setSource(s[1]);
                par.add(t);
                if(par.size()%1000==0){
                    count++;
                    File outfile =new File(dir+outfileName+count);
                    bufferedWriter =new BufferedWriter(new FileWriter(outfile));
                    bufferedWriter.write(Jsonutil.toJsonString(par));
                    bufferedWriter.flush();
                    par.clear();
                    bufferedWriter.close();
                }
            }
            count++;
            File outfile =new File(dir+outfileName+count);
            bufferedWriter =new BufferedWriter(new FileWriter(outfile));
            bufferedWriter.write(Jsonutil.toJsonString(par));
            bufferedWriter.flush();
            ;
        } catch (IOException e) {
            e.printStackTrace();
        }
         finally {
            try {
                if(bufferedWriter!=null){
                    bufferedWriter.close();
//                    bufferedReader.close();
                }
                if(bufferedReader!=null){
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return "";
    }


    @Data
    class  tempVO{
        private String platOrderSn;
        private String source;
    }
}

