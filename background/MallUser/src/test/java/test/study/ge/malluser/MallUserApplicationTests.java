package test.study.ge.malluser;

import net.sf.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import test.study.ge.malluser.malluser.controller.po.MallParam;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
class MallUserApplicationTests {

    @Test
    void contextLoads() {
    }

    @Resource
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void testUserLoginByMock() throws Exception {
        String mallUserName = "lk01";
        String mallUserPassword = "123456";
        String mallUserRealName = "lk01";
        String mallUserGender = "male";

        MallParam mallUserParam = new MallParam();
        mallUserParam.setMallUserGender(mallUserGender);
        mallUserParam.setMallUserName(mallUserName);
        mallUserParam.setMallUserPassword(mallUserPassword);
        mallUserParam.setMallUserRealName(mallUserRealName);

        JSONObject jsonObject = JSONObject.fromObject(mallUserParam);
        String jsonStr = jsonObject.toString();

        RequestBuilder request = MockMvcRequestBuilders.put("/malluser-manage-api/v1/userLogin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonStr)
                ;
        MvcResult mvcResult = mockMvc.perform(request).andReturn();
        int status = mvcResult.getResponse().getStatus();
        System.out.println(mvcResult.getResponse().getContentAsString());
        System.out.println("-----------------------" + status);
    }
}
