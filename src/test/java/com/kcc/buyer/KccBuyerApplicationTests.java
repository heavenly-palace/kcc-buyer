package com.kcc.buyer;

import com.google.gson.Gson;
import com.kcc.buyer.domain.Company;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class KccBuyerApplicationTests {

    @Test
    public void contextLoads() {

        String json = "{\n" +
                "\t\"asdassda\": {\n" +
                "\t\t\"asdas\": \"15711459123\",\n" +
                "\t\t\"xcx\": 1,\n" +
                "\t\t\"ccc\": \"天下\",\n" +
                "\t\t\"vvv\": \"memories_xiao@163.com\",\n" +
                "\t\t\"vvv\": \"000000\",\n" +
                "\t\t\"bbbb\": \"北京市和平村E区4号楼3单元1702\",\n" +
                "\t\t\"rrr\": \"天下的公司\",\n" +
                "\t\t\"aaa\": \"46369127\"\t\n" +
                "\t}\n" +
                "}";
        Gson gson = new Gson();
        Company company = gson.fromJson(json, Company.class);
        if(company!=null && !"".equals(company)){
            System.out.println("ok");
        }

    }
}
