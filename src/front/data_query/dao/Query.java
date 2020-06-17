package front.data_query.dao;

import front.data_query.domain.Key_Name_Id;
import front.data_query.domain.Lable;
import front.data_query.domain.Temp;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Query {

    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     *静态查询和联动查询
     * @param name
     * @return
     */
    public List<Map> queryLable(String name){

        //创建一个Lable对象，用于封装每个标签内容的数据总数和名称
        List<Map> maplist = new ArrayList<>();

        if(name == null){
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
            //查询满足属性值名字等于所传参数name的数据id,如'湖北省',得到basi_info_id为4，5，8，9的数据。
            String sql1 = "SELECT t1.basi_info_id FROM \n" +
                    "\trela_chart t1,attr_value t2 WHERE t1.at_val_id = t2.`v_id` \n" +
                    "AND \n" +
                    "\tt2.`v_name` = '"+name+"' \n" +
                    "GROUP BY \n" +
                    "\tt1.basi_info_id";

            //根据sql1所得结果，查询basi_info_id对应的rela_chart对应的所有字段内容
            String sql2 = "SELECT * FROM \n" +
                    "\trela_chart \n" +
                    "WHERE \n" +
                    "\tbasi_info_id \n" +
                    "IN \n" +
                    "\t("+sql1+")";

            //根据sql2所得内容，通过属性值字段分组和关键字字段排序，得到虚拟临时表
            String sql3 = "SELECT \n" +
                    "\tCOUNT(t1.basi_info_id) num,t1.at_val_id,t1.at_key_id,t2.v_name,t3.k_name\n" +
                    "FROM\n" +
                    "\t("+sql2+") t1,\n" +
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

            //根据sql2得到的虚拟临时表，查询所得数据所有的关键字内容
            String sql4 = "SELECT\n" +
                    "\tt2.`k_id`,t2.`k_name`\n" +
                    "FROM\n" +
                    "\t("+sql2+") t1,\n" +
                    "\tattr_key t2\n" +
                    "WHERE\n" +
                    "\tt1.`at_key_id` = t2.`k_id`\n" +
                    "GROUP BY\n" +
                    "\tt1.`at_key_id`";

            List<Key_Name_Id> keylist = template.query(sql4, new BeanPropertyRowMapper<>(Key_Name_Id.class));

            //遍历属性关键字集合
            Integer count = 0;
            for (int i = 0; i < keylist.size(); i++) {

                //计算该关键字下有多少个被涉及的属性值
                String sql5 = "SELECT \n" +
                        "\tCOUNT(temp.at_val_id)\n" +
                        "FROM\n" +
                        "\t("+sql3+") temp\n" +
                        "\t\n" +
                        "WHERE\n" +
                        "\ttemp.at_key_id = ?";

                //根据value_count遍历templist，用List集合封装value和count
                List<Lable> list = new ArrayList<>();

                Integer value_count = template.queryForObject(sql5, Integer.class,keylist.get(i).getK_id());

                for (int i1 = 0; i1 < value_count; i1++) {
                    Lable lable = new Lable();
                    lable.setValue(tempList.get(count+i1).getV_name());
                    lable.setCounts(tempList.get(count+i1).getNum());
                    list.add(lable);

                }
                count += value_count;

                Map map = new HashMap();
                map.put(keylist.get(i).getK_name(),list);
                maplist.add(map);
            }
        }

        return maplist;
    }
}
