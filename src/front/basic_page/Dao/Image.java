package front.basic_page.Dao;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Fun:
 * Created by CW on 2020/6/16 4:06 下午
 */
public class Image extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        String filePath = "图片路径";
        File file = new File(filePath);
        //1.定义一个字节输入流，将图片从硬盘读到内存中
        FileInputStream fis = new FileInputStream(file);

        //2.将文件读到字节数组中
        int len ;  //每次读取有效字节的长度
        byte[] bytes = new byte[1024*8];  // 定义字节数组，作为装字节数据的容器
        while ((len = fis.read(bytes)) != -1){
            //System.out.println(new String(bytes,0,len));    //这里从0写到len长度的数据。因为最后一次读取的数据len有效长度不定。
        }

        //3.向前端返回图片
        OutputStream os = resp.getOutputStream();
        os.write(bytes);

        os.close();//关闭流

    }


}
