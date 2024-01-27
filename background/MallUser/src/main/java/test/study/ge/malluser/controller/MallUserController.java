package test.study.ge.malluser.controller;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import test.study.ge.malluser.utils.VerifyCodeUtils;
import org.springframework.web.bind.annotation.*;
import test.study.ge.malluser.controller.param.MallParam;
import test.study.ge.malluser.dao.entity.MallUser;
import test.study.ge.malluser.service.MallGoodClient;
import test.study.ge.malluser.service.MallOrderClient;
import test.study.ge.malluser.service.MallUserService;
import test.study.ge.malluser.utils.Result;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/malluser-manage-api/v1")
public class MallUserController {
    @Resource
    private MallUserService mallUserService;
    @Resource
    private MallOrderClient mallOrderClient;
    @Resource
    private MallGoodClient mallGoodClient;


    //生成验证码图片
    @GetMapping("/verifyImg")
    public void generateVerifyImg(HttpSession session,
                                  HttpServletResponse response)
            throws IOException {
        //生成随机验证码
        String code = VerifyCodeUtils.generateVerifyCode(4);
        //将验证码存入session，便于与用户的输入进行比对
        session.setAttribute("code", code);
        //设置响应的类型
        response.setContentType("image/png");
        //将验证码写入到图片中，并通过response响应图片
        ServletOutputStream os = response.getOutputStream();
        VerifyCodeUtils.outputImage(180, 50, os, code);
    }

    //校验验证码
    @PostMapping("matchVerifyCode")
    @ResponseBody
    public Result matchVerifyCode(@RequestParam String verifyCode, HttpSession session) {
        String code = session.getAttribute("code").toString();
        System.out.println("code = " + code);
        System.out.println("verifyCode = " + verifyCode);
        if (code.equalsIgnoreCase(verifyCode)) {
            return Result.success();
        } else {
            return Result.error("200", "验证码输入错误，请重新输入！");
        }
    }

    @PutMapping(value = "/userLogin")
//    @RequestMapping(value = "/userLogin", method = RequestMethod.PUT)
    @ResponseBody
    public Result userLogin(@RequestBody @Validated MallParam mallUserParam) {
        String mallUserName = mallUserParam.getMallUserName();
        String mallUserPassword = mallUserParam.getMallUserPassword();
        Result sr = mallUserService.doLogin(mallUserName, mallUserPassword);
        if ("0".equals(sr.getCode())) {
            return Result.success(sr.getData());
        } else {
            return Result.error(sr.getCode(), sr.getMsg());
        }
    }

    @PutMapping(value = "/userRegister")
    @ResponseBody
    public Result userRegister(@RequestBody MallParam mallUserParam) {
        String mallUserName = mallUserParam.getMallUserName();
        String mallUserPassword = mallUserParam.getMallUserPassword();
        String mallUserRealName = mallUserParam.getMallUserRealName();
        String mallUserGender = mallUserParam.getMallUserGender();
        //根据用户名查询user对象
        //mallOrder mallOrderImpl.get
        MallUser userDB = mallUserService.findUserByUserName(mallUserName);
        if (!ObjectUtils.isEmpty(userDB)) {  //用户名已存在
            return Result.error("301", "此用户名已存在，请重新输入");
        } else if (!mallUserService.mallUserRegister(mallUserName, mallUserRealName, mallUserPassword, mallUserGender)) {   //执行添加
            return Result.error("302", "注册失败！");
        }
        MallUser returnMallUser = new MallUser();
        returnMallUser.setMallUserName(mallUserName);
        returnMallUser.setMallUserPassword(mallUserPassword);
        returnMallUser.setMallUserRealName(mallUserRealName);
        returnMallUser.setMallUserGender(mallUserGender);

        return Result.success(returnMallUser);

    }

    @RequestMapping(value = "/getAllGoodsOfUserName", method = RequestMethod.PUT)
    public Result getOrderAndAllGoodsOfUserName(@RequestBody MallParam mallParam) {
        System.out.println(mallParam.getMallUserName());
        List<HashMap> returnList = new ArrayList<>();

        Result orderResult = mallOrderClient.getOrdersByUserName(mallParam);
        List mallOrders = (List) orderResult.getData();
        for (int i = 0; i < mallOrders.size(); i++) {
            List<Long> allMallGoodIds = new ArrayList<>();
            HashMap mallOrderMap = (HashMap) mallOrders.get(i);
            String mallOrderName = (String) mallOrderMap.get("mallOrderName");
            Long mallOrderId = ((Integer) mallOrderMap.get("mallOrderId")).longValue();
            mallParam.setMallOrderId(mallOrderId);
            Result mallGoodIdResult = mallOrderClient.getAllGoodIdsOfOrder(mallParam);
            List mallGoodIdsInteger = (List) mallGoodIdResult.getData();
            List<Long> mallGoodIds = new ArrayList<>();
            for (int m = 0; m < mallGoodIdsInteger.size(); m++) {
                Integer mv = (Integer) mallGoodIdsInteger.get(m);
                Long mallGoodId = mv.longValue();
                mallGoodIds.add(mallGoodId);
            }

            allMallGoodIds.addAll(mallGoodIds);

            MallParam mp2 = new MallParam();
            mp2.setMallGoodsList(allMallGoodIds);

            Result mallGoodResult = mallGoodClient.findGoodsByGoodIds(mp2);
            List mallGoods = (List) mallGoodResult.getData();
            for (int j = 0; j < mallGoods.size(); j++) {
                HashMap mallGoodMap = (HashMap) mallGoods.get(j);
                mallGoodMap.put("mallOrderName", mallOrderName);
                returnList.add(mallGoodMap);
            }
        }
        return Result.success(returnList);
    }

    @RequestMapping(value = "/getAllGoods", method = RequestMethod.PUT)
    public Result getAllGoods() {
        return mallGoodClient.getAllGoods();

    }

    @RequestMapping(value = "/saveUserOrders", method = RequestMethod.POST)
    @ResponseBody
    public Result saveUserOrders(@RequestBody MallParam mallParam) {
        Long mallUserId = mallParam.getMallUserId();
        String mallUserName = mallParam.getMallUserName();
        List<Long> mallGoods = mallParam.getMallGoodsList();
        return mallOrderClient.saveUserOrders(mallParam);

    }
}
