package back.wang.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import back.wang.Dao.DownAimInsert;
import back.wang.Domain.BasicInfoAll;
import back.wang.Domain.Downaim;
import back.wang.Domain.Order_confirm;

import org.apache.commons.fileupload.FileItem;

import back.wang.Domain.Page;
import front.user_io.domain.User;
import utils.KeyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 描述:
 * 下载目的服务
 *
 * @author black-leaves
 * @createTime 2020-06-29  15:27
 */

public class DownAimService {

    /**
     * 保存文件流到指定的目录
     *
     * @param item        FileItem 对象
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
     *
     * @param downaim Downaim对象
     * @return 插入成功后的id
     */
    public int InsertDownAim(Downaim downaim) {
        DownAimInsert insert = new DownAimInsert();
        return insert.InsertDownAim(downaim);
    }

    /**
     * 更新 order_confirm 表 ，先根据userId和dataId查询到要修改的记录，然后再进行修改
     *
     * @param userId     用户id
     * @param dataId     数据id
     * @param downaim_id 下载目的id
     * @return 是否修改成功
     */
    public boolean UpDateOrderConfirm(int userId, int dataId, int downaim_id) {
        Order_confirm order_confirm = new Order_confirm();
        order_confirm.setUserId(userId);
        order_confirm.setDataId(dataId);
        order_confirm.setDown_aim(downaim_id);
        order_confirm.setOrderCode(KeyUtils.generateUniqueKey());
        DownAimInsert insert = new DownAimInsert();
        int id = insert.QueryOrderConfirmByIds(order_confirm.getUserId(), order_confirm.getDataId());
        if (id > 0) {
            order_confirm.setId(id);
            return insert.UpDateOrderConfirm(order_confirm);
        }
        return false;


    }

    /**
     * 所有分页查询
     *
     * @param currentPage  当前页面
     * @param currentCount 每页条数
     * @param key          需要模糊查询的字段名
     * @param value        需要模糊查询的字段值
     * @param status       订单状态
     * @return Page<>转为的JsonString
     */
    public String queryOrderByPageLike(int currentPage, int currentCount, String key, String value, int status) {
        DownAimInsert insert = new DownAimInsert();
        List<Order_confirm> orders = null;
        int totalCount;
        int totalPage;
        int startPosition;
        if (key.equals("orderStatus")) {
            totalCount = insert.QueryOrderCountByStatus(status);
            totalPage = totalCount % currentCount == 0 ? totalCount / currentCount : totalCount / currentCount + 1;
            startPosition = (currentPage - 1) * currentCount;
            orders = insert.queryOrderByStatusNPage(status, startPosition, currentCount);
        } else {
            totalCount = insert.QueryOrderCountByKeyNValuesLike(key, value, status);
            totalPage = totalCount % currentCount == 0 ? totalCount / currentCount : totalCount / currentCount + 1;
            startPosition = (currentPage - 1) * currentCount;
            orders = insert.QueryOrderByKeyNValueNPage(status, key, value, startPosition, currentCount);
        }

        JSONArray array = new JSONArray();
        orders.forEach(order -> {
            //object初始化
            JSONObject object = new JSONObject();
            object.put("status", status);
            object.put("totalPage", totalPage);
            object.put("currentPage", currentPage);
            object.put("currentCount", currentCount);
            object.put("totalCount", totalCount);

            int orderId = order.getId();
            String orderCode = order.getOrderCode();
            object.put("id", orderId);
            object.put("orderCode", orderCode);
            Order_confirm order_confirm = insert.QueryOrderConfirmAllById(orderId);
            if (order_confirm == null) {
                object.put("downaim", "");
                object.put("user", "");
            } else {
                int downAimId = order_confirm.getDown_aim();
                int userId = order_confirm.getUserId();
                int dataId = order_confirm.getDataId();
                Downaim downaim = insert.QueryDownAimById(downAimId);
                User user = insert.queryUserById(userId);
                BasicInfoAll basicInfo = insert.queryBasicInfoById(dataId);
                object.put("downaim", downaim);
                object.put("user", user);
                object.put("basicInfo", basicInfo);
            }
            array.add(object);
        });
//        Page<JSONArray> page = new Page<JSONArray>(currentPage, currentCount, totalPage, totalCount, array);
        return JSON.toJSONString(array);
    }
}
