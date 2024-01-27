package test.study.ge.mallorder.controller;

import org.springframework.web.bind.annotation.*;
import test.study.ge.mallorder.controller.param.MallParam;
import test.study.ge.mallorder.dao.entity.MallOrder;
import test.study.ge.mallorder.service.MallOrderService;
import test.study.ge.mallorder.utils.Result;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/mallorders-manage-api/v1")
public class MallOrderController {
    @Resource
    MallOrderService mallOrderService;

    @RequestMapping(value = "/getOrdersByUserName", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrdersByUserName(@RequestBody MallParam mallParam){
        String mallUserName = mallParam.getMallUserName();
        List<MallOrder> mallOrders = mallOrderService.getOrdersByUserName(mallUserName);

        return Result.success(mallOrders);
    }

    @RequestMapping(value = "/saveUserOrders", method = RequestMethod.POST)
    @ResponseBody
    public Result saveUserOrders(@RequestBody MallParam mallParam){
        Long mallUserId = mallParam.getMallUserId();
        String mallUserName = mallParam.getMallUserName();
        List<Long> mallGoods = mallParam.getMallGoodsList();
        MallOrder mallOrder = mallOrderService.saveUserOrders(mallUserId,mallUserName,mallGoods);
        return Result.success(mallOrder);
    }


    @RequestMapping(value = "/getAllGoodsOfUser", method = RequestMethod.POST)
    @ResponseBody
    public Result getAllGoodsOfUser(@RequestBody MallParam mallParam){
        String mallUserName = mallParam.getMallUserName();
        Long mallUserId = mallParam.getMallUserId();
        List<Long> mallGoods = mallOrderService.getAllGoodIdsOfUser(mallUserName);
        return Result.success(mallGoods);
    }

    @RequestMapping(value = "/getAllGoodIdsOfOrder", method = RequestMethod.POST)
    @ResponseBody
    public Result getAllGoodIdsOfOrder(@RequestBody MallParam mallParam){
        Long mallOrderId = mallParam.getMallOrderId();
        List<Long> mallGoodIds = mallOrderService.getAllGoodIdsOfOrder(mallOrderId);
        return Result.success(mallGoodIds);
    }
}
