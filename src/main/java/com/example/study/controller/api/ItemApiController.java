package com.example.study.controller.api;

import com.example.study.controller.CRUDController;
import com.example.study.ifs.CRUDInterface;
import com.example.study.model.entity.Item;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.ItemApiRequest;
import com.example.study.model.network.response.ItemApiResponse;
import com.example.study.service.ItemApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/api/item")
public class ItemApiController extends CRUDController<ItemApiRequest,ItemApiResponse, Item> {

/*
    @Autowired
    private ItemApiLogicService itemApiLogicService;

    @PostConstruct//생성자와 비슷한 역할
    public void init(){
        this.baseService = itemApiLogicService;

 */
}
