package back.wang.Service;

import back.wang.Dao.DownAimInsert;
import back.wang.Domain.Downaim;
import back.wang.Domain.Order_confirm;
import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 描述:
 * 下载目的服务
 *
 * @author black-leaves
 * @createTime 2020-06-29  15:27
 */

public class DownAimService {

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

    /**
     * 向DownAim表插入一条数据
     * @param downaim Downaim对象
     * @return 插入成功后的id
     */
    public int InsertDownAim(Downaim downaim){
        DownAimInsert insert = new DownAimInsert();
        return insert.InsertDownAim(downaim);
    }

    /**
     * 更新 order_confirm 表 ，先根据userId和dataId查询到要修改的记录，然后再进行修改
     * @param userId 用户id
     * @param dataId 数据id
     * @param downaim_id 下载目的id
     * @return 是否修改成功
     */
    public boolean UpDateOrderConfirm(int userId, int dataId, int downaim_id){
        Order_confirm order_confirm = new Order_confirm();
        order_confirm.setUserId(userId);
        order_confirm.setDataId(dataId);
        order_confirm.setDown_aim(downaim_id);
        DownAimInsert insert = new DownAimInsert();
        int id  = insert.QueryOrderConfirmByIds(order_confirm.getUserId(),order_confirm.getDataId());
        if (id >0){
            order_confirm.setId(id);
            return insert.UpDateOrderConfirm(order_confirm);
        }
        return false;


    }
}
