package front.basic_page.service;

import back.wang.Dao.AdminQuery;
import back.wang.Domain.DataConnector;
import front.basic_page.Dao.QueryBasicData;
import front.basic_page.Domain.BasicData;
import front.basic_page.Domain.DataDetails;
import front.basic_page.Domain.User;

/**
 * Fun:像前端返回用户详情页
 * Created by CW on 2020/6/17 5:20 下午
 */
public class DataDetailsContr {

    /**
     * 1.基础数据逻辑业务查询
     * @param id
     * @return 该条数据的简介信息；数据的上传者信息。
     */
    public DataDetails dataDetails(int id){
        QueryBasicData queryBasicData = new QueryBasicData();
        //查询基础数据信息
        BasicData basicData = queryBasicData.queryBasicDataById(id);
//        int up_id = basicData.getUp_id();
        //查询上传者信息
//        User user = queryBasicData.queryUserById(up_id);
        DataConnector dataConnector = new AdminQuery().queryDaConByBasicId(id);
        //将查询结果封装为JavaBean对象
//        DataDetails dataDetails = new DataDetails(basicData, user);
        DataDetails dataDetails = new DataDetails(basicData, dataConnector);
        return dataDetails;
    }
}
