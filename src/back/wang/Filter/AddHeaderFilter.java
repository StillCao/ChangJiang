package back.wang.Filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 为了给响应头加上Content-Security-Policy等头的filter类
 */
public class AddHeaderFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
//        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Content-Security-Policy", "default-src 'self'");      //内容安全策略（CSP）用于检测和减轻用于 Web 站点的特定类型的攻击，例如 XSS 和数据注入等。
//        res.addHeader("X-Content-Type-Options","");                             //HTTP 消息头相当于一个提示标志，被服务器用来提示客户端一定要遵循在 Content-Type 首部中对  MIME 类型 的设定，而不能对其进行修改。这就禁用了客户端的 MIME 类型嗅探行为
        res.addHeader("X-XSS-Protection", "1;mode=block");                   // 当检测到跨站脚本攻击 (XSS)时，浏览器将停止加载页面。
        chain.doFilter(req, res);
    }

    public void destroy() {
    }
}