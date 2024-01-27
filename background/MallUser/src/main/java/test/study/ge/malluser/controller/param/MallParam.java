package test.study.ge.malluser.controller.param;

import lombok.Data;

import java.util.List;

@Data
public class MallParam {
    private Long mallUserId;
    private String mallUserName;
    private String mallUserRealName;
    private String mallUserPassword;
    private String mallUserGender;
    private String mallOrderName;
    private Long mallOrderId;
    private List<Long> mallGoodsList;
}
