package com.example.study.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {

    REGISTERED(0,"등록상태","사용자 등록상태"),
    UNREGISTERED(1,"해지","사용자 해지상태");

    private Integer id;
    private String title;
    private String description;
}
/*
이렇게 따로 enum을 관리함으로써 오타를 방지할 수 있다.
 */
