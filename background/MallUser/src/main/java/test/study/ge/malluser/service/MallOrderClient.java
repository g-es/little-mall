package test.study.ge.malluser.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import test.study.ge.malluser.controller.param.MallParam;
import test.study.ge.malluser.utils.Result;

@FeignClient(name="MallOrder-service")
public interface MallOrderClient {
    @RequestMapping(value = "/mallorders-manage-api/v1/saveUserOrders", method = RequestMethod.POST)
    @ResponseBody
    Result saveUserOrders(@RequestBody MallParam mallParam);
    @RequestMapping(value = "/mallorders-manage-api/v1/getAllGoodsOfUser", method = RequestMethod.POST)
    @ResponseBody
    Result getAllGoodsOfUser(@RequestBody MallParam mallParam);
    @RequestMapping(value = "/mallorders-manage-api/v1/getOrdersByUserName", method = RequestMethod.POST)
    @ResponseBody
    Result getOrdersByUserName(@RequestBody MallParam mallParam);

    @RequestMapping(value = "/mallorders-manage-api/v1/getAllGoodIdsOfOrder", method = RequestMethod.POST)
    @ResponseBody
    Result getAllGoodIdsOfOrder(@RequestBody MallParam mallParam);
}
