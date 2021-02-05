package back.simba.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 写一个全局过滤器，解决响应头缺失问题
 * @author Simba
 * @date 2020/12/17 21:31
 */
@WebFilter("/*") //访问项目下的所有资源时，过滤器都会被执行
public class ReponseFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        //增加响应头缺失代码
        HttpServletRequest req=(HttpServletRequest)request;
        HttpServletResponse res=(HttpServletResponse)response;
        res.addHeader("X-Frame-Options","SAMEORIGIN");
        res.addHeader("Referer-Policy","origin");
        res.addHeader("Content-Security-Policy","object-src 'self'");
        res.addHeader("X-Permitted-Cross-Domain-Policies","master-only");
        res.addHeader("X-Content-Type-Options","nosniff");
        res.addHeader("X-XSS-Protection","1; mode=block");
        res.addHeader("X-Download-Options","noopen");
        res.addHeader("Set-Cookie", "name=value; HttpOnly");
        res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        res.addHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,token, content-type"); //这里要加上content-type
        res.addHeader("Access-Control-Allow-Credentials", "true");
        res.addHeader("Access-Control-Max-Age", "3600");
        //处理cookie问题
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String value = cookie.getValue();
                StringBuilder builder = new StringBuilder();
                builder.append(cookie.getName()+"="+value+";");
                builder.append("Secure;");//Cookie设置Secure标识
                builder.append("HttpOnly;");//Cookie设置HttpOnly
                res.addHeader("Set-Cookie", builder.toString());
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
