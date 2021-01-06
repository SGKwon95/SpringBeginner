package com.example.study.controller.api;

import com.example.study.controller.CRUDController;
import com.example.study.ifs.CRUDInterface;
import com.example.study.model.entity.OrderGroup;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.OrderGroupApiRequest;
import com.example.study.model.network.response.OrderGroupApiResponse;
import com.example.study.repository.OrderGroupRepository;
import com.example.study.service.OrderGroupApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/api/orderGroup")
public class OrderGroupApiContoller extends CRUDController<OrderGroupApiRequest, OrderGroupApiResponse, OrderGroup> {
/*
    @Autowired
    private OrderGroupApiLogicService orderGroupApiLogicService;

    @PostConstruct//생성자와 비슷한 역할
    public void init(){
        this.baseService = orderGroupApiLogicService;//추상클래스의 서비스를 이 클래스의 서비스와 연결
    }

 */
}
