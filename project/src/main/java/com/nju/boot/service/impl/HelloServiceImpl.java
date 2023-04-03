package com.nju.boot.service.impl;

import com.nju.boot.dao.HelloDao;
import com.nju.boot.service.HelloService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Service的实现
 */

@Service
public class HelloServiceImpl implements HelloService {
    @Resource
    private HelloDao helloDao;


}
