package test.study.ge.mallgood.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class MallGood {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "mall_good_id", nullable = false)
    private Long mallGoodId;

    private String mallGoodName;
    private String mallGoodPrice;
    private String mallGoodType;
    private String mallGoodArea;
    private String mallGoodStatus;


}
