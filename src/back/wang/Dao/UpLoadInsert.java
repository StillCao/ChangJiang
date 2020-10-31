package back.wang.Dao;

import back.wang.Domain.BasicInfoAll;
import front.basic_page.Domain.Attr_value;
import front.basic_page.Domain.RelateKeyNData;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import utils.JDBCUtils;

/**
 * 描述:
 * 专题数据上传 数据插入
 *
 * @author black-leaves
 * @createTime 2020-06-24  13:28
 */

public class UpLoadInsert {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    NamedParameterJdbcTemplate npjTemplate = new NamedParameterJdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 插入一条基础数据
     *
     * @param data 基础数据对象
     * @return 插入后的主键ID
     */
    public int basicDataInsert(BasicInfoAll data) {
        //id,name,sploc,docname,up_time,point1_lat,point1_lon,point2_lat,point2_lon,topic_w1,topic_w2,topic_w3,topic_cfi,da_summ,da_size,up_id,da_type
        String sql = "Insert into basic_info(id,name,sploc,docname,up_time,point1_lat,point1_lon,point2_lat,point2_lon,topic_w1,topic_w2,topic_w3,topic_cfi,da_summ,da_size,da_url,up_id,da_type,image,uper_name,uper_place,file_url,sample_url,datm_range,da_source,da_method,da_projection,da_quality,da_refer,subj_cfi) Values (null,:name,:sploc,:docname,:up_time,:point1_lat,:point1_lon,:point2_lat,:point2_lon,:topic_w1,:topic_w2,:topic_w3,:topic_cfi,:da_summ,:da_size,:da_url,:up_id,:da_type,:image,:uper_name,:uper_place,:file_url,:sample_url,:datm_range,:da_source,:da_method,:da_projection,:da_quality,:da_refer,:subj_cfi)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        npjTemplate.update(sql, new BeanPropertySqlParameterSource(data), keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     * 插入一条数据到数据和主题词关联表
     *
     * @param relateKeyNData 数据和主题词关联表对象
     * @return 是否插入成功
     */
    public boolean relateInsert(RelateKeyNData relateKeyNData) {
        String sql = "Insert into rela_chart (rela_id,basi_info_id,at_key_id,at_val_id) values (null,?,?,?)";
        return template.update(sql, relateKeyNData.getBasi_info_id(), relateKeyNData.getAt_key_id(), relateKeyNData.getAt_val_id()) > 0;
    }

    /**
     * 插入一条数据到 attr_value 表
     * @param attr_value attr_value 对象
     * @return 插入后的主键ID
     */
    public int attrValueInsert(Attr_value attr_value) {
        String sql = "Insert into attr_value (v_id,v_name,v_id_k) values (null,:v_name,:v_id_k)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        npjTemplate.update(sql, new BeanPropertySqlParameterSource(attr_value), keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     * 查询 attr_value 表里是否存在 v_id
     * @param v_id 标签id
     * @return 是否存在
     */
    public boolean attrValueQuery(int v_id){
        String sql = "select count(*) from attr_value where v_id = ?";
        int count = template.queryForObject(sql,Integer.class,v_id);
        return count >= 1;
    }

    /**
     * 查询 attr_value 表里是否存在 v_name
     * @param v_name 标签名称
     * @return 是否存在
     */
    public boolean attrValueQueryName(String v_name){
        String sql = "select count(*) from attr_value where v_name = ?";
        int count = template.queryForObject(sql,Integer.class,v_name);
        return count >= 1;
    }

    /**
     * 删除 rela_chart 关联表中 基础数据关联的所有记录
     * @param basic_info_id 基础数据id
     * @return 是否成功删除
     */
    public boolean deleteRelaChartByBasicInfoId(int basic_info_id){
        String sql = "delete from rela_chart where basi_info_id = ?";
        return template.update(sql,basic_info_id) > 0;
    }

}
