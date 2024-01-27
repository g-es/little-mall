package test.study.ge.mallorder.controller.param;

import lombok.Data;

import java.util.List;

@Data
public class MallParam {
    private String mallUserName;
    private Long mallUserId;
    private String mallUserRealName;
    private String mallUserPassword;
    private String mallUserGender;
    private String mallOrderName;
    private Long mallOrderId;
    private List<Long> mallGoodsList;
}
