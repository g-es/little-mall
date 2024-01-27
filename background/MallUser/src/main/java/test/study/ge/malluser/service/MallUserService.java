package test.study.ge.malluser.service;

import test.study.ge.malluser.controller.param.MallParam;
import test.study.ge.malluser.dao.entity.MallUser;
import test.study.ge.malluser.utils.Result;

import java.util.List;

public interface MallUserService {

    Result doLogin(String mallUserName, String mallUserPassword);

    boolean mallUserRegister(String mallUserName, String mallUserRealName, String mallUserPassword, String mallUserGender);

    MallUser findUserByUserName(String mallUserName);
}
