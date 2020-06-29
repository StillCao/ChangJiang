package back.wang.Service;

import back.wang.Dao.AdminQuery;
import back.wang.Domain.Admin;
import back.wang.Domain.Page;
import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * 描述:
 * 管理员服务
 *
 * @author black-leaves
 * @createTime 2020-06-23  9:38
 */

public class AdminService {
    /**
     * @param currentPage  当前页码
     * @param currentCount 每页条数
     * @return Page<Admin>转为的JsonString
     */
    public String allAdminService(int currentPage, int currentCount) {
        AdminQuery adminQuery = new AdminQuery();
        int totalCount = adminQuery.queryAdminCount();
        int totalPage = totalCount % currentCount == 0 ? totalCount / currentCount : totalCount / currentCount + 1;
        int startPosition = (currentPage - 1) * currentCount;
        List<Admin> admins = adminQuery.queryAdminsByPage(startPosition, currentCount);
        Page<Admin> page = new Page<>(currentPage, currentCount, totalPage, totalCount, admins);
        return JSON.toJSONString(page);
    }

    /**
     * @param id 管理员 id
     * @return 根据id查信息
     */
    public String adminById(int id) {
        AdminQuery adminQuery = new AdminQuery();
        Admin admin = adminQuery.queryAdminById(id);
        return JSON.toJSONString(admin);
    }

    /** 根据名称模糊分页查询
     * @param account 管理员 account
     * @return 根据account查信息
     */
    public String adminByAccount(String account,int currentPage, int currentCount) {
        AdminQuery adminQuery = new AdminQuery();
        int totalCount = adminQuery.queryAdminCount();
        int totalPage = totalCount % currentCount == 0 ? totalCount / currentCount : totalCount / currentCount + 1;
        int startPosition = (currentPage - 1) * currentCount;
        List<Admin> admins = adminQuery.queryAdminByAccountLikeByPage(account,startPosition ,currentCount);
        Page<Admin> page = new Page<>(currentPage, currentCount, totalPage, totalCount, admins);
        return JSON.toJSONString(page);
    }

    /**
     * 管理员添加
     *
     * @param admin
     * @return 返回添加状态
     */
    public String adminAdd(Admin admin) {
        AdminQuery adminQuery = new AdminQuery();
        String account = admin.getAccount();
        Admin isAdmin = adminQuery.queryAdminByAccount(account);
        boolean unExited = (isAdmin == null);
        if (unExited) {
            String password = admin.getPassword();
            if (!account.equals("") && !password.equals("")) {
                if (adminQuery.addAdmin(admin)) {
                    return "添加管理员成功！";
                } else return "添加管理员失败！cause:未知";
            }
        }
        return "添加管理员失败！cause:用户名已经存在";
    }

    /**
     * 管理员信息修改
     *
     * @param admin
     * @return 返回是否修改成功
     */
    public boolean adminUpdate(Admin admin) {
        AdminQuery adminQuery = new AdminQuery();
        if (admin != null) {
            return adminQuery.updateAdmin(admin);
        }
        return false;
    }

    /**
     * 管理员删除
     *
     * @param id admin id
     * @return 返回是否删除成功
     */
    public boolean adminDelete(int id) {
        AdminQuery adminQuery = new AdminQuery();
        return adminQuery.deleteAdminById(id);
    }

    /**
     * 管理员登录
     * @param admin account 和 password 不为空的 admin
     * @return
     */
    public int adminLogin(Admin admin){
        AdminQuery adminQuery = new AdminQuery();
        return adminQuery.loginAdmin(admin);
    }
}
