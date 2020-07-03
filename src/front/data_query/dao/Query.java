package front.data_query.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import front.data_query.domain.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.*;

public class Query {

    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    //设置全局变量，用于存储文件列表中所需的数据id集合，便于查询每个数据id对应的缩略图
    List<Integer> imglist = new ArrayList<>();

    /**
     *静态查询
     * @param
     * @return
     */
    public List<Map> queryStatic() {

        //创建一个Lable对象，用于封装每个标签内容的数据总数和名称
        List<Map> maplist = new ArrayList<>();

        String sql1 = "SELECT \n" +
                "\tt1.*,t2.`v_name`,t3.`k_name`\n" +
                "FROM\n" +
                "\t(SELECT COUNT(basi_info_id) num,at_val_id,at_key_id FROM rela_chart GROUP BY at_val_id) t1,\n" +
                "\tattr_value t2,\n" +
                "\tattr_key t3\n" +
                "WHERE\n" +
                "\tt1.at_val_id = t2.`v_id` AND t2.`v_id_k` = t3.`k_id`\n" +
                "ORDER BY \n" +
                "\tt1.at_key_id";

        List<Temp> tempList = template.query(sql1, new BeanPropertyRowMapper<>(Temp.class));


        String sql2 = "SELECT\n" +
                "\tt2.`k_id`,t2.`k_name`\n" +
                "FROM\n" +
                "\trela_chart t1,\n" +
                "\tattr_key t2\n" +
                "WHERE\n" +
                "\tt1.`at_key_id` = t2.`k_id`\n" +
                "GROUP BY\n" +
                "\tt1.`at_key_id`";

        List<Key_Name_Id> keylist = template.query(sql2, new BeanPropertyRowMapper<>(Key_Name_Id.class));

        //遍历属性关键字集合
        Integer count = 0;
        for (int i = 0; i < keylist.size(); i++) {

            //计算该关键字下有多少个被涉及的属性值
            String sql3 = "SELECT \n" +
                    "\tCOUNT(temp.at_val_id)\n" +
                    "FROM\n" +
                    "\t(" + sql1 + ") temp\n" +
                    "\t\n" +
                    "WHERE\n" +
                    "\ttemp.at_key_id = ?";

            //根据value_count遍历templist，用List集合封装value和count
            List<Lable> list = new ArrayList<>();

            Integer value_count1 = template.queryForObject(sql3, Integer.class, keylist.get(i).getK_id());

            for (int i1 = 0; i1 < value_count1; i1++) {
                Lable lable = new Lable();
                lable.setValue(tempList.get(count + i1).getV_name());
                lable.setCounts(tempList.get(count + i1).getNum());
                list.add(lable);

            }
            count += value_count1;

            Map map = new HashMap();
            map.put(keylist.get(i).getK_name(), list);
            maplist.add(map);
        }
        return maplist;
    }

    public List<Map> query_link_basic(List<String> list1, int type) {

        //创建一个Lable对象，用于封装每个标签内容的数据总数和名称
        List<Map> maplist = new ArrayList<>();

        if(type == 0){
            String sql1 = "SELECT \n" +
                    "\tt1.*,t2.`v_name`,t3.`k_name`\n" +
                    "FROM\n" +
                    "\t(SELECT COUNT(basi_info_id) num,at_val_id,at_key_id FROM rela_chart GROUP BY at_val_id) t1,\n" +
                    "\tattr_value t2,\n" +
                    "\tattr_key t3\n" +
                    "WHERE\n" +
                    "\tt1.at_val_id = t2.`v_id` AND t2.`v_id_k` = t3.`k_id`\n"+
                    "ORDER BY \n" +
                    "\tt1.at_key_id";

            List<Temp> tempList = template.query(sql1, new BeanPropertyRowMapper<>(Temp.class));


            String sql2 = "SELECT\n" +
                    "\tt2.`k_id`,t2.`k_name`\n" +
                    "FROM\n" +
                    "\trela_chart t1,\n" +
                    "\tattr_key t2\n" +
                    "WHERE\n" +
                    "\tt1.`at_key_id` = t2.`k_id`\n" +
                    "GROUP BY\n" +
                    "\tt1.`at_key_id`";

            List<Key_Name_Id> keylist = template.query(sql2, new BeanPropertyRowMapper<>(Key_Name_Id.class));

            //遍历属性关键字集合
            Integer count = 0;
            for (int i = 0; i < keylist.size(); i++) {

                //计算该关键字下有多少个被涉及的属性值
                String sql3 = "SELECT \n" +
                        "\tCOUNT(temp.at_val_id)\n" +
                        "FROM\n" +
                        "\t("+sql1+") temp\n" +
                        "\t\n" +
                        "WHERE\n" +
                        "\ttemp.at_key_id = ?";

                //根据value_count遍历templist，用List集合封装value和count
                List<Lable> list = new ArrayList<>();

                Integer value_count1 = template.queryForObject(sql3, Integer.class,keylist.get(i).getK_id());

                for (int i1 = 0; i1 < value_count1; i1++) {
                    Lable lable = new Lable();
                    lable.setValue(tempList.get(count+i1).getV_name());
                    lable.setCounts(tempList.get(count+i1).getNum());
                    list.add(lable);

                }
                count += value_count1;

                Map map = new HashMap();
                map.put(keylist.get(i).getK_name(),list);
                maplist.add(map);
            }
        }else{
            //查询各属性值对应的关键字
            String sql = "SELECT v_id FROM attr_value WHERE v_name IN (";
            String str = "";
            for (int i = 0; i < list1.size(); i++) {

                if (i == list1.size() - 1) {
                    str = str + "'" + list1.get(i) + "')";
                } else {
                    str = str + "'" + list1.get(i) + "',";
                }
            }

            String sql1 = sql + str;

            //查询拥有这些属性值的数据id
            String sql2 = "SELECT \n" +
                    "\tbasi_info_id \n" +
                    "FROM \n" +
                    "\trela_chart \n" +
                    "WHERE \n" +
                    "\tat_val_id \n" +
                    "IN \n" +
                    "\t(" + sql1 + "))\n" +
                    "GROUP BY\n" +
                    "\tbasi_info_id";

            //根据上条sql语句得出的数据id，访问rela_chart表看其还涉及那些关键字及其属性值
            String sql3 = "SELECT * FROM \n" +
                    "\trela_chart \n" +
                    "WHERE \n" +
                    "\tbasi_info_id \n" +
                    "IN \n" +
                    "\t(SELECT \n" +
                    "\tbasi_info_id \n" +
                    "FROM \n" +
                    "\trela_chart \n" +
                    "WHERE \n" +
                    "\tbasi_info_id \n" +
                    "IN \n" +
                    "\t(" + sql2 + ")";

            //根据sql3所得内容，通过属性值字段分组和关键字字段排序，得到虚拟临时表
            String sql4 = "SELECT \n" +
                    "\tCOUNT(t1.basi_info_id) num,t1.basi_info_id,t1.at_val_id,t1.at_key_id,t2.v_name,t3.k_name\n" +
                    "FROM\n" +
                    "\t(" + sql3 + ") t1,\n" +
                    "\tattr_value t2,\n" +
                    "\tattr_key t3\n" +
                    "\n" +
                    "WHERE\n" +
                    "\tt1.at_val_id = t2.v_id\n" +
                    "AND\n" +
                    "\tt1.at_key_id = t3.k_id\n" +
                    "GROUP BY \n" +
                    "\tt1.at_val_id\n" +
                    "ORDER BY\n" +
                    "\tt1.at_key_id";

            List<Temp> tempList = template.query(sql4, new BeanPropertyRowMapper<>(Temp.class));

            //根据sql4得到的虚拟临时表，查询所得数据所有的关键字内容
            String sql5 = " SELECT \n" +
                    "\tt2.k_id,t2.k_name\n" +
                    " FROM\n" +
                    "\t(SELECT \n" +
                    "\t\t*\n" +
                    "\tFROM \n" +
                    "\t\trela_chart \n" +
                    "\tWHERE \n" +
                    "\t\tbasi_info_id \n" +
                    "\tIN \n" +
                    "\t\t(" + sql1 + ")\n" +
                    "\tGROUP BY\n" +
                    "\tat_key_id) t1,\n" +
                    "\tattr_key t2\n" +
                    " WHERE\n" +
                    "\tt1.at_key_id = t2.k_id\n" +
                    " GROUP BY\n" +
                    "\tt2.k_id\n";

            List<Key_Name_Id> keylist = template.query(sql5, new BeanPropertyRowMapper<>(Key_Name_Id.class));

            //遍历属性关键字集合
            Integer count = 0;
            for (int i = 0; i < keylist.size(); i++) {

                //计算该关键字下有多少个被涉及的属性值
                String sql6 = "SELECT \n" +
                        "\tCOUNT(temp.at_val_id)\n" +
                        "FROM\n" +
                        "\t(" + sql4 + ") temp\n" +
                        "\t\n" +
                        "WHERE\n" +
                        "\ttemp.at_key_id = ?";

                //根据value_count遍历templist，用List集合封装value和count
                List<Lable> list = new ArrayList<>();

                Integer value_count = template.queryForObject(sql6, Integer.class, keylist.get(i).getK_id());


                for (int i1 = 0; i1 < value_count; i1++) {
                    Lable lable = new Lable();
                    lable.setValue(tempList.get(count + i1).getV_name());
                    lable.setCounts(tempList.get(count + i1).getNum());
                    list.add(lable);
                }
                count += value_count;
                Map map = new HashMap();
                map.put(keylist.get(i).getK_name(), list);
                maplist.add(map);
            }
        }
        return maplist;
    }

    /**
     * 联动查询
     * @param value_list
     * @return
     */
    public List<Integer> query_link(List<String> value_list){

        //创建一个Lable对象，用于封装每个标签内容的数据总数和名称
        List<Map> maplist = new ArrayList<>();

        //查询满足属性值名字等于所传参数name的数据id,如'湖北省',得到basi_info_id为4，5，8，9的数据。
        String sql1 = "SELECT t1.basi_info_id FROM \n" +
                "\trela_chart t1,attr_value t2 WHERE t1.at_val_id = t2.`v_id` \n" +
                "AND \n" +
                "\tt2.`v_name` = '" + value_list.get(0) + "' \n" +
                "GROUP BY \n" +
                "\tt1.basi_info_id";

        //根据sql1所得结果，查询basi_info_id对应的rela_chart对应的所有字段内容
        String sql2 = "SELECT * FROM \n" +
                "\trela_chart \n" +
                "WHERE \n" +
                "\tbasi_info_id \n" +
                "IN \n" +
                "\t(" + sql1 + ")";

        //根据sql2所得内容，通过basi_info_id字段排序，得到虚拟临时表
        String sql3 = "SELECT \n" +
                "\tt1.basi_info_id,t1.at_val_id,t1.at_key_id,t2.v_name,t3.k_name\n" +
                "FROM\n" +
                "\t(" + sql2 + ") t1,\n" +
                "\tattr_value t2,\n" +
                "\tattr_key t3\n" +
                "\n" +
                "WHERE\n" +
                "\tt1.at_val_id = t2.v_id\n" +
                "AND\n" +
                "\tt1.at_key_id = t3.k_id\n" +
                "ORDER BY\n" +
                "\tt1.basi_info_id";

        List<TempLink> tempList = template.query(sql3, new BeanPropertyRowMapper<>(TempLink.class));

        //确定所有条件满足后的数据id

        //存放满足所有条件的数据id
        List<Integer> numlist = new ArrayList<>();
        for (int j = 1; j < value_list.size(); j++) {
            //结合temp1用来存放满足一个条件的中间结果
            List<TempLink> temp1 = new ArrayList<>();

            for (int j1 = 0; j1 < tempList.size(); j1++) {

                //注：字符串匹配最好是用equals方法
                if(tempList.get(j1).getV_name().equals(value_list.get(j))){
                    Integer basi_info_id = tempList.get(j1).getBasi_info_id();
                    System.out.println(basi_info_id);
                    numlist.add(basi_info_id);
                }
            }
            /*System.out.println("num===========");
            System.out.println(numlist);*/

            //通过上面的循环，得到满足list.get(0)~list.get(j)之间的所有条件的数据id，用temp1存放数据id对应的所有结果
            for (int j2 = 0; j2 < tempList.size(); j2++) {

                //tempList中每条数据的数据id和numlist中的值对比。相等，将该条数据放入临时集合temp1中
                for (int j3 = 0; j3 < numlist.size(); j3++) {
                    if(tempList.get(j2).getBasi_info_id() == numlist.get(j3)){
                        temp1.add(tempList.get(j2));
                    }
                }
            }

            tempList = temp1;
        }


        //取出满足所有条件后的数据id，结果用List集合封装
        List<Integer> idlist = new ArrayList<>();
        for (int j = 0; j < tempList.size(); j++) {
            if (idlist.size() == 0){
                idlist.add(tempList.get(j).getBasi_info_id());
            }else {
                if(tempList.get(j).getBasi_info_id() != idlist.get(idlist.size()-1)){
                    //因为tempList里面的数据是根据basi_info_id排序，为了避免重复添加相同数据id，需要判断
                    idlist.add(tempList.get(j).getBasi_info_id());
                }
            }
        }
        //将idlist集合中的所有id存放在全局变量imglist中，方便查询文件列表
        imglist = idlist;

        return idlist;
    }

    /**
     * 联动查询辅助方法
     * @param list
     * @return
     */
    public List<Map> query_by_id(List<Integer> list){

        List<Map> mapList = new ArrayList<>();
        //集合list里存放的是数据id
        //1.查询各rela_chart表中所有数据
        String sql1 = "SELECT * FROM rela_chart WHERE basi_info_id IN (";


        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                sql1 = sql1 + "'" + list.get(i) + "')";
            } else {
                sql1 = sql1 + "'" + list.get(i) + "',";
            }
        }

        //2. 根据sql1所得结果，查询basi_info_id对应的rela_chart对应的所有字段内容
        String sql2 = sql1;

        //3. 根据sql2所得内容，通过属性值字段分组和关键字字段排序，得到虚拟临时表
        String sql3 = "SELECT \n" +
                "\tCOUNT(t1.basi_info_id) num,t1.at_val_id,t1.at_key_id,t2.v_name,t3.k_name\n" +
                "FROM\n" +
                "\t(" + sql2 + ") t1,\n" +
                "\tattr_value t2,\n" +
                "\tattr_key t3\n" +
                "\n" +
                "WHERE\n" +
                "\tt1.at_val_id = t2.v_id\n" +
                "AND\n" +
                "\tt1.at_key_id = t3.k_id\n" +
                "GROUP BY \n" +
                "\tt1.at_val_id\n" +
                "ORDER BY\n" +
                "\tt1.at_key_id";


        List<Temp> tempList = template.query(sql3, new BeanPropertyRowMapper<>(Temp.class));

        //4. 根据sql2得到的虚拟临时表，查询所得数据所有的关键字内容
        String sql4 = "SELECT\n" +
                "\tt2.`k_id`,t2.`k_name`\n" +
                "FROM\n" +
                "\t(" + sql2 + ") t1,\n" +
                "\tattr_key t2\n" +
                "WHERE\n" +
                "\tt1.`at_key_id` = t2.`k_id`\n" +
                "GROUP BY\n" +
                "\tt1.`at_key_id`";

        /*System.out.println("sql4============");
        System.out.println(sql4);*/

        List<Key_Name_Id> keylist = template.query(sql4, new BeanPropertyRowMapper<>(Key_Name_Id.class));

        //5. 遍历属性关键字集合
        Integer count = 0;
        for (int i = 0; i < keylist.size(); i++) {

            //5.1 计算该关键字下有多少个被涉及的属性值
            String sql5 = "SELECT \n" +
                    "\tCOUNT(temp.at_val_id)\n" +
                    "FROM\n" +
                    "\t(" + sql3 + ") temp\n" +
                    "\t\n" +
                    "WHERE\n" +
                    "\ttemp.at_key_id = ?";

            //根据value_count遍历templist，用List集合封装value和count
            List<Lable> lableList = new ArrayList<>();

            Integer value_count = template.queryForObject(sql5, Integer.class, keylist.get(i).getK_id());

            for (int i1 = 0; i1 < value_count; i1++) {
                Lable lable = new Lable();
                lable.setValue(tempList.get(count + i1).getV_name());
                lable.setCounts(tempList.get(count + i1).getNum());
                lableList.add(lable);

            }
            count += value_count;

            Map map = new HashMap();
            map.put(keylist.get(i).getK_name(), lableList);
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 统计每个分类体系的数据总数。点击分类体系，标签搜索页面发生变动，但没有实现标签页面对分类体系的反向联动
     * @param cate_id
     * @param type
     * @return
     */
    public List<Integer> click_category(int cate_id,int type) {
        //创建一个Lable对象，用于封装每个标签内容的数据总数和名称
        List<Map> maplist = new ArrayList<>();

        //存放完成数据分类体系查询的id集合
        List<Integer> cate_idlist = new ArrayList<>();
        if (type == 2){
            //type = 2. 说明二级分类体系id为参数
            //1. 定义sql语句
            String sql = "SELECT * FROM basic_info WHERE da_type = ?";

            //            //2. 执行sql语句，得到满足条件的内容
            List<Basic_info> infolist = template.query(sql, new BeanPropertyRowMapper<>(Basic_info.class), cate_id);

            //3. 用集合存下上步得到的所有数据id

            //4. 封装二级分类体系id的数量和名称
            Lable lable = new Lable();
            lable.setCounts(infolist.size());

            String value_sql = "SELECT t1_name FROM da_type1 WHERE id = ?";
            String s = template.queryForObject(value_sql, String.class, cate_id);
            lable.setValue(s);

            Map da_typesec =new HashMap();
            da_typesec.put("dataType2",lable);
            maplist.add(da_typesec);
            //5. 用集合存下上步得到的所有数据id
            List<Integer> idlist = new ArrayList<>();
            for (int i = 0; i < infolist.size(); i++) {
                idlist.add(infolist.get(i).getId());
            }

            System.out.println("idlist===========");
            System.out.println(idlist);
            //6. 调用方法传入参数idlist,
            //maplist = query_by_id(idlist);//该方法得到的结果是满足idlist集合中的数据id所对应

            cate_idlist = idlist;

        }else{
            //type =1.说明一级分类体系id为参数
            //1. 定义sql语句
            String sql1 = "SELECT* FROM da_type2 WHERE t1_id = ?";

            //2. 执行sql1语句，得到满足条件的所有二级分类体系id
            List<Datatype_Sec> datatype_secs = template.query(sql1, new BeanPropertyRowMapper<>(Datatype_Sec.class), cate_id);

            //3. 定义sql语句，将sql1查询的结果作为临时表，查询da_type等于临时表中id的所有数据内容
            String sql2 = "SELECT * FROM ("+sql1+") t1,basic_info t2 WHERE t2.`da_type` = t1.id";

            //4. 执行sql2语句，得到满足条件的内容
            List<Basic_info> infolist = template.query(sql2, new BeanPropertyRowMapper<>(Basic_info.class), cate_id);

            //4.1 封装一级分类体系id的数量和名称
            Lable lable = new Lable();
            lable.setCounts(infolist.size());

            String value_sql = "SELECT t1_name FROM da_type1 WHERE id = ?";
            String s = template.queryForObject(value_sql, String.class, cate_id);
            lable.setValue(s);

            Map da_typefir =new HashMap();
            da_typefir.put("dataType1",lable);
            maplist.add(da_typefir);

            //5. 用集合存下上步得到的所有数据id
            List<Integer> idlist = new ArrayList<>();
            for (int i = 0; i < infolist.size(); i++) {
                idlist.add(infolist.get(i).getId());
            }

            System.out.println("idlist==========");
            System.out.println(idlist);
            //6. 调用方法传入参数idlist
            //maplist = query_by_id(idlist);
            cate_idlist = idlist;

        }

        return cate_idlist;
    }

    //需要设置一个全局变量imglist，用来接收联动查询最终得到的数据id集合。
    //但需要注意的是，这个全局变量可在下面的方法使用

    /**
     * 返回静态页面数据分类体系的功能模块结果，而且能实现标签页面对分类体系的反向联动
     * @return
     */
    public List<Map> query_lab_to_cate(List<Integer> idlist){

        //创建集合，封装最终结果
        List<Map> cate_List = new ArrayList<>();

        //设置一个List<Integer>集合，接收标签查询返回的数据id
        //List<Integer> idlist = imglist;

        //1. 查询da_type1(一级分类体系表)，封装结果
        //1.1 创建sql语句，查出所有的一级分类体系相关内容，并用list集合封装
        String sql1 = "SELECT * FROM da_type1";

        //1.2 封装结果
        List<Datatype_Fir> datype_fir = template.query(sql1,new BeanPropertyRowMapper<>(Datatype_Fir.class));

        //2. 查询da_type2(二级分类体系)，封装结果
        //2.1 创建sql语句，查出所有的一级分类体系相关内容，并用list集合封装
        String sql2 = "SELECT * FROM da_type2";

        //2.2 封装结果
        List<Datatype_Sec> datype_sec = template.query(sql2,new BeanPropertyRowMapper<>(Datatype_Sec.class));

        //3.先将basic_info表按二级分类体系id排序，再封装所有结果
        //根据idlist是否为空，用不同的查询条件约束basic_info表
        List<Basic_info> infoList = new ArrayList<>();
        if(idlist == null){
            //说明没有进行标签查询，此时应该对所有数据根据分类体系进行分类，并记下数量
            //3.1 创建排序sql语句，查出所有排序后的数据基本信息，并用list集合封装
            String sql3 = "SELECT * FROM basic_info ORDER BY da_type";

            //3.2 封装结果
            infoList = template.query(sql3, new BeanPropertyRowMapper<>(Basic_info.class));
        }else {
            //说明有进行标签查询，此时应该对idlist的数据根据分类体系进行分类，并记下数量
            //3.1 创建排序sql语句，查出所有排序后的数据基本信息，并用list集合封装
            String s1 = "SELECT * FROM basic_info WHERE id IN (";
            String s2 = "";
            for (int i = 0; i < idlist.size(); i++) {
                if (i == idlist.size()-1){
                    s2 += idlist.get(i)+") ";
                }else {
                    s2 += idlist.get(i)+",";
                }
            }

            String s3 = "ORDER BY da_type DESC";

            String sql3 = s1 + s2 + s3;

            //3.2 封装结果
            infoList = template.query(sql3, new BeanPropertyRowMapper<>(Basic_info.class));
        }

        //4. 遍历数据基本信息，将其按二级分类体系和一级分类体系区分，并计算每个分类体系的数据总数
        //4.1 设置List<Map>集合，存放二级分类体系名字和数据总数
        List<Lable> sec_cate = new ArrayList<>();
        for (int i = 0; i < datype_sec.size(); i++) {

            Lable lable = new Lable();
            //（1）设置计数变量，统计每个二级分类体系的值
            int count = 0;
            //(2) 每条数据循环遍历数据集合
            for (int j = 0; j < infoList.size(); j++) {
                if(infoList.get(j).getDa_type() == datype_sec.get(i).getId()){
                    count ++;
                }
            }
            //(3) 设置Lable对象，存下二级分类体系编码和数据总数
            lable.setValue(datype_sec.get(i).getT2_code());
            lable.setCounts(count);
            sec_cate.add(lable);
        }
        Map secmap = new HashMap();
        secmap.put("secondCategory",sec_cate);
        cate_List.add(secmap);

        //4.2 设置List<Map>集合，存放一级分类体系名字和数据总数
        List<Lable> fir_cate = new ArrayList<>();
        for (int i = 0; i < datype_fir.size(); i++) {
            Lable lable = new Lable();
            String s = datype_fir.get(i).getT1_code();
            int count = 0;
            for (int j = 0; j < sec_cate.size(); j++) {
                /*这种判断条件不准确，例如0201也会被计入01中
                if(sec_cate.get(i).getValue().contains(s)){
                    count += sec_cate.get(i).getCount();
                }*/
                //以0102为例，要先剪掉02，可使用String类中的substring方法
                String sub_code = sec_cate.get(j).getValue().substring(0,2);
                /*System.out.println(sub_code);
                System.out.println(sec_cate.get(j).getCount());
                System.out.println("count="+count);*/
                if(sub_code.equals(s)){
                    count += sec_cate.get(j).getCounts();
                }

            }
            lable.setValue(datype_fir.get(i).getT1_code());
            lable.setCounts(count);
            fir_cate.add(lable);
        }
        Map firsec = new HashMap();
        firsec.put("firstCategory",fir_cate);
        cate_List.add(firsec);

        return cate_List;
    }

    /**
     * 文件列表不传参时的静态页面所需数据
     * @return
     */
    public List<Map> doclist_sta(boolean updated,int currentpage, int pagesize){

        //创建一个集合，封装最终结果
        List<Map> list = new ArrayList<>();
        String sql = "SELECT count(*) FROM basic_info";
        Integer allcounts = template.queryForObject(sql, Integer.class);

        //1.判断update的值，来确定升序排列还是降序排列
        if(updated == true){
            //按时间降序排列，也就是日期从大到小
            //2.创建sql语句，获取基本信息表的数据信息

            String sql1 = "SELECT * FROM basic_info ORDER BY up_time DESC";

            //3. 执行sql语句并封装
            List<Basic_info> InfoList = template.query(sql1, new BeanPropertyRowMapper<>(Basic_info.class));
            if (currentpage==0 && pagesize==0){
                //计算结果中的数据总条数，将其封装在map集合
                Map num_map = new HashMap();
                num_map.put("allCounts",allcounts);
                list.add(num_map);
                //将关键字features和属性值放在map中
                Map reslut_map = new HashMap();
                reslut_map.put("features",InfoList);
                list.add(reslut_map);
            }else {
                String sql2 = "SELECT * FROM basic_info ORDER BY up_time DESC LIMIT ?,?";

                //3. 执行sql语句并封装
                int index = (currentpage - 1) * pagesize;
                List<Basic_info> basicInfoList = template.query(sql2, new BeanPropertyRowMapper<>(Basic_info.class),index,pagesize);

                //计算结果中的数据总条数，将其封装在map集合
                Map num_map = new HashMap();
                num_map.put("allCounts",allcounts);
                list.add(num_map);
                //将关键字features和属性值放在map中
                Map reslut_map = new HashMap();
                reslut_map.put("features",basicInfoList);
                list.add(reslut_map);
            }

        }else{
            //按时间升序排列，也就是日期从小到大
            //2.创建sql语句，获取基本信息表的数据信息


            String sql1 = "SELECT * FROM basic_info ORDER BY up_time ASC";

            //3. 执行sql语句并封装

            List<Basic_info> InfoList = template.query(sql1, new BeanPropertyRowMapper<>(Basic_info.class));
            if (currentpage==0 && pagesize==0){
                //计算结果中的数据总条数，将其封装在map集合
                Map num_map = new HashMap();
                num_map.put("allCounts",InfoList.size());
                list.add(num_map);
                //将关键字features和属性值放在map中
                Map reslut_map = new HashMap();
                reslut_map.put("features",InfoList);
                list.add(reslut_map);
            }else {
                String sql2 = "SELECT * FROM basic_info ORDER BY up_time ASC LIMIT ?,?";

                //3. 执行sql语句并封装
                int index = (currentpage - 1) * pagesize;
                List<Basic_info> basicInfoList = template.query(sql2, new BeanPropertyRowMapper<>(Basic_info.class),index ,pagesize);

                //计算结果中的数据总条数，将其封装在map集合
                Map num_map = new HashMap();
                num_map.put("allCounts",InfoList.size());
                list.add(num_map);
                //将关键字features和属性值放在map中
                Map reslut_map = new HashMap();
                reslut_map.put("features",basicInfoList);
                list.add(reslut_map);
            }
        }
        return list;
    }


    //需要设置一个全局变量imglist，用来接收联动查询最终得到的数据id集合。
    //但需要注意的是，这个全局变量可在下面的方法使用

    /**
     * 联动查询得到的文件列表
     * @param updated
     * @param pagesize
     * @param currentpage
     * @return
     */
    public List<Map> doclist_dyn(boolean updated,int currentpage,int pagesize,List<Integer> idlist) throws JsonProcessingException {

        //创建一个集合，封装最终结果
        List<Map> list = new ArrayList<>();

        if(updated == true){
            //联动查询得到的文件列表根据时间采用降序排列
            //拼接sql语句，最终得到根据参数info_id集合查询涉及其中id的数据基本信息
            String s1 = "SELECT * FROM basic_info WHERE id IN (";
            String s2 = "";
            for (int i = 0; i < idlist.size(); i++) {
                if (i == idlist.size()-1){
                    s2 += idlist.get(i)+") ";
                }else {
                    s2 += idlist.get(i)+",";
                }
            }

            String s3 = "ORDER BY up_time DESC";
            String sql = s1 + s2 + s3;

            //执行sql语句，并封装结果
            List<Basic_info> basicinfolist = template.query(sql, new BeanPropertyRowMapper<>(Basic_info.class));
            if(currentpage == 0 && pagesize == 0){
                //计算结果中的数据总条数，将其封装在map集合
                Map num_map = new HashMap();
                num_map.put("allCounts",basicinfolist.size());
                list.add(num_map);
                //将关键字features和属性值放在map中
                Map reslut_map = new HashMap();
                reslut_map.put("features",basicinfolist);
                list.add(reslut_map);
            }else {
                String s4 = "ORDER BY up_time DESC LIMIT ?,?";

                String sql1 = s1 + s2 + s4;

                //执行sql语句，并封装结果
                int index = (currentpage - 1) * pagesize;
                List<Basic_info> infolist = template.query(sql1, new BeanPropertyRowMapper<>(Basic_info.class),index,pagesize);

                //计算结果中的数据总条数，将其封装在map集合
                Map num_map = new HashMap();
                num_map.put("allCounts",basicinfolist.size());
                list.add(num_map);
                //将关键字features和属性值放在map中
                Map reslut_map = new HashMap();
                reslut_map.put("features",infolist);
                list.add(reslut_map);
            }

        }else {
            System.out.println("imglist===============");
            System.out.println(imglist);
            //联动查询得到的文件列表根据时间采用降序排列
            //拼接sql语句，最终得到根据参数info_id集合查询涉及其中id的数据基本信息
            String s1 = "SELECT * FROM basic_info WHERE id IN (";
            String s2 = "";

            for (int i = 0; i < idlist.size(); i++) {
                if (i == idlist.size()-1){
                    s2 += idlist.get(i)+") ";
                }else {
                    s2 += idlist.get(i)+",";
                }
            }

            String s3 = "ORDER BY up_time ASC";
            String sql = s1 + s2 + s3;

            //执行sql语句，并封装结果
            List<Basic_info> basicinfolist = template.query(sql, new BeanPropertyRowMapper<>(Basic_info.class));
            if (currentpage==0 && pagesize==0){
                //计算结果中的数据总条数，将其封装在map集合
                Map num_map = new HashMap();
                num_map.put("allCounts",basicinfolist.size());
                list.add(num_map);
                //将关键字features和属性值放在map中
                Map reslut_map = new HashMap();
                reslut_map.put("features",basicinfolist);
                list.add(reslut_map);
            }else {
                String s4 = "ORDER BY up_time ASC LIMIT ?,?";

                String sql1 = s1 + s2 + s4;

                System.out.println("sql=================");
                System.out.println(sql);
                //执行sql语句，并封装结果
                int index = (currentpage - 1) * pagesize;
                List<Basic_info> infolist = template.query(sql1, new BeanPropertyRowMapper<>(Basic_info.class), index, pagesize);

                //计算结果中的数据总条数，将其封装在map集合
                Map num_map = new HashMap();
                num_map.put("allCounts",basicinfolist.size());
                list.add(num_map);
                //将关键字features和属性值放在map中
                Map reslut_map = new HashMap();
                reslut_map.put("features",infolist);
                list.add(reslut_map);
            }

        }
        return list;
    }

    /**
     * 一次查询，针对原始数据。所有分页都是在文件列表处显示，该方法不提供分页
     * @param name
     * @param uper_place
     * @return
     */
    public List<Integer> search_fir(String name,String uper_place){

        //查询原始数据。用户通过name和uper_place两个字段进行模糊查询，得出所有满足条件的数据并分页
        //1. 创建sql语句

        String sql1 = "SELECT * FROM basic_info WHERE NAME LIKE '%"+name+"%' OR uper_place LIKE '%"+uper_place+"%' ORDER BY id";

        //2. 执行sql语句

        List<Basic_info> infoList1 = template.query(sql1, new BeanPropertyRowMapper<>(Basic_info.class));

        //3. 取出所有数据id并封装，将获得的数据id用于查询分类体系数据总数和标签的数据总数
        List<Integer> idlist = new ArrayList<>();
        for (Basic_info basic_info : infoList1) {
            idlist.add(basic_info.getId());
        }

        return idlist;
    }

    /**
     *二次查询，传入之前获取到的文件列表。最重查询的是文件列表，所以调用文件列表查询下的接口
     * @return
     */
    public List<Map> search_sec(String name,String uper_place,int time_l,int time_r,List<Basic_info> infos){

        //对满足之前的查询结果在进行筛选，即在infos集合中筛选

        //1. 新建一个List集合，用于封装结果
        List<Map> maplist = new ArrayList<>();
        List<Basic_info> infoList = new ArrayList<>();
        //2. 依次比较infos集合中的元素
        for (Basic_info info : infos) {
            //满足三个条件，根据name和uper_place进行模糊查询
            if(name != null && uper_place != null){
                if (info.getName().contains(name) || info.getUper_place().contains(uper_place)){
                    //2.1 判断该数据是否在年份范围类
                    Calendar calendar= Calendar.getInstance();
                    calendar.setTime(info.getUp_time());
                    int year = calendar.get(Calendar.YEAR);
                    //如果左边年份和右边年份未输入，参数设为0，则不进行判断；反之，要判断
                    if(time_l != 0 && time_r != 0){
                        if (year >= time_l && year<=time_r) infoList.add(info);
                    }else {
                        if (time_l == 0 && time_r != 0){
                            if (year <= time_r) infoList.add(info);
                        }else {
                            if (time_l != 0 && time_r == 0){
                                if (year >= time_l) infoList.add(info);
                            }else {
                                infoList.add(info);
                            }
                        }
                    }
                }
            }else {
                //2.1 判断该数据是否在年份范围类
                Calendar calendar= Calendar.getInstance();
                calendar.setTime(info.getUp_time());
                int year = calendar.get(Calendar.YEAR);
                //如果左边年份和右边年份未输入，参数设为0，则不进行判断；反之，要判断
                if(time_l != 0 && time_r != 0){
                    if (year >= time_l && year<=time_r) infoList.add(info);
                }else {
                    if (time_l == 0 && time_r != 0){
                        if (year <= time_r) infoList.add(info);
                    }else {
                        if (time_l != 0 && time_r == 0){
                            if (year >= time_l) infoList.add(info);
                        }else {
                            infoList.add(info);
                        }
                    }
                }
            }
        }
        Map map1 = new HashMap();
        map1.put("allCounts",infoList.size());
        maplist.add(map1);
        Map map2 = new HashMap();
        map2.put("features",infoList);
        maplist.add(map2);

        return maplist;
    }

    /**
     * 两次查询取交集，得到同时满足分类体系查询和标签查询的数据id
     * @param list1
     * @param list2
     * @return
     */
    public List<Integer> id_union(List<Integer> list1,List<Integer> list2){

        List<Integer> list = new ArrayList<>();
        //对list2实现升序排列
        //排序后不用遍历集合所有元素进行比较
        for (int i = 0; i < list1.size(); i++) {
            for (int j = 0; j < list2.size(); j++){
                if(list1.get(i) == list2.get(j)){
                    list.add(list1.get(i));
                }
            }
        }

        return list;
    }
}
