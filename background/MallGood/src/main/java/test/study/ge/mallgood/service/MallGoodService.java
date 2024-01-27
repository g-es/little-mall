package test.study.ge.mallgood.service;

import test.study.ge.mallgood.dao.entity.MallGood;
import test.study.ge.mallgood.utils.Result;

import java.util.List;

public interface MallGoodService {
    public List<MallGood> findGoodsByGoodIds(List<Long> mallGoodIds);


    List<MallGood> getAllGoods();

    MallGood findMallGoodById(Long mallGoodId);

    MallGood addMallGood(MallGood mallGood);
}
