package back.wang.Dao;

import org.junit.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import back.wang.Domain.TypicalAlgo;
import back.wang.Domain.TypicalAlgoTags;
import back.wang.Service.AlgoService;
import utils.KeyUtils;


/**
 * @author wwx-sys
 * @time 2020-08-17-14:45
 * @description
 */
public class AlgoQueryTest {
    AlgoQuery algoQuery = new AlgoQuery();

    @Test
    public void getAllAlgo() {
        List<TypicalAlgo> typicalAlgos = new AlgoQuery().getAllAlgo();
        System.out.println(typicalAlgos.toString());
    }

    @Test
    public void getAlgoByIdTest() {
        TypicalAlgo typicalAlgo = algoQuery.getAlgoById(1);
        System.out.println(typicalAlgo.toString());
    }

    @Test
    public void getAlgoTagsTest() {
        List<TypicalAlgoTags> typicalAlgoTags = algoQuery.getAllTags();
        System.out.println(typicalAlgoTags.toString());
    }

    @Test
    public void getAlgoTagsByIdTest() {
        TypicalAlgoTags typicalAlgoTags = algoQuery.getTagsById(8);
        System.out.println(typicalAlgoTags.toString());
    }

    @Test
    public void insertAlgoTest() {
        TypicalAlgo typicalAlgo = new TypicalAlgo();
        typicalAlgo.setName("suanfa1");
        System.out.println(algoQuery.algoInsert(typicalAlgo));
    }

    @Test
    public void insertTagsTest() {
        TypicalAlgoTags typicalAlgoTags = new TypicalAlgoTags();
        typicalAlgoTags.setName("tag1.1");
        System.out.println(algoQuery.tagInsert(typicalAlgoTags));
    }

    @Test
    public void getAlgoCountTest() {
        System.out.println(algoQuery.getAlgoCounts());
    }

    @Test
    public void getAlgoSTest() {
        AlgoService allAlgoService = new AlgoService();
        System.out.println(algoQuery.queryAlgoByPage(0, 4));
        System.out.println(allAlgoService.allAlgoService(1, 4));
    }

    @Test
    public void isTagsExistsTest() {
        System.out.println(algoQuery.isTagsExistsById(10));
    }

    @Test
    public void isTagsExistsByNameTest() {
        System.out.println(algoQuery.isTagsExistsByName("tag4"));
    }

    @Test
    public void getBinaryIndexTest() {
        int num = 5;//     (1,1,1)
        for (int i = 0; i < 32; i++) {
            if ((num & (1 << i)) != 0) {
                System.out.println(i + 1);
            }
        }
    }

    @Test
    public void getStringLength() {
        String a = "1111111111111111111111111111111111111111111111111111111111111111";
        System.out.println(a.length());
    }

    @Test
    public void simpleTest() {
        System.out.println(Arrays.toString(Integer.toBinaryString(1 << 2).getBytes()));
        System.out.println(Integer.toBinaryString(1 << 2).getBytes()[0]);
        System.out.println(Arrays.toString(new byte[]{Byte.parseByte("49")}));
        System.out.println(new String(Integer.toBinaryString(1 << 9).getBytes()));

    }

    @Test
    public void deleteAlgoTest() {
        System.out.println(algoQuery.deleteAlgo(4));
    }

    @Test
    public void getTagsIdByNameTest() {
        System.out.println(algoQuery.getTagsIdByName("tag1"));
    }


    @Test
    public void intTest() {
        int[] s = null;
        if (1 != 2) {
            s = new int[3];
        }
        if (s != null) {
            System.out.println("Aaaaa");
        }

        System.out.println(Arrays.toString(s));
    }

    @Test
    public void bytesIncreaseTest() {
//        TypicalAlgoTags algoTags = new TypicalAlgoTags();
//        int a = 0;
//        a |= (1 << 6-1);
//        a |= (1 << 8-1);
        String s = "110000001";
        byte[] ss = s.getBytes();
        for (byte b :ss){
            System.out.println(Integer.parseInt(String.valueOf(b)));
        }
//        algoTags.setAlgo(s.getBytes());

//        algoQuery.tagInsert(algoTags);
//        System.out.println(Arrays.toString(Integer.toBinaryString(a).getBytes()));
//        algo.setTags(Integer.toBinaryString(a).getBytes());
//        algoQuery.algoInsert(algo);

//        TypicalAlgo algo1 = algoQuery.getAlgoById(11);
//        System.out.println(algo1);

//        TypicalAlgoTags tags = algoQuery.getTagsById(1);
//        tags.algo = new byte[]{49,48,48,48};
//        tags.algo = Integer.toBinaryString(1 << 9).getBytes();;
//        System.out.println(tags.toString());
//        byte[] algos = Integer.toBinaryString(1 << 9).getBytes();
//        System.out.println(Arrays.toString(algos));
//
//        algos = java.util.Arrays.copyOf(algos, 11);
//        algos[11 -1] = '1';
//        System.out.println(Arrays.toString(algos));
    }


    @Test
    public void pathDeal() {
//        String root = "C:/ftp/ChangJiang/典型数据文档/";
        String root = "D:/ftp/ChangJiang/典型数据文档/";
//        File file = new File(root);
//        System.out.println(Arrays.toString(file.list()));
        String url = "http://101.37.83.223:8025/典型数据文档/爬虫算法/hu2019.pdf";
        String[] rootSplits = root.split("/");
        if (rootSplits.length > 0) {
            String rootDirName = rootSplits[rootSplits.length - 1];
            String[] urlSplits = url.split(rootDirName);
            String docSuffixPath = urlSplits[urlSplits.length - 1];
            File doc = new File(root, docSuffixPath);
            System.out.println(doc.delete());
            System.out.println(doc.getParentFile().delete());
        }
    }

    @Test
    public void queryAlgoTagRelaByAlgoIdTest(){
        System.out.println(algoQuery.deleteRelate(7));
    }

    @Test
    public void phoneTest(){
        String phone = "15927121358";
        System.out.println(KeyUtils.encryptPhone(phone));
        String code = "79635532469";
        System.out.println(KeyUtils.decodePhone(code));

    }

    @Test
    public void base64Test(){
        String id = "c1j";
        byte[] encodeRs = Base64.getEncoder().encode(id.getBytes(StandardCharsets.UTF_8));
//        byte[] decodeRs = Base64.getDecoder().decode(encodeRs);
//        System.out.println(new String(encodeRs));
//        System.out.println(new String(decodeRs));

        System.out.println(KeyUtils.decodeBase64Id(new String(encodeRs)));
    }

}