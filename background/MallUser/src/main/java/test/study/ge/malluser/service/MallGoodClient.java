package test.study.ge.malluser.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import test.study.ge.malluser.controller.param.MallParam;
import test.study.ge.malluser.utils.Result;

import java.util.List;

@FeignClient(name="MallGood-service")
public interface MallGoodClient {

    @RequestMapping(value = "/mallgoods-manage-api/v1/findGoodsByGoodIds01", method = RequestMethod.POST)
    String findGoodsByGoodIds01(@RequestParam("goodIds") List<Long> goodIds);
    @RequestMapping(value = "/mallgoods-manage-api/v1/findGoodsByGoodIds", method = RequestMethod.POST)
    Result findGoodsByGoodIds(@RequestBody MallParam mallParam);
    @RequestMapping(value = "/mallgoods-manage-api/v1/getAllGoods", method = RequestMethod.GET)
    Result getAllGoods();
}
