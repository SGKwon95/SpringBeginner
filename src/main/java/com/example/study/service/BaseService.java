package com.example.study.service;

import com.example.study.ifs.CRUDInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component//Autowired를 사용하기 위해 설정함
public abstract class BaseService<Req,Res,Entity> implements CRUDInterface<Req,Res> {
    //위의 Entity는 각 Repository의 JpaRepository<a,b>중 a에 해당하는 속성이다.
    @Autowired(required = false)
    protected JpaRepository<Entity,Long> baseRepository;

    //create, read, update, delete는 상속받은 자식 클래스에서 재정의해야하므로 여기서 작성하지 않는다.
    //각 service의 logic service에는 클래스 이름이 들어간 repository를 사용하는데(예를 들면, itemApiLogicService에서는
    //ItemRepository) 이를 추상화하여 사용한다.
}
