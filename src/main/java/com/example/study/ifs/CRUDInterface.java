package com.example.study.ifs;

import com.example.study.model.network.Header;

public interface CRUDInterface<Req,Res> {
    //반드시 작성해야 할 api 명세
    Header<Res> create(Header<Req> request);

    Header<Res> read(Long id);

    Header<Res> update(Header<Req> request);

    Header delete(Long id);
}
