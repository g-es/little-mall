package test.study.ge.mallorder.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import test.study.ge.mallorder.dao.entity.MallOrder;

import java.util.List;

public interface MallOrderRepository extends JpaRepository<MallOrder, Long> {
    List<MallOrder> findByMallUserNameEquals(String mallUserName);
}