package front.data_query.dao;

import front.data_query.domain.AllInfo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class Download {

    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    public String downloadfile(HttpServletRequest request, HttpServletResponse response, int id) throws IOException {


        ServletOutputStream out = null;
        FileInputStream ips = null;
        String name = null;
        List<AllInfo> list = null;  //此处为业务需要
        list = template.query("select * from basic_info where id = ?",new BeanPropertyRowMapper<>(AllInfo.class),id);  //此处为业务需要

        if(list.size() > 0){
            try {
                String url = list.get(0).getSample_url();//此处为业务需要  如果是测试可以指定路径
                //获取文件存放的路径
                File file = new File(url);
                String fileName=file.getName();

                name = list.get(0).getName()+" —"+fileName;
                System.out.println(name);
                System.out.println(fileName);

                if(!file.exists()) {
                    //如果文件不存在就跳出
                    return null;
                }
                ips = new FileInputStream(file);
                response.setContentType("application/octet-stream");
                //为文件重新设置名字，采用数据库内存储的文件名称
                //response.getWriter().append(name);
                response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"),"ISO8859-1") + "\"");
                out = response.getOutputStream();
                //读取文件流
                int len = 0;
                byte[] buffer = new byte[1024 * 10];
                while ((len = ips.read(buffer)) != -1){
                    out.write(buffer,0,len);
                }
                out.flush();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    out.close();
                    ips.close();
                } catch (IOException e) {
                    System.out.println("关闭流出现异常");
                    e.printStackTrace();
                }
            }
        }
        return name;
    }

    /**
     *
     * @return
     */
    public String getSampleName(int id){

        String name = null;
        List<AllInfo> list = null;  //此处为业务需要
        list = template.query("select * from basic_info where id = ?",new BeanPropertyRowMapper<>(AllInfo.class),id);

        String url = list.get(0).getSample_url();

        if (url == null){
            return null;
        }

        File file = new File(url);
        String fileName=file.getName();

        name = list.get(0).getName()+" ——"+fileName;
        System.out.println(name);
        System.out.println(fileName);
        return name;
    }
}
