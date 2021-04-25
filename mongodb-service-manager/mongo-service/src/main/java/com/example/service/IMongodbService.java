package com.example.service;

import org.elasticsearch.client.security.user.User;

import java.util.List;

public interface IMongodbService {

    List<User> list();
}
