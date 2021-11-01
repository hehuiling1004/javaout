package com.lemon.testcases;

import com.lemon.common.BaseTest;
import com.lemon.data.Environment;
import com.lemon.pojo.CaseInfo;
import com.lemon.utils.ExcelUtil;
import com.lemon.utils.RandomDataUtil;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

/**
 * 注册功能模块的测试类
 */
public class GetUserInfoTest extends BaseTest {

    @BeforeClass
    public void setup(){
        //准备三个未注册的手机号码
        String mobile_phone = RandomDataUtil.getUnregisterPhone();
        //保存到环境变量中
        Environment.envMap.put("mobile_phone",mobile_phone);
    }

    @Test(dataProvider = "getUserInfoDatas")
    public void user_info(CaseInfo caseInfo){
        //参数化替换
        caseInfo = paramsReplace(caseInfo);
        //发起接口请求
        Response res = request(caseInfo);
        //断言
        assertResponse(res,caseInfo);
        //提取响应结果
        extractToEnvironment(res,caseInfo);
    }

    @DataProvider
    public Object[] getUserInfoDatas(){
        List<CaseInfo> listDatas = ExcelUtil.readExcelSheetAllDatas(3);
        return listDatas.toArray();
    }

}
