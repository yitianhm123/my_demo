package com.example.esservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class Jsonutil {



    public static String toJsonString(Object o)  {

        ObjectMapper objectMapper =new ObjectMapper();

        ObjectWriter objectWriter = objectMapper.writer();

        String jsonString = null;
        try {
            jsonString = objectWriter.writeValueAsString(o);
            return jsonString;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;


    }
}
