package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

/**
 * 描述:
 * 订单编号生成
 * 生成订单唯一主键，纯数字
 *
 * @author black-leaves
 * @createTime 2020-07-01  11:09
 */

public class KeyUtils {
    /**
     * 生成主键id
     * 时间+随机数
     */
    public static synchronized String generateUniqueKey() {
        Random random = new Random();
        // 随机数的量 自由定制，这是9位随机数
        int r = random.nextInt(900000000) + 100000000;

        // 返回  13位时间
        Long timeMillis = System.currentTimeMillis();

        // 返回  17位时间
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String timeStr = sdf.format(new Date());

        // 13位毫秒+9位随机数
        ///return  timeMillis + String.valueOf(r);
        // 17位时间+9位随机数
        return timeStr + r;
    }

    public static String encryptPhone(String phone) {
        if (phone.length() < 11) {
            return phone;
        }
        String str1 = phone.substring(0, 6);
        String str2 = phone.substring(6);
        int num1 = Integer.parseInt(str1) * 5;
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < str2.length(); i++) {
            int t = Integer.parseInt(String.valueOf(str2.charAt(i))) + 1;
            if (t == 10) {
                t = 0;
            }
            sb2.append(t);
        }
        return num1 + sb2.toString();
    }

    public static String decodePhone(String word) {
        String str1 = word.substring(0, 6);
        String str2 = word.substring(6);
        int num1 = Integer.parseInt(str1) / 5;
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < str2.length(); i++) {
            int t = Integer.parseInt(String.valueOf(str2.charAt(i))) - 1;
            if (t == -1) {
                t = 9;
            }
            sb2.append(t);
        }
        return num1 + sb2.toString();

    }

    public static int decodeBase64Id(String base64Id){
        byte[] decodeRs = Base64.getDecoder().decode(base64Id);
        String temp = new String(decodeRs);
        String idString = temp.substring(1,temp.length() - 1);
        return Integer.parseInt(idString);
    }
}


