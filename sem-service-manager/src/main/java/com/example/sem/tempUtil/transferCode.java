package com.example.sem.tempUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;

public class transferCode {

    public static void main(String[] args) {
        File file =new File("");

        String tem ="Temperature de conception (?)";
        try {
            byte[] bytes =tem.getBytes("text/tab-separated-values;charset=iso-8859-1");
            System.out.println(new String(bytes));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}
