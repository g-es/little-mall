package test.study.ge.malluser.service.impl;

import org.springframework.stereotype.Service;
import test.study.ge.malluser.dao.entity.MallUser;
import test.study.ge.malluser.dao.repo.MallUserRepository;
import test.study.ge.malluser.service.MallUserService;
import test.study.ge.malluser.utils.Result;

import javax.annotation.Resource;

@Service
public class MallUserServiceImpl implements MallUserService {

    @Resource
    MallUserRepository mallUserRepository;


    @Override
    public Result doLogin(String mallUserName, String mallUserPassword) {
        System.out.println(mallUserName);
        MallUser mallUser = mallUserRepository.findByMallUserNameEquals(mallUserName);
        if(mallUser == null){
            return  Result.error("305","wrong password or user name");
        }
        if(!mallUserPassword.equals(mallUser.getMallUserPassword())){
            return  Result.error("305","wrong password or user name");
        }else{
            return Result.success(mallUser);
        }
    }

    @Override
    public boolean mallUserRegister(String mallUserName, String mallUserRealName, String mallUserPassword, String mallUserGender) {
        MallUser saveUser = new MallUser();
        saveUser.setMallUserGender(mallUserGender);
        saveUser.setMallUserName(mallUserName);
        saveUser.setMallUserPassword(mallUserPassword);
        saveUser.setMallUserRealName(mallUserRealName);
        mallUserRepository.save(saveUser);

        return true;
    }

    @Override
    public MallUser findUserByUserName(String mallUserName) {
        return mallUserRepository.findByMallUserNameEquals(mallUserName);
    }

}
