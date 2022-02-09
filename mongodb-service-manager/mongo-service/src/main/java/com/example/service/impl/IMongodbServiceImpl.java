package com.example.service.impl;

import com.example.service.IMongodbService;
import org.elasticsearch.client.security.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IMongodbServiceImpl  implements IMongodbService {
    @Override
    public List<User> list() {
        return null;
    }
}