package test.study.ge.mallorder.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class MallOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "mall_order_id", nullable = false)
    private Long mallOrderId;

    private Long mallUserId;
    private String mallUserName;

    private String mallOrderName;

}
