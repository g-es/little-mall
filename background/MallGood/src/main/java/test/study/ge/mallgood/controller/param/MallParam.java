package test.study.ge.mallgood.controller.param;

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
    private Long mallAreaId;
    private List<Long> mallGoodsList;
}
