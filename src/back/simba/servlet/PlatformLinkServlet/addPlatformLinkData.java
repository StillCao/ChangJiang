package back.simba.servlet.PlatformLinkServlet;

import back.simba.dao.PlatQuery;
import back.simba.domain.PlatformLink;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 新增平台链接接口
 * @author Simba
 * @date 2020/10/28 22:40
 */
@WebServlet("/AddPLData")
public class addPlatformLinkData extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1. 设置request和response编码格式
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //2.获取post请求中的json字符串
        StringBuffer sb = new StringBuffer();
        InputStream is = request.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String s = "";
        while ((s = br.readLine()) != null){
            sb.append(s);
        }
        String str = sb.toString();

        JSONArray jsonArray = JSONArray.parseArray(str);

        //3.遍历json数组，将json数组中的每个jsonobject转为特定对象
        ObjectMapper mapper = new ObjectMapper();
        PlatQuery pQ = new PlatQuery();//创建一个包含操作平台方法的对象
        Map<String,Integer> isSuccessed = new HashMap<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            PlatformLink pl = mapper.readValue(jsonArray.getJSONObject(i).toJSONString(), PlatformLink.class);//将每个json对象转为PlatformLink对象
            int res = pQ.addNewPlatform(pl);//插入新增的数据
            isSuccessed.put(pl.getName(),res);//封装每条新增数据的插入操作执行结果
        }
        String result = mapper.writeValueAsString(isSuccessed);//java的Map集合转为JSON字符串，返回给前端

        //4.向前端响应json数据
        response.setHeader("Access-Control-Allow-Origin","*"); //解决跨域问题，让返回结果可远程调用
        response.getWriter().append(result);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
