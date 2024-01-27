package test.study.ge.mallorder.service.impl;

import org.springframework.stereotype.Service;
import test.study.ge.mallorder.dao.entity.MallOrder;
import test.study.ge.mallorder.dao.entity.MallOrderGood;
import test.study.ge.mallorder.dao.repo.MallOrderGoodRepository;
import test.study.ge.mallorder.dao.repo.MallOrderRepository;
import test.study.ge.mallorder.service.MallOrderService;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class MallOrderServiceImpl implements MallOrderService {
    @Resource
    MallOrderRepository mallOrderRepository;
    @Override
    public List<MallOrder> getOrdersByUserName(String mallUserName) {
        return mallOrderRepository.findByMallUserNameEquals(mallUserName);
    }

    @Resource
    MallOrderGoodRepository mallOrderGoodRepository;
    @Transactional
    @Override
    public MallOrder saveUserOrders(Long mallUserId, String mallUserName, List<Long> mallGoods) {
        System.out.println("get here!");
        MallOrder mallOrder = new MallOrder();
        mallOrder.setMallOrderName("O-" + System.currentTimeMillis()); // new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime())
        mallOrder.setMallUserId(mallUserId);
        mallOrder.setMallUserName(mallUserName);

        mallOrder = mallOrderRepository.save(mallOrder);

        List<MallOrderGood> mallOrderGoods = new ArrayList<>();
        System.out.println(mallGoods + " where are goods");
        for (int i = 0; i < mallGoods.size(); i++) {
            MallOrderGood mallOrderGood = new MallOrderGood();
            mallOrderGood.setMallOrderId(mallOrder.getMallOrderId());
            mallOrderGood.setMallGoodId(mallGoods.get(i));
            mallOrderGoods.add(mallOrderGood);
        }

        mallOrderGoodRepository.saveAll(mallOrderGoods);
        return mallOrder;
    }

    @Override
    // todo change the return list to the view object (order name + good information)
    public List<Long> getAllGoodIdsOfUser(String mallUserName) {
        List<Long> mallGoods = new ArrayList<>();
        List<MallOrder> mallOrders = getOrdersByUserName(mallUserName);
        for (int i = 0; i < mallOrders.size(); i++) {
            Long mallOrderId = mallOrders.get(i).getMallOrderId();
            List<MallOrderGood> mallOrderGoods =
                    mallOrderGoodRepository.findByMallOrderId(mallOrderId);
            for (int j = 0; j < mallOrderGoods.size(); j++) {
                MallOrderGood mallOrderGood = mallOrderGoods.get(j);
                mallGoods.add(mallOrderGood.getMallGoodId());
            }
        }
        return mallGoods;
    }

    @Override
    public List<Long> getAllGoodIdsOfOrder(Long mallOrderId) {
        List<MallOrderGood> orderGoods = mallOrderGoodRepository.findByMallOrderId(mallOrderId);
        List<Long> goodsIds = new ArrayList<>();
        for (MallOrderGood orderGood : orderGoods) {
            goodsIds.add(orderGood.getMallGoodId());
        }
        return goodsIds;
    }
}
