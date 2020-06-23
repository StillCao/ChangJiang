package back.wang.Test;

import back.wang.Dao.AdminQuery;
import back.wang.Domain.Admin;

import java.util.List;

/**
 * 描述:
 *
 * @author black-leaves
 * @createTime 2020-06-23  9:42
 */

public class AdminQueryTest {

    @org.junit.Test
    public void queryAllAdmins() {
        List<Admin> admins = new AdminQuery().queryAllAdmins();
        System.out.println(admins);
    }

    @org.junit.Test
    public void queryAdminCount() {
        System.out.println(new AdminQuery().queryAdminCount());
    }

    @org.junit.Test
    public void queryAdminById() {
        Admin admin = new AdminQuery().queryAdminById(1);
        System.out.println(admin.toString());
    }

    @org.junit.Test
    public void queryAdminByAccount() {
        Admin admin = new AdminQuery().queryAdminByAccount("wwx");
        if (admin ==null){
            System.out.println("");
            return;
        }
        System.out.println(admin.toString());
    }

    @org.junit.Test
    public void updateAdmin() {
        Admin admin = new Admin();
        admin.setAccount("wwxx");
        admin.setPassword("123456");
        admin.setId(3);
        System.out.println(new AdminQuery().updateAdmin(admin));
    }

    @org.junit.Test
    public void queryAdminByAccountLikeByPage() {
        Admin admin = new Admin();
        admin.setAccount("wwxx");
        admin.setPassword("123456");
        admin.setId(3);
        System.out.println(new AdminQuery().queryAdminByAccountLikeByPage("wwx",0,10));
    }


}