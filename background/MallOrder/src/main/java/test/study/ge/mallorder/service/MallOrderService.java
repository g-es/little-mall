package test.study.ge.mallorder.service;

import test.study.ge.mallorder.dao.entity.MallOrder;

import java.util.List;

public interface MallOrderService {
    List<MallOrder> getOrdersByUserName(String mallUserName);

    MallOrder saveUserOrders(Long mallUserId, String mallUserName, List<Long> mallGoods);

    List<Long> getAllGoodIdsOfUser(String mallUserName);

    List<Long> getAllGoodIdsOfOrder(Long mallOrderId);
}
