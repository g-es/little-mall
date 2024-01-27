package test.study.ge.mallgood.controller;

import org.springframework.web.bind.annotation.*;
import test.study.ge.mallgood.controller.param.MallParam;
import test.study.ge.mallgood.dao.entity.MallGood;
import test.study.ge.mallgood.service.MallGoodService;
import test.study.ge.mallgood.utils.Result;

import javax.annotation.Resource;
import java.util.List;
@RestController
@RequestMapping("/mallgoods-manage-api/v1")
public class MallGoodController {
    @RequestMapping(value = "/findGoodsByGoodIds01", method = RequestMethod.POST)
    @ResponseBody
    public String findGoodsByGoodIds01(@RequestParam("goodIds") List<Long> goodIds){
        for (int i = 0; i < goodIds.size(); i++) {
           System.out.println("Good id is : " + i);
        }
        return "findGoodsByGoodIds";
    }

    @Resource
    MallGoodService mallGoodService;

    @RequestMapping(value = "/findGoodsByGoodIds", method = RequestMethod.POST)
    @ResponseBody
    public Result findGoodsByGoodIds(@RequestBody MallParam mallParam){
        List<Long> mallGoodIds = mallParam.getMallGoodsList();
        List<MallGood> mallGoods =
            mallGoodService.findGoodsByGoodIds(mallGoodIds);
        return Result.success(mallGoods);
    }
    @RequestMapping(value = "/getAllGoods", method = RequestMethod.GET)
    public Result getAllGoods(){
        List<MallGood> mallGoods = mallGoodService.getAllGoods();
        return Result.success(mallGoods);
    }
}
