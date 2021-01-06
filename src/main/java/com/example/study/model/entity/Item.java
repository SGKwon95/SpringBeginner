package com.example.study.model.entity;

import com.example.study.model.enumclass.ItemStatus;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(exclude = {"orderDetailList","partner"})
@EntityListeners(AuditingEntityListener.class)
@Builder//생성자 대신 사용
@Accessors(chain = true)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ItemStatus status; // 등록 / 해지 / 검수 중

    private String name;

    private String title;

    private String content;

    private BigDecimal price;

    private String brandName;

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

    @ManyToOne
    private Partner partner;

    /*
    //1:N
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "item")//OrderDetail의 item과 매칭
    private List<OrderDetail> orderDetailList;
    */

    //Item 1: N orderDetail
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
    private List<OrderDetail> orderDetailList;
}
 /*
 fatchType: 2가지 종류가 있다.
 1. LAZY:지연로딩,
  SELECT * FROM ITEM WHERE ID = ?
 연관관계가 설정된 테이블은 select하지 않음
 1:N 관계에 적합하다.

 2. EAGER:즉시로딩,
 item_id = order_detail.item_id
 user_id = order_detail.user_id
 where item.id = ?
 연관관계가 설정된 모든 테이블에 join이 발생함, 시간이 더 오래 걸린다.
 1:1 관계에 적합하다.
 그 외에는 불러와야 할 데이터가 많을 경우 EAGER를 쓰면 성능저하가 발생할 수 있으므로
 LAZY를 사용하는것을 권장한다.

  */