package back.wang.Servlet;

import back.wang.Domain.Admin;
import back.wang.Service.AdminService;
import front.basic_page.Servlet.BaseServlet;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 描述:
 * 管理员接口
 *
 * @author black-leaves
 * @createTime 2020-06-23  9:34
 */

@WebServlet("/AdminServlet")
public class AdminServlet extends BaseServlet {

    /**
     * 分页查询所有管理员
     */
    public void getAllAdmins(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, String[]> map = req.getParameterMap();
        int currentPage = Integer.parseInt(map.getOrDefault("currentPage", new String[]{"1"})[0]);
        int currentCount = Integer.parseInt(map.getOrDefault("currentCount", new String[]{"10"})[0]);
        String result = new AdminService().allAdminService(currentPage, currentCount);

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(result);
    }

    /**
     * 根据Id查询管理员
     */
    public void getAdminById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idString = req.getParameter("id");
        int id = Integer.parseInt(idString);
        String result = new AdminService().adminById(id);

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(result);
    }

    /**
     * 根据名称模糊查询管理员
     */

    public void getAdminByAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, String[]> map = req.getParameterMap();
        int currentPage = Integer.parseInt(map.getOrDefault("currentPage", new String[]{"1"})[0]);
        int currentCount = Integer.parseInt(map.getOrDefault("currentCount", new String[]{"10"})[0]);
        String account = map.get("account")[0];
        String result = new AdminService().adminByAccount(account,currentPage, currentCount);

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(result);
    }

    /**
     * 添加管理员
     */
    public void addAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        Map<String, String[]> map = req.getParameterMap();
        Admin admin = new Admin();
        BeanUtils.populate(admin, map);
        AdminService adminService = new AdminService();
        String result = adminService.adminAdd(admin);

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(result);
    }

    /**
     * 修改管理员信息
     */
    public void updateAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        Map<String, String[]> map = req.getParameterMap();
        Admin admin = new Admin();
        BeanUtils.populate(admin, map);
        AdminService adminService = new AdminService();
        boolean result = adminService.adminUpdate(admin);

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(String.valueOf(result));
    }

    /**
     * 删除管理员信息
     */
    public void deleteAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idString = req.getParameter("id");
        int id = Integer.parseInt(idString);
        AdminService adminService = new AdminService();
        boolean result = adminService.adminDelete(id);

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(String.valueOf(result));

    }

    /**
     * 管理员登录
     */
    public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String account = req.getParameter("account");
        String password = req.getParameter("password");

        if (account.equals("")||password.equals("")){
            return;
        }

        Admin admin = new Admin();
        admin.setAccount(account);
        admin.setPassword(password);

        AdminService adminService = new AdminService();
        int id = adminService.adminLogin(admin);

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(String.valueOf(id));

    }


}
