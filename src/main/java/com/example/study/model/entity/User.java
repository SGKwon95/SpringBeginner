package com.example.study.model.entity;

import com.example.study.model.enumclass.UserStatus;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor // 모든 멤버변수를 매개변수로 가지는 생성자를 생성한다.
@NoArgsConstructor
@Data//getter, setter 자동 생성한다.
@Entity//DB의 데이터를 자바 객체로 매핑한다.
@Table(name="user")//이 파일의 클래스 이름이 DB에 존재하면 안 써도 된다.
@ToString(exclude = {"orderGroupList"})
@EntityListeners(AuditingEntityListener.class)
@Builder//생성자 대신 사용
@Accessors(chain = true)//이 어노테이션을 사용하면 user.setXX(), user.setYY() 이거를 user.setXX().setYY() 이렇게 이어 붙일 수 있다.
//또는 new User().setXX().setYY() 이런식으로 builder와 비슷하게 사용할 수 있다.
public class User {
    @Id // primary key를 의미한다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="account")//바로 아래줄의 변수명이 DB에 컬럼으로 존재하면 안 써도 된다.
    private String account;
    private String password;

    @Enumerated(EnumType.STRING)//enum으로 쓸 변수 지정한다.
    private UserStatus status;//REGISTERED / UNREGISTERED / WAITING


    private String email;
    private String phoneNumber; // java에서는 camel형 name규칙을 사용한다.

    private LocalDateTime registeredAt;

    private LocalDateTime unregisteredAt;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy// 이렇게 하면 모든 entity의 createdBy, updatedBy에 자동으로 AdminServer라고 입력이 될 것이다.
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private String updatedBy;

    /*
    //user는 1이고 Detail은 N이다
    @OneToMany(fetch=FetchType.LAZY,mappedBy = "user")//OrderDetail의 user column과 매칭됨
    private List<OrderDetail> orderDetailList;
    */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<OrderGroup> orderGroupList;
}
