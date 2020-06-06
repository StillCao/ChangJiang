package front.user_io.service;

import front.user_io.dao.UserQuery;
import front.user_io.domain.User;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/userLogin")
public class UserLogin extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        //1.设置resquest和response编码格式
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin","*"); //解决跨域问题，让返回结果可远程调用

        //2.或取请求参数集,利用BeanUtils。populate()封装到UserBean中
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");

        //3.执行入库操作
        UserQuery uq = new UserQuery();
        int i = uq.userLoginIn(userName,password);

        //4.向前端响应数据response对象

        if (i == 1){
            //用户登录成功，返回用户信息
            List<User> user = uq.queryUser(userName);
            resp.getWriter().append(user.get(0).toString());

        }else{
            //用户信息有误，登录失败
            resp.setStatus(400);
            resp.getWriter().append("账号名或密码有误");
        }



    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
}
