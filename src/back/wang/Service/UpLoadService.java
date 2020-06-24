package back.wang.Service;

import back.wang.Dao.UpLoadInsert;
import back.wang.Domain.BasicInfoAll;
import front.basic_page.Domain.Attr_value;
import front.basic_page.Domain.RelateKeyNData;

/**
 * 描述:
 * 专题数据上传服务
 *
 * @author black-leaves
 * @createTime 2020-06-24  13:26
 */

public class UpLoadService {

    /**
     * 插入基础数据服务
     *
     * @param basic_data BasicInfoAll对象
     * @return 插入成功返回id ， 插入失败返回0
     */
    public int InsertBasic(BasicInfoAll basic_data) {
        UpLoadInsert insert = new UpLoadInsert();
        int id = 0;
        if (basic_data.getUp_id() != 0 && basic_data.getDa_type() != 0) {
            id = insert.basicDataInsert(basic_data);
        }
        return id;
    }

    public boolean InsertRelate(Attr_value attr_value, int basic_id) {
        UpLoadInsert insert = new UpLoadInsert();
        if (attr_value.getV_id() == -1) { //新增标签，需要先插入attr_value表然后再插入关系表
            int attr_id = insert.attrValueInsert(attr_value);
            if (attr_id == 0) return false;
            attr_value.setV_id(attr_id);
        }
        RelateKeyNData relateKeyNData = new RelateKeyNData();
        relateKeyNData.setBasi_info_id(basic_id);
        relateKeyNData.setAt_key_id(attr_value.getV_id_k());
        relateKeyNData.setAt_val_id(attr_value.getV_id());

        return insert.relateInsert(relateKeyNData);
    }
}
