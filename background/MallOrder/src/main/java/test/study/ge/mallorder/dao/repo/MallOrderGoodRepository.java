package test.study.ge.mallorder.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import test.study.ge.mallorder.dao.entity.MallOrderGood;

import java.util.List;

public interface MallOrderGoodRepository extends JpaRepository<MallOrderGood, Long> {
    List<MallOrderGood> findByMallOrderId(Long mallOrderId);
}