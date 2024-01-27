package test.study.ge.malluser.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class MallUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "mall_user_id", nullable = false)
    private Long mallUserId;

    private String mallUserName;
    private String mallUserPassword;
    private String mallUserRealName;
    private String mallUserGender;
}

