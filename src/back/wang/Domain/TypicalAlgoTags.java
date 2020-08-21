package back.wang.Domain;

import com.mysql.cj.jdbc.Blob;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wwx-sys
 * @time 2020-08-17-14:29
 * @description 典型算法标签 实体类
 */
public class TypicalAlgoTags {
    private int id;
    private String name;
    public byte[] algo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlgo() {
        return byte2String(algo);
    }

    public void setAlgo(byte[] algo) {
        this.algo = algo;
    }

    //从数据库的二进制的algo转化为对应的id
    public String byte2String(byte[] algo){
        if (algo == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < algo.length; i++) {
            if (algo[i] == '1') {
               sb.append(algo.length - i).append(",");
            }
        }
        if (sb.toString().endsWith(",")) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    //从数据库的二进制的algo转化为对应的id
    public List<Integer> byte2ints() {
        if (algo == null) {
            return null;
        }
        List<Integer> idLists = new ArrayList<>();
        for (int i = 0; i < algo.length; i++) {
            if (algo[i] == '1') {
                idLists.add(algo.length - i);
            }
        }
        return idLists;
    }


    @Override
    public String toString() {
        return "TypicalAlgoTags{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", algo=" + byte2String(algo) +
                '}';
    }
}
