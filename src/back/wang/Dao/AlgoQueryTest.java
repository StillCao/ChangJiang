package back.wang.Dao;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import back.wang.Domain.TypicalAlgo;
import back.wang.Domain.TypicalAlgoTags;
import back.wang.Service.AlgoService;


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
    public void getAlgoByIdTest(){
        TypicalAlgo typicalAlgo = algoQuery.getAlgoById(1);
        System.out.println(typicalAlgo.toString());
    }

    @Test
    public void getAlgoTagsTest(){
        List<TypicalAlgoTags> typicalAlgoTags = algoQuery.getAllTags();
        System.out.println(typicalAlgoTags.toString());
    }

    @Test
    public void getAlgoTagsByIdTest(){
        TypicalAlgoTags typicalAlgoTags = algoQuery.getTagsById(1);
        System.out.println(typicalAlgoTags.toString());
    }

    @Test
    public void insertAlgoTest(){
        TypicalAlgo typicalAlgo = new TypicalAlgo();
        typicalAlgo.setName("suanfa1");
        System.out.println(algoQuery.algoInsert(typicalAlgo));
    }

    @Test
    public void insertTagsTest(){
        TypicalAlgoTags typicalAlgoTags = new TypicalAlgoTags();
        typicalAlgoTags.setName("tag1.1");
        System.out.println(algoQuery.tagInsert(typicalAlgoTags));
    }

    @Test
    public void getAlgoCountTest(){
        System.out.println(algoQuery.getAlgoCounts());
    }

    @Test
    public void getAlgoSTest(){
        AlgoService allAlgoService = new AlgoService();
        System.out.println(algoQuery.queryAlgoByPage(0,4));
        System.out.println(allAlgoService.allAlgoService(1,4));
    }

    @Test
    public void isTagsExistsTest(){
        System.out.println(algoQuery.isTagsExistsById(10));
    }

    @Test
    public void isTagsExistsByNameTest(){
        System.out.println(algoQuery.isTagsExistsByName("tag4"));
    }

    @Test
    public void getBinaryIndexTest(){
        int num = 5;//     (1,1,1)
        for (int i = 0; i < 32; i++) {
            if ((num & (1 << i)) != 0) {
                System.out.println(i + 1);
            }
        }
    }

    @Test
    public void getStringLength(){
        String a = "1111111111111111111111111111111111111111111111111111111111111111";
        System.out.println(a.length());
    }

    @Test
    public void simpleTest(){
        System.out.println(Arrays.toString(Integer.toBinaryString(1 << 2).getBytes()));
        System.out.println(Arrays.toString(new byte[]{Byte.parseByte("49")}));

    }

    @Test
    public void updateTagsAlgo(){
        byte[] bytes = new byte[]{Byte.parseByte("49")};
        System.out.println(algoQuery.updateTagsAlgo(7,bytes));
    }

    @Test
    public void deleteAlgoTest(){
        System.out.println(algoQuery.deleteAlgo(4));
    }

    @Test
    public void getTagsIdByNameTest(){
        System.out.println(algoQuery.getTagsIdByName("tag1"));
    }

    @Test
    public void getTagsByNameTest(){
        System.out.println(algoQuery.getTagsByName("tag1111"));
    }




}