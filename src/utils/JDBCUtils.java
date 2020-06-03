package utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Druid连接池的工具类
 */
public class JDBCUtils {
    //1.定义成员变量 DataSource 连接池对象ds
    private static DataSource ds;
    static {
        try {
            //2.加载配置文件-->配置文件中包括注册驱动等信息
            Properties pro = new Properties();
            InputStream inputStream = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
            pro.load(inputStream);
            //3.获取连接池对象
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 提供获取连接池对象的方法
     * */
    public static DataSource getDataSource(){
        return ds;
    }

    /*
     * 提供获取连接的方法 getConnection
     * */
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    /*
     * -->Springtemplate中不需要调用close()
     * 提供2种重载的关闭资源的方法
     * 根据执行DQL,DMl语句来选择
     * */
    public static void close(Statement stmt,Connection conn){
        close(null,stmt,conn);
    }

    public static void close(ResultSet rs, Statement stmt, Connection conn){
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
