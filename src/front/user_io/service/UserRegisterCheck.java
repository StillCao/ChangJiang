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
import java.lang.reflect.InvocationTargetException;
import java.util.Map;


@WebServlet("/userRegisterCheck")
public class UserRegisterCheck extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        //1.设置resquest和response编码格式
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin","*"); //解决跨域问题，让返回结果可远程调用
        //resp.setHeader("Access-Control-Expose-Headers","flag"); //将自定义响应头，暴露给用户。

        //2.或取请求参数集,利用BeanUtils。populate()封装到UserBean中
        Map<String, String[]> parameterMap = req.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3.执行入库操作
        int i = 0;
        if (user.getUserName()!=null){
             i = new UserQuery().userConfi("userName", user.getUserName());
        }else if(user.getEmail()!=null){
             i = new UserQuery().userConfi("email", user.getEmail());
        }else if (user.getPhone()!=null){
             i = new UserQuery().userConfi("phone", user.getPhone());
        }

        //4.向前端响应数据response对象
        if (i == 1){
            //昵称或者邮箱已存在
            //resp.setIntHeader("flag",0);
            resp.setStatus(400);
            resp.getWriter().append("false");
        }else{
           // resp.setIntHeader("flag",1);
            resp.getWriter().append("true");
            resp.setStatus(200);

        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
}
