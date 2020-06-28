package back.wang.Service;

import back.wang.Dao.UpLoadInsert;
import back.wang.Domain.BasicInfoAll;
import front.basic_page.Domain.Attr_value;
import front.basic_page.Domain.RelateKeyNData;
import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

    /**
     * 插入关系表
     *
     * @param attr_value Attr_value对象
     * @param basic_id   关联的数据Id
     * @return 是否插入成功
     */
    public boolean InsertRelate(Attr_value attr_value, int basic_id) {
        UpLoadInsert insert = new UpLoadInsert();

        int v_id = attr_value.getV_id();

        if (insert.attrValueQuery(v_id)) { //先判断v_id 是否在 attr_value 表中 ，若为新增标签，需要先插入attr_value表然后再插入关系表
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

    /** 保存文件流到指定的目录
     * @param item FileItem 对象
     * @param projDirPath 指定的目录
     * @return 是否保存成功
     */
    public boolean SaveFile(FileItem item, String projDirPath) throws IOException {
        File file = new File(projDirPath, item.getName());
        InputStream is = item.getInputStream();
        //创建文件输出流
        FileOutputStream os = new FileOutputStream(file);
        //将输入流中的数据写出到输出流中
        int len = -1;
        byte[] buf = new byte[1024];
        while ((len = is.read(buf)) != -1) {
            os.write(buf, 0, len);
        }
        //关闭流
        os.close();
        is.close();
        //删除临时文件
        item.delete();
        return file.exists();
    }
}
