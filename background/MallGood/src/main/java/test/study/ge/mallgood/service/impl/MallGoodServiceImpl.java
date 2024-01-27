package test.study.ge.mallgood.service.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import test.study.ge.mallgood.dao.entity.MallGood;
import test.study.ge.mallgood.dao.repo.MallGoodRepository;
import test.study.ge.mallgood.service.MallGoodService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MallGoodServiceImpl implements MallGoodService {
    @Resource
    MallGoodRepository mallGoodRepository;
    @Override
//    @Cacheable(value = {"mallGood"},keyGenerator = "springCacheCustomKeyGenerator",cacheManager = "cacheManager1Minute")
    public List<MallGood> findGoodsByGoodIds(List<Long> mallGoodIds) {
        return mallGoodRepository.findAllById(mallGoodIds);
    }

    @Override
//    @Cacheable(value = {"mallGood"},keyGenerator = "springCacheCustomKeyGenerator",cacheManager = "cacheManager1Minute")
    public List<MallGood> getAllGoods() {
        return mallGoodRepository.findAll();
    }

    @Override
//    @Cacheable(value = {"mallGood"},key = "#root.args[0].id",cacheManager = "cacheManager1Minute")
    public MallGood findMallGoodById(Long mallGoodId) {
        return mallGoodRepository.findById(mallGoodId).get();
    }

    @Override
//    @Cacheable(value = {"mallGood"},key = "#root.args[0].id",cacheManager = "cacheManager1Minute")
    public MallGood addMallGood(MallGood mallGood) {

        return mallGoodRepository.save(mallGood);
    }


}
