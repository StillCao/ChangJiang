package back.wang.Test;

import back.wang.Dao.AdminQuery;
import back.wang.Dao.AlgoQuery;
import back.wang.Dao.DownAimInsert;
import back.wang.Dao.UpLoadInsert;
import back.wang.Domain.Admin;
import back.wang.Domain.BasicInfoAll;
import back.wang.Domain.Downaim;
import back.wang.Domain.Order_confirm;
import back.wang.Service.UpLoadService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import front.basic_page.Dao.QueryData;
import front.basic_page.Domain.Attr_value;
import front.basic_page.Domain.BasicData;
import front.basic_page.Domain.TypeLevel2;
import front.user_io.dao.UserQuery;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import utils.KeyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 描述:
 * 上传相关功能测试
 *
 * @author black-leaves
 * @createTime 2020-06-24  10:10
 */

public class UpLoadTest {

    @org.junit.Test
    public void testJson2Obj() {
        Map<String, String[]> map = new HashMap<>();
        map.put("data", new String[]{"{\"basic_info\":{\"NAME\":\"\",\"aploc\":\"\",\"docname\":\"\",\"up_time\":\"\",\"point1_lat\":-1,\"point1_lon\":-1,\"point2_lat\":-1,\"point2_lon\":-1,\"topic_w1\":\"\",\"topic_w2\":\"\",\"topic_w3\":\"\",\"topic_cfi\":\"\"},\"da_type2\":{\"t2_code\":\"1\",\"t2_name\":\"dad\",\"t1_id\":\"3\"},\"attr_value\":{\"v_name\":\"1\",\"v_id_k\":\"-1\"}}"});
        JSONObject jsonObject = JSON.parseObject(map.get("data")[0]);
        JSONObject basic_infoObj = jsonObject.getJSONObject("basic_info");
        JSONObject da_type2Obj = jsonObject.getJSONObject("da_type2");
        JSONObject attr_valueObj = jsonObject.getJSONObject("attr_value");

        System.out.println(da_type2Obj.getString("t2_name"));
        TypeLevel2 typeLevel2 = JSON.parseObject(da_type2Obj.toJSONString(), TypeLevel2.class);
        BasicInfoAll basic_info = JSON.parseObject(basic_infoObj.toJSONString(), BasicInfoAll.class);
        System.out.println(typeLevel2.toString());
        System.out.println(basic_info.toString());
        basic_info.setUp_id(1);
        basic_info.setDa_type(1);
        System.out.println(new UpLoadInsert().basicDataInsert(basic_info));
    }

    @org.junit.Test
    public void testJsonArray2List() {
        String JsonString = "{\"basic_info\":{\"NAME\":\"\",\"aploc\":\"\",\"docname\":\"\",\"up_time\":\"\",\"point1_lat\":-1,\"point1_lon\":-1,\"point2_lat\":-1,\"point2_lon\":-1,\"topic_w1\":\"\",\"topic_w2\":\"\",\"topic_w3\":\"\",\"topic_cfi\":\"\",\"da_type\":2,\"up_id\":1},\"attr_value\":[{\"v_id\":-1,\"v_name\":\"asdwqd\",\"v_id_k\":4},{\"v_id\":1,\"v_name\":\"asdwqd\",\"v_id_k\":4},{\"v_id\":2,\"v_name\":\"asdwqd\",\"v_id_k\":4}]}";

        JSONObject jsonObject = JSON.parseObject(JsonString);
        JSONObject basic_infoObj = jsonObject.getJSONObject("basic_info");
        JSONArray attr_valueArray = jsonObject.getJSONArray("attr_value");

//        JSONArray jsonArray = JSONArray.parseArray(attr_valueObj.toJSONString());
        List<Attr_value> attr_valueList = JSONObject.parseArray(attr_valueArray.toString(), Attr_value.class);
        attr_valueList.forEach(attr_value -> {
            System.out.println(attr_value.toString());
        });
    }

    @org.junit.Test
    public void printBasicInfoFields() {
        BasicInfoAll basicInfo = new BasicInfoAll();
        String data = basicInfo.toString();
        data = data.replaceAll("(=)(.*?)(,)", "");
//        data = data.replaceAll(" ",",");
        data = data.replaceAll(" ", ",:");
        System.out.println(data);
    }

    @org.junit.Test
    public void attrValueInsert() {
        Attr_value attr_value = new Attr_value();
        attr_value.setV_id_k(4);
        attr_value.setV_name("sadwqdqwd");
        System.out.println(new UpLoadInsert().attrValueInsert(attr_value));
    }

    @org.junit.Test
    public void attrValueQuery() {
        System.out.println(new UpLoadInsert().attrValueQuery(-1));
    }

    @org.junit.Test
    public void jsonChange() {
        String json = "[{\"key\":\"010000\",\"value\":\"管理学\",\"label\":\"管理学\",\"children\":[{\"key\":\"010100\",\"value\":\"管理科学与工程类\",\"label\":\"管理科学与工程类\",\"children\":[{\"key\":\"010101\",\"value\":\"管理科学\",\"label\":\"管理科学\"},{\"key\":\"010102\",\"value\":\"信息管理和信息系统\",\"label\":\"信息管理和信息系统\"},{\"key\":\"010103\",\"value\":\"工业工程\",\"label\":\"工业工程\"},{\"key\":\"010104\",\"value\":\"工程管理\",\"label\":\"工程管理\"},{\"key\":\"010105\",\"value\":\"产品质量工程\",\"label\":\"产品质量工程\"},{\"key\":\"010106\",\"value\":\"项目管理\",\"label\":\"项目管理\"}]},{\"key\":\"010200\",\"value\":\"工商管理类\",\"label\":\"工商管理类\",\"children\":[{\"key\":\"010201\",\"value\":\"工商管理\",\"label\":\"工商管理\"},{\"key\":\"010202\",\"value\":\"市场营销\",\"label\":\"市场营销\"},{\"key\":\"010203\",\"value\":\"商品学\",\"label\":\"商品学\"},{\"key\":\"010204\",\"value\":\"会计学\",\"label\":\"会计学\"},{\"key\":\"010205\",\"value\":\"审计学\",\"label\":\"审计学\"},{\"key\":\"010206\",\"value\":\"涉外会计\",\"label\":\"涉外会计\"},{\"key\":\"010207\",\"value\":\"会计信息系统\",\"label\":\"会计信息系统\"},{\"key\":\"010208\",\"value\":\"财务管理\",\"label\":\"财务管理\"},{\"key\":\"010209\",\"value\":\"财政金融\",\"label\":\"财政金融\"}]},{\"key\":\"010300\",\"value\":\"行政管理、公共管理类\",\"label\":\"行政管理、公共管理类\",\"children\":[{\"key\":\"010301\",\"value\":\"行政管理\",\"label\":\"行政管理\"},{\"key\":\"010302\",\"value\":\"公共关系学\",\"label\":\"公共关系学\"},{\"key\":\"010303\",\"value\":\"文秘\",\"label\":\"文秘\"},{\"key\":\"010304\",\"value\":\"公共事业管理\",\"label\":\"公共事业管理\"},{\"key\":\"010305\",\"value\":\"公共政策学\",\"label\":\"公共政策学\"},{\"key\":\"010306\",\"value\":\"国防教育与管理\",\"label\":\"国防教育与管理\"},{\"key\":\"010307\",\"value\":\"劳动关系\",\"label\":\"劳动关系\"},{\"key\":\"010308\",\"value\":\"劳动与社会保障\",\"label\":\"劳动与社会保障\"},{\"key\":\"010309\",\"value\":\"城市规划与管理\",\"label\":\"城市规划与管理\"}]},{\"key\":\"010400\",\"value\":\"图书档案学类\",\"label\":\"图书档案学类\",\"children\":[{\"key\":\"010401\",\"value\":\"图书馆学和档案学\",\"label\":\"图书馆学和档案学\"},{\"key\":\"010402\",\"value\":\"信息资源管理\",\"label\":\"信息资源管理\"}]}]},{\"key\":\"020000\",\"value\":\"理学\",\"label\":\"理学\",\"children\":[{\"key\":\"020100\",\"value\":\"电子信息科学类\",\"label\":\"电子信息科学类\",\"children\":[{\"key\":\"020101\",\"value\":\"电子信息科学与技术\",\"label\":\"电子信息科学与技术\"},{\"key\":\"020102\",\"value\":\"光信息科学与技术\",\"label\":\"光信息科学与技术理\"},{\"key\":\"020103\",\"value\":\"光电子技术科学\",\"label\":\"光电子技术科学\"},{\"key\":\"020104\",\"value\":\"信息科学技术\",\"label\":\"信息科学技术\"},{\"key\":\"020105\",\"value\":\"信息安全\",\"label\":\"信息安全\"},{\"key\":\"020106\",\"value\":\"科技防卫\",\"label\":\"科技防卫\"},{\"key\":\"020107\",\"value\":\"科学技术史\",\"label\":\"科学技术史\"}]},{\"key\":\"020200\",\"value\":\"数学类\",\"label\":\"数学类\",\"children\":[{\"key\":\"020201\",\"value\":\"数学与应用数学\",\"label\":\"数学与应用数学\"},{\"key\":\"020202\",\"value\":\"信息与计算科学\",\"label\":\"信息与计算科学\"}]},{\"key\":\"020300\",\"value\":\"物理学类\",\"label\":\"物理学类\",\"children\":[{\"key\":\"020301\",\"value\":\"物理学\",\"label\":\"物理学\"},{\"key\":\"020302\",\"value\":\"应用物理学\",\"label\":\"应用物理学\"},{\"key\":\"020303\",\"value\":\"声学\",\"label\":\"声学\"},{\"key\":\"020304\",\"value\":\"光学工程\",\"label\":\"光学工程\"}]},{\"key\":\"020400\",\"value\":\"化学类及化学工程类\",\"label\":\"化学类及化学工程类\",\"children\":[{\"key\":\"020401\",\"value\":\"化学\",\"label\":\"化学\"},{\"key\":\"020402\",\"value\":\"应用化学\",\"label\":\"应用化学\"},{\"key\":\"020403\",\"value\":\"生物化工\",\"label\":\"生物化工\"},{\"key\":\"020404\",\"value\":\"化学工程与工艺\",\"label\":\"化学工程与工艺\"},{\"key\":\"020405\",\"value\":\"精细化工\",\"label\":\"精细化工\"},{\"key\":\"020406\",\"value\":\"化工设备与机械\",\"label\":\"化工设备与机械\"}]},{\"key\":\"020500\",\"value\":\"生物科学及生物技术类\",\"label\":\"生物科学及生物技术类\",\"children\":[{\"key\":\"020501\",\"value\":\"生物科学技术\",\"label\":\"生物科学技术\"},{\"key\":\"020502\",\"value\":\"生物工程\",\"label\":\"生物工程\"},{\"key\":\"020503\",\"value\":\"生物信息学\",\"label\":\"生物信息学\"}]},{\"key\":\"020600\",\"value\":\"天文地质地理类\",\"label\":\"天文地质地理类\",\"children\":[{\"key\":\"020601\",\"value\":\"天文学\",\"label\":\"天文学\"},{\"key\":\"020602\",\"value\":\"地质学\",\"label\":\"地质学\"},{\"key\":\"020603\",\"value\":\"地球化学\",\"label\":\"地球化学\"},{\"key\":\"020604\",\"value\":\"地球物理学\",\"label\":\"地球物理学\"},{\"key\":\"020605\",\"value\":\"地理信息系统\",\"label\":\"地理信息系统\"},{\"key\":\"020606\",\"value\":\"地理科学\",\"label\":\"地理科学\"},{\"key\":\"020607\",\"value\":\"地理信息科学与技术\",\"label\":\"地理信息科学与技术\"},{\"key\":\"020608\",\"value\":\"地球与空间科学\",\"label\":\"地球与空间科学\"},{\"key\":\"020609\",\"value\":\"大气科学\",\"label\":\"大气科学\"}]},{\"key\":\"020700\",\"value\":\"力学类\",\"label\":\"力学类\",\"children\":[{\"key\":\"020701\",\"value\":\"力学\",\"label\":\"力学\"},{\"key\":\"020702\",\"value\":\"应用力学\",\"label\":\"应用力学\"}]},{\"key\":\"020800\",\"value\":\"系统科学类\",\"label\":\"系统科学类\",\"children\":[{\"key\":\"020801\",\"value\":\"系统理论\",\"label\":\"系统理论\"},{\"key\":\"020802\",\"value\":\"系统科学与工程\",\"label\":\"系统科学与工程\"}]},{\"key\":\"020900\",\"value\":\"环境科学与安全类\",\"label\":\"环境科学与安全类\",\"children\":[{\"key\":\"020901\",\"value\":\"环境科学\",\"label\":\"环境科学\"},{\"key\":\"020902\",\"value\":\"环境工程\",\"label\":\"环境工程\"},{\"key\":\"020903\",\"value\":\"安全工程\",\"label\":\"安全工程\"},{\"key\":\"020904\",\"value\":\"生态学\",\"label\":\"生态学\"}]}]},{\"key\":\"030000\",\"value\":\"工学\",\"label\":\"工学\",\"children\":[{\"key\":\"030100\",\"value\":\"机械类\",\"label\":\"机械类\",\"children\":[{\"key\":\"030101\",\"value\":\"机械设计制造及其自动化\",\"label\":\"机械设计制造及其自动化\"},{\"key\":\"030102\",\"value\":\"机械电子工程/机电一体化\",\"label\":\"机械电子工程/机电一体化\"},{\"key\":\"030103\",\"value\":\"机械工程及自动化\",\"label\":\"机械工程及自动化\"},{\"key\":\"030104\",\"value\":\"机械制造工艺与设备\",\"label\":\"机械制造工艺与设备\"},{\"key\":\"030105\",\"value\":\"制造工程\",\"label\":\"制造工程\"},{\"key\":\"030106\",\"value\":\"制造自动化与测控技术\",\"label\":\"制造自动化与测控技术\"},{\"key\":\"030107\",\"value\":\"材料成型及控制工程\",\"label\":\"材料成型及控制工程\"},{\"key\":\"030108\",\"value\":\"工业设计\",\"label\":\"工业设计\"},{\"key\":\"030109\",\"value\":\"过程装备与控制工程\",\"label\":\"过程装备与控制工程\"}]},{\"key\":\"030200\",\"value\":\"仪器仪表类\",\"label\":\"仪器仪表类\",\"children\":[{\"key\":\"030201\",\"value\":\"测控技术与仪器\",\"label\":\"测控技术与仪器\"}]},{\"key\":\"030300\",\"value\":\"能源动力类\",\"label\":\"能源动力类\",\"children\":[{\"key\":\"030301\",\"value\":\"热能与动力工程\",\"label\":\"热能与动力工程\"},{\"key\":\"030302\",\"value\":\"电力系统及自动化\",\"label\":\"电力系统及自动化\"},{\"key\":\"030303\",\"value\":\"制冷与低温技术\",\"label\":\"制冷与低温技术\"},{\"key\":\"030304\",\"value\":\"核工程与核技术\",\"label\":\"核工程与核技术\"},{\"key\":\"030305\",\"value\":\"石油与天然气工程\",\"label\":\"石油与天然气工程\"}]},{\"key\":\"030400\",\"value\":\"材料类\",\"label\":\"材料类\",\"children\":[{\"key\":\"030401\",\"value\":\"材料科学与工程\",\"label\":\"材料科学与工程\"},{\"key\":\"030402\",\"value\":\"材料物理\",\"label\":\"材料物理\"},{\"key\":\"030403\",\"value\":\"材料化学\",\"label\":\"材料化学\"},{\"key\":\"030404\",\"value\":\"冶金工程\",\"label\":\"冶金工程\"},{\"key\":\"030405\",\"value\":\"金属材料工程\",\"label\":\"金属材料工程\"},{\"key\":\"030406\",\"value\":\"无机非金属材料工程\",\"label\":\"无机非金属材料工程\"},{\"key\":\"030407\",\"value\":\"高分子材料与工程\",\"label\":\"高分子材料与工程\"},{\"key\":\"030408\",\"value\":\"电子封装技术\",\"label\":\"电子封装技术\"}]},{\"key\":\"030500\",\"value\":\"轻工纺织食品类\",\"label\":\"轻工纺织食品类\",\"children\":[{\"key\":\"030501\",\"value\":\"食品科学与工程\",\"label\":\"食品科学与工程\"},{\"key\":\"030502\",\"value\":\"轻化工程\",\"label\":\"轻化工程\"},{\"key\":\"030503\",\"value\":\"包装工程\",\"label\":\"包装工程\"},{\"key\":\"030504\",\"value\":\"印刷工程\",\"label\":\"印刷工程\"},{\"key\":\"030505\",\"value\":\"纺织工程\",\"label\":\"纺织工程\"},{\"key\":\"030506\",\"value\":\"服装设计与工程\",\"label\":\"服装设计与工程\"}]},{\"key\":\"030600\",\"value\":\"土建类\",\"value\":\"土建类\",\"children\":[{\"key\":\"030601\",\"value\":\"土木工程\",\"value\":\"土木工程\"},{\"key\":\"030602\",\"value\":\"道路与桥梁\",\"value\":\"土木工程\"},{\"key\":\"030603\",\"value\":\"建筑学\",\"value\":\"土木工程\"},{\"key\":\"030604\",\"value\":\"建筑工程\",\"value\":\"土木工程\"},{\"key\":\"030605\",\"value\":\"工业与民用建筑\",\"value\":\"土木工程\"},{\"key\":\"030606\",\"value\":\"工程造价管理\",\"value\":\"土木工程\"},{\"key\":\"030607\",\"value\":\"建筑环境与设备工程\",\"value\":\"土木工程\"},{\"key\":\"030608\",\"value\":\"给排水科学与工程\",\"value\":\"土木工程\"},{\"key\":\"030609\",\"value\":\"供热通风与空调工程\",\"value\":\"土木工程\"}]},{\"key\":\"030700\",\"value\":\"制药工程类\",\"value\":\"制药工程类\",\"children\":[{\"key\":\"030701\",\"value\":\"制药工程\",\"label\":\"制药工程\"}]},{\"key\":\"030800\",\"value\":\"交通运输类\",\"value\":\"交通运输类\",\"children\":[{\"key\":\"030801\",\"value\":\"交通运输\",\"label\":\"交通运输\"},{\"key\":\"030803\",\"value\":\"物流工程\",\"label\":\"物流工程\"},{\"key\":\"030804\",\"value\":\"油气储运工程\",\"label\":\"油气储运工程\"},{\"key\":\"030805\",\"value\":\"轮机工程\",\"label\":\"轮机工程\"},{\"key\":\"030806\",\"value\":\"飞行技术\",\"label\":\"飞行技术\"},{\"key\":\"030807\",\"value\":\"航海技术\",\"label\":\"航海技术\"},{\"key\":\"030808\",\"value\":\"海事管理\",\"label\":\"海事管理\"}]},{\"key\":\"030900\",\"value\":\"航空航天类\",\"label\":\"航空航天类\",\"children\":[{\"key\":\"030901\",\"label\":\"飞行器设计与工程\",\"value\":\"飞行器设计与工程\"},{\"key\":\"030902\",\"label\":\"飞行器动力工程\",\"value\":\"飞行器动力工程\"},{\"key\":\"030903\",\"label\":\"飞行器制造工程\",\"value\":\"飞行器制造工程\"},{\"key\":\"030904\",\"label\":\"飞行器环境与生命保障工程\",\"value\":\"飞行器环境与生命保障工程\"}]},{\"key\":\"031000\",\"value\":\"船舶与海洋工程类\",\"label\":\"船舶与海洋工程类\",\"children\":[{\"key\":\"031001\",\"value\":\"船舶与海洋工程\",\"label\":\"船舶与海洋工程\"}]},{\"key\":\"031100\",\"value\":\"水利类\",\"label\":\"船舶与海洋工程类\",\"children\":[{\"key\":\"031101\",\"value\":\"水利水电工程\",\"label\":\"船舶与海洋工程类\"},{\"key\":\"031102\",\"value\":\"水文与水资源工程\",\"label\":\"船舶与海洋工程类\"},{\"key\":\"031103\",\"value\":\"港口航道与海岸工程\",\"label\":\"船舶与海洋工程类\"}]},{\"key\":\"031200\",\"value\":\"测绘类\",\"children\":[{\"key\":\"031201\",\"value\":\"测绘工程\"},{\"key\":\"031202\",\"value\":\"遥感科学与技术\"},{\"key\":\"031203\",\"value\":\"空间信息与数字技术\"}]},{\"key\":\"031300\",\"value\":\"公安技术类\",\"children\":[{\"key\":\"031301\",\"value\":\"公安技术\"}]},{\"key\":\"031400\",\"value\":\"武器类\",\"children\":[{\"key\":\"031401\",\"value\":\"武器系统与发射工程\"},{\"key\":\"031402\",\"value\":\"探测制导与控制技术\"},{\"key\":\"031403\",\"value\":\"弹药工程与爆炸技术\"},{\"key\":\"031404\",\"value\":\"特种能源工程与烟火技术\"},{\"key\":\"031405\",\"value\":\"地面武器机动工程\"},{\"key\":\"031406\",\"value\":\"信息对抗技术\"}]},{\"key\":\"031500\",\"value\":\"生物医学工程类\",\"children\":[{\"key\":\"031501\",\"value\":\"生物医学工程\"}]},{\"key\":\"031600\",\"value\":\"计算机科学与技术类\",\"children\":[{\"key\":\"031601\",\"value\":\"计算机科学与技术\"},{\"key\":\"031602\",\"value\":\"计算机科学\"},{\"key\":\"031603\",\"value\":\"计算机工程\"},{\"key\":\"031604\",\"value\":\"计算机网络\"},{\"key\":\"031605\",\"value\":\"计算机应用\"},{\"key\":\"031606\",\"value\":\"软件工程\"},{\"key\":\"031607\",\"value\":\"计算机信息管理\"}]},{\"key\":\"031700\",\"value\":\"电气信息类\",\"children\":[{\"key\":\"031701\",\"value\":\"电气工程及其自动化\"},{\"key\":\"031702\",\"value\":\"电气信息工程\"},{\"key\":\"031703\",\"value\":\"通信工程\"},{\"key\":\"031704\",\"value\":\"自动化\"},{\"key\":\"031705\",\"value\":\"电子信息工程\"},{\"key\":\"031706\",\"value\":\"电子科学与技术\"},{\"key\":\"031707\",\"value\":\"集成电路设计与集成系统\"},{\"key\":\"031708\",\"value\":\"影视艺术技术\"},{\"key\":\"031709\",\"value\":\"广播电视工程\"}]},{\"key\":\"031800\",\"value\":\"军事学\",\"children\":[{\"key\":\"031801\",\"value\":\"军事思想及军事历史\"},{\"key\":\"031802\",\"value\":\"战术学\"},{\"key\":\"031803\",\"value\":\"战役学\"},{\"key\":\"031804\",\"value\":\"战略学\"},{\"key\":\"031805\",\"value\":\"军事后勤学与军事装备学\"},{\"key\":\"031806\",\"value\":\"军队政治工作学\"},{\"key\":\"031807\",\"value\":\"军制学\"},{\"key\":\"031808\",\"value\":\"军队指挥学\"}]}]},{\"key\":\"040000\",\"value\":\"经济学\",\"children\":[{\"key\":\"040100\",\"value\":\"经济学类\",\"children\":[{\"key\":\"040101\",\"value\":\"经济与金融\"},{\"key\":\"040102\",\"value\":\"经济学\"},{\"key\":\"040103\",\"value\":\"财政学\"},{\"key\":\"040104\",\"value\":\"金融学\"},{\"key\":\"040105\",\"value\":\"经济管理\"},{\"key\":\"040106\",\"value\":\"经济信息管理\"},{\"key\":\"040107\",\"value\":\"国际经济与贸易\"},{\"key\":\"040108\",\"value\":\"国际金融\"},{\"key\":\"040109\",\"value\":\"贸易经济\"}]}]},{\"key\":\"050000\",\"value\":\"文学\",\"children\":[{\"key\":\"050100\",\"value\":\"语言文学类\",\"children\":[{\"key\":\"050101\",\"value\":\"汉语言文学\"},{\"key\":\"050102\",\"value\":\"对外汉语\"},{\"key\":\"050103\",\"value\":\"英语\"},{\"key\":\"050104\",\"value\":\"商务英语\"},{\"key\":\"050105\",\"value\":\"外贸英语\"},{\"key\":\"050106\",\"value\":\"俄语\"},{\"key\":\"050107\",\"value\":\"德语\"},{\"key\":\"050108\",\"value\":\"法语\"},{\"key\":\"050109\",\"value\":\"西班牙语\"}]},{\"key\":\"050200\",\"value\":\"艺术类\",\"children\":[{\"key\":\"050201\",\"value\":\"音乐，作曲\"},{\"key\":\"050202\",\"value\":\"舞蹈\"},{\"key\":\"050203\",\"value\":\"美术学\"},{\"key\":\"050204\",\"value\":\"艺术设计\"},{\"key\":\"050205\",\"value\":\"绘画\"},{\"key\":\"050206\",\"value\":\"雕塑\"},{\"key\":\"050207\",\"value\":\"服装设计\"},{\"key\":\"050208\",\"value\":\"影视学\"},{\"key\":\"050209\",\"value\":\"导演，广播电视编导\"}]},{\"key\":\"050300\",\"value\":\"新闻传播学类\",\"children\":[{\"key\":\"050301\",\"value\":\"新闻学\"},{\"key\":\"050302\",\"value\":\"广播电视新闻\"},{\"key\":\"050303\",\"value\":\"广告学\"},{\"key\":\"050304\",\"value\":\"编辑出版学\"},{\"key\":\"050305\",\"value\":\"传播学\"},{\"key\":\"050306\",\"value\":\"媒体创意\"}]}]},{\"key\":\"060000\",\"value\":\"法学\",\"children\":[{\"key\":\"060100\",\"value\":\"法学类\",\"children\":[{\"key\":\"060101\",\"value\":\"法学\"},{\"key\":\"060102\",\"value\":\"马克思主义理论\"},{\"key\":\"060103\",\"value\":\"政治学与行政学\"},{\"key\":\"060104\",\"value\":\"思想政治教育\"},{\"key\":\"060105\",\"value\":\"国际政治\"},{\"key\":\"060106\",\"value\":\"国际经济法\"},{\"key\":\"060107\",\"value\":\"经济法\"},{\"key\":\"060108\",\"value\":\"社会学\"},{\"key\":\"060109\",\"value\":\"外交学\"}]}]},{\"key\":\"070000\",\"value\":\"哲学\",\"children\":[{\"key\":\"070100\",\"value\":\"哲学类\",\"children\":[{\"key\":\"070101\",\"value\":\"哲学（含伦理学）\"},{\"key\":\"070102\",\"value\":\"逻辑学\"},{\"key\":\"070103\",\"value\":\"宗教学\"}]}]},{\"key\":\"080000\",\"value\":\"教育学\",\"children\":[{\"key\":\"080100\",\"value\":\"教育学类\",\"children\":[{\"key\":\"080101\",\"value\":\"教育学\"},{\"key\":\"080102\",\"value\":\"体育教育\"},{\"key\":\"080103\",\"value\":\"学前教育\"},{\"key\":\"080104\",\"value\":\"职业技术教育\"},{\"key\":\"080105\",\"value\":\"特殊教育\"},{\"key\":\"080106\",\"value\":\"教育技术学\"}]}]},{\"key\":\"090000\",\"value\":\"医学\",\"children\":[{\"key\":\"090100\",\"value\":\"心理学类\",\"children\":[{\"key\":\"090101\",\"value\":\"心理学\"},{\"key\":\"090102\",\"value\":\"应用心理学\"}]},{\"key\":\"090200\",\"value\":\"医学类 \",\"children\":[{\"key\":\"090201\",\"value\":\"基础医学\"},{\"key\":\"090202\",\"value\":\"预防医学\"},{\"key\":\"090203\",\"value\":\"营养学\"},{\"key\":\"090204\",\"value\":\"临床医学与医学技术\"},{\"key\":\"090205\",\"value\":\"麻醉学\"},{\"key\":\"090206\",\"value\":\"放射医学\"},{\"key\":\"090207\",\"value\":\"眼视光学\"},{\"key\":\"090208\",\"value\":\"口腔医学\"},{\"key\":\"090209\",\"value\":\"中医学\"}]}]},{\"key\":\"100000\",\"value\":\"农学\",\"children\":[{\"key\":\"100100\",\"value\":\"农业类\",\"children\":[{\"key\":\"100101\",\"value\":\"农学\"},{\"key\":\"100102\",\"value\":\"农业工程\"},{\"key\":\"100103\",\"value\":\"森林资源\"},{\"key\":\"100104\",\"value\":\"园林\"},{\"key\":\"100105\",\"value\":\"园艺\"},{\"key\":\"100106\",\"value\":\"林业工程\"},{\"key\":\"100107\",\"value\":\"植物生产\"},{\"key\":\"100108\",\"value\":\"植物保护学\"},{\"key\":\"100109\",\"value\":\"茶学\"}]}]},{\"key\":\"110000\",\"value\":\"历史学\",\"children\":[{\"key\":\"110100\",\"value\":\"历史学类\",\"children\":[{\"key\":\"110101\",\"value\":\"历史学\"},{\"key\":\"110102\",\"value\":\"考古学\"},{\"key\":\"110103\",\"value\":\"博物馆学\"}]}]}]";
        JSONArray attr_valueArray = JSONArray.parseArray(json);
        UpLoadInsert insert = new UpLoadInsert();
        attr_valueArray.forEach(jsonObj -> {
            String s = ((JSONObject) jsonObj).getString("children");
            JSONArray jsonArray = JSONArray.parseArray(s);
            jsonArray.forEach(jsonObjs -> {

                String ss = ((JSONObject) jsonObjs).getString("children");
                JSONArray jsonArray2 = JSONArray.parseArray(ss);
                jsonArray2.forEach(jsonObj2 -> {
                    String v_name = ((JSONObject) jsonObj2).getString("value");

                    Attr_value attr_value = new Attr_value();
                    attr_value.setV_name(v_name);
                    attr_value.setV_id_k(2);
                    if (!insert.attrValueQueryName(v_name)) {
                        insert.attrValueInsert(attr_value);
                        System.out.println("插入" + v_name);
                    }
                });


            });
        });

    }

    @org.junit.Test
    public void loginAdmin() {
        AdminQuery adminQuery = new AdminQuery();
        Admin admin = new Admin();
        admin.setAccount("张铖");
        admin.setPassword("3333333333");
        System.out.println(adminQuery.loginAdmin(admin));

    }

    @org.junit.Test
    public void Time() {
        System.out.println(new Date().getTime());
    }

    @org.junit.Test
    public void printDownAimFields() {
        Downaim downaim = new Downaim();
        String data = downaim.toString();
        data = data.replaceAll("(=)(.*?)(,)", "");
//        data = data.replaceAll(" ",",");
        data = data.replaceAll(" ", ",:");
        System.out.println(data);
    }

    @org.junit.Test
    public void String2List() {
        String s = "123,123,434";
        List<String> strings = Arrays.asList((s.split(",")));
        List<Integer> codesInteger = strings.stream().map(Integer::parseInt).collect(Collectors.toList());

        System.out.println(codesInteger);
    }

    @org.junit.Test
    public void UpDateOrderConfirm() {
        DownAimInsert insert = new DownAimInsert();
        Order_confirm order_confirm = new Order_confirm();
        order_confirm.setDataId(8);
        order_confirm.setUserId(8);
        order_confirm.setDown_aim(1);
        order_confirm.setOrderCode(KeyUtils.generateUniqueKey());

        int id = insert.QueryOrderConfirmByIds(order_confirm.getUserId(), order_confirm.getDataId());
        order_confirm.setId(id);

        System.out.println(new DownAimInsert().UpDateOrderConfirm(order_confirm));
    }


    @org.junit.Test
    public void QueryOrderConfirmByIds() {
        System.out.println(new DownAimInsert().QueryOrderConfirmByIds(1, 2));
    }

    @org.junit.Test
    public void queryUserById() {
        System.out.println(new UserQuery().queryUserById(1));
    }

    @org.junit.Test
    public void mkDir() {

        File picProjDir = new File("D:\\kk\\hah");
        File pic = new File(picProjDir,"aa.txt");
        System.out.println(pic.getAbsolutePath());

//        if (!picProjDir.exists()) {
////            if (!picProjDir.mkdir()) {
////                if (picProjDir.getParentFile().mkdir()){
////                    picProjDir.mkdir();
////                }
////            }
//            picProjDir.mkdirs();
//
//            System.out.println("数据不存在，正在上传图片");
//        } else {
//            System.out.println("数据存在，添加图片");
//        }
    }

    @org.junit.Test
    public void StringSub() {
        String a = "0,1,2,3,4,5,6";
        List<String> aa =  Arrays.asList((a.split(",")));
        System.out.println(aa.subList(0,5).toString());
    }

    @org.junit.Test
    public void GenerateKey() {
        System.out.println(KeyUtils.generateUniqueKey());
    }

    @org.junit.Test
    public void QueryOrderConfirmAllByIds() {
        System.out.println(new DownAimInsert().QueryOrderConfirmAllById(1));
    }


    @org.junit.Test
    public void QueryDownAimById() {
        System.out.println(new DownAimInsert().QueryDownAimById(1));
    }

    @org.junit.Test
    public void queryUserById2() {
        System.out.println(new DownAimInsert().queryUserById(1));
    }

    @org.junit.Test
    public void queryOrderIdsByStatus() {
        System.out.println(new DownAimInsert().queryOrderByStatus(1));
    }

    @org.junit.Test
    public void queryBasicInfoById() {
        System.out.println(new DownAimInsert().queryBasicInfoById(1));
    }

    @org.junit.Test
    public void StringReplace() {
        String a = "adasda:\\asdasd\\ad1e1dasdas\\qdqdq11e1dsada";
        System.out.println(a.replace("\\","/"));
    }

    @org.junit.Test
    public void DeleteRelateByAlgoId() {
        System.out.println(new AlgoQuery().deleteRelateByAlgoId(15));
    }

    @org.junit.Test
    public void DeleteRelaChartByBasicInfoId() {
        System.out.println(new UpLoadInsert().deleteRelaChartByBasicInfoId(67));
    }

    @org.junit.Test
    public void mergeChunkFile() throws IOException {
        String filePath = "D:/fileTest";
        String filePathTemp = "D:/fileTemp";
        String filename = "沐川县84投影系列数据.zip";
        String guid = filename;

        File file = new File(filePathTemp + File.separator + guid);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                File partFile = new File(filePath + File.separator + filename);
                for (int i = 1; i <= files.length; i++) {
                    File s = new File(filePathTemp + File.separator + guid, i + ".part");
                    FileOutputStream destTempfos = new FileOutputStream(partFile, true);
                    FileUtils.copyFile(s, destTempfos);
                    destTempfos.close();
                }
                FileUtils.deleteDirectory(file);
            }
        }
    }

    @org.junit.Test
    public void updateBasicDataPathById()  {
        System.out.println(new QueryData().updateBasicDataPathById(1,"D:\\fileTest"));
    }









}
