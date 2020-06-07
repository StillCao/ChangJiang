package front.user_io.service;

import front.user_io.dao.UserQuery;
import front.user_io.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
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
            //4.1用户登录成功，返回用户信息
            List<User> user = uq.queryUser(userName);
            resp.getWriter().append(user.get(0).toString());

            //4.2通过设置cookie来存储用户的登陆时间。客户端会话技术
            //获取Cookie数组
            Cookie[] cookies = req.getCookies();
            boolean flag = false; //是否首次访问
            //本次访问时间
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            String str_loginTime = sdf.format(date); //本次登陆时间
            str_loginTime = URLEncoder.encode(str_loginTime,"utf-8");  //中文以及特殊字符：建议使用URL编码存储，URL解码解析

            //遍历cookies
            if (cookies!=null && cookies.length>0){
                for (Cookie cookie : cookies) {
                    if ("lastTime".equals(cookie.getName())){
                        //不是第一次访问，则重新设置cookie值，为本次登陆时间。
                      //  String value = cookie.getValue();
                       // System.out.println("上次访问时间为："+value);
                       // resp.getWriter().append("上次访问时间为："+value);
                        flag = true;
                        cookie.setValue(str_loginTime);
                        cookie.setMaxAge(60 * 60 * 24 * 30);//一个月
                        resp.addCookie(cookie);
                        break;
                    }
                }
            }

            if (cookies == null || cookies.length == 0 || flag == false){
                //第一次访问
                Cookie cookie = new Cookie("lastTime", str_loginTime);
                cookie.setMaxAge(60 * 60 *24 *30); // one Month
                resp.addCookie(cookie);
                //resp.getWriter().append("您好，欢迎访问！");
            }



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
