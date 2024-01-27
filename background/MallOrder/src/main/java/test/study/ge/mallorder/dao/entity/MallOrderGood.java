package test.study.ge.mallorder.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class MallOrderGood {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "mall_order_good_id", nullable = false)
    private Long mallOrderGoodId;

    private Long mallOrderId;

    private Long mallGoodId;

}
