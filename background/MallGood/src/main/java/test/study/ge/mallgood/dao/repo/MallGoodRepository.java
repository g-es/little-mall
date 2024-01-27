package test.study.ge.mallgood.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import test.study.ge.mallgood.dao.entity.MallGood;

public interface MallGoodRepository extends JpaRepository<MallGood, Long> {
}