package front.basic_page.Servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@WebServlet(name = "BaseServlet")
public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        try {
            //1.获取方法名
            String method = req.getParameter("method");
            //2.获取到当前对象的字节码文件
            //拿到字节码对象中的方法
            Method clazzMethod = this.getClass().getMethod(method, HttpServletRequest.class, HttpServletResponse.class);
            //执行方法
            clazzMethod.invoke(this,req,resp);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
