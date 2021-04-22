package com.example.esservice.stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationConfig;
import lombok.SneakyThrows;


import javax.annotation.Resource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class testUser {



    @SneakyThrows
    public static void main(String[] args) {
        List<User> users =new ArrayList<>();
        for (int i =0;i<10;i++){
            users.add(User.builder().
                    userName(i+"hm").age(i).build());
        }
        System.out.println("123111");
        ObjectMapper objectMapper =new ObjectMapper();
        ObjectWriter ObjectWrite =objectMapper.writer();
        List<User> uses = users.stream().filter(user -> {return user.getAge()>4;}).collect(Collectors.toList());
//        List<User.Apple> appleStream= users.stream().flatMap(user ->user.getApples().stream()).collect(Collectors.toList());
        List<String> names= (List)users.stream().flatMap(user->Stream.builder().add(user.getUserName()).build()).collect(Collectors.toList());
        List<String> name1=users.stream().map(e->{return e.getUserName();}).collect(Collectors.toList());

        List<Integer>  integers = users.stream().flatMapToInt(user -> {return IntStream.builder().add(user.getAge()).build();}).boxed().collect(Collectors.toList());
//        users.stream().isParallel();d
        System.out.println( ObjectWrite.writeValueAsString(integers));;

        Class<?> drivemysql = Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = null;
        System.out.println("connection"+connection);
        String url = "jdbc:mysql://127.0.0.1:3306/hm";
        System.out.println(url);
         connection=  DriverManager.getConnection(url,"root","123456");
        System.out.println("connection"+connection);
        PreparedStatement preparedStatement = connection.prepareStatement("select * from hm.house");
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        resultSet.next();
        ResultSetMetaData metaData = preparedStatement.getMetaData();


    }
}
