package test.study.ge.malluser.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import test.study.ge.malluser.dao.entity.MallUser;

public interface MallUserRepository extends JpaRepository<MallUser, Long> {
    MallUser findByMallUserNameEquals(String mallUserName);
}