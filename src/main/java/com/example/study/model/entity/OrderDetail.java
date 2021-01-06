package com.example.study.model.entity;

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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = {"orderGroup","item"})
//@ToString(exclude = {"user","item"})//이거 안쓰면 무한 ToString 재귀호출이 일어난다.
@EntityListeners(AuditingEntityListener.class)
@Builder//생성자 대신 사용
@Accessors(chain = true)
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    private LocalDateTime arrivalDate;

    private Integer quantity;

    private BigDecimal totalPrice;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy// 이렇게 하면 모든 entity의 createdBy, updatedBy에 자동으로 AdminServer라고 입력이 될 것이다.
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private String updatedBy;

    // OrderDetail N : 1 Item = 한 개의 아이템에 대해 여러개의 주문 목록이 있을 수 있다.
    @ManyToOne
    private Item item;

    // OrderDetail N : 1 OrderGroup
    @ManyToOne
    private OrderGroup orderGroup; // 반드시 OrderGroup의 mappedBy와 일치해야 한다.
    /*
    private LocalDateTime orderAt;

    //OrderDetail 입장에서 N, user는 1이므로
    @ManyToOne
    private User user; // Long userId에서 User user로 변경함

    @ManyToOne
    private Item item;
     */
}
