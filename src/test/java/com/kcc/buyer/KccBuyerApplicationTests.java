package com.kcc.buyer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kcc.buyer.domain.Order;
import com.kcc.buyer.util.GeneratePdf;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class KccBuyerApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void generatePdfTest(){
        String orderJson = "{\n" +
                "\t\"orderUuid\": \"PO201912260609\",\n" +
                "\t\"comment\": \"这是一条订单\",\n" +
                "\t\"companyInfoList\": [{\n" +
                "\t\t\t\"orderUuid\": \"PO201912260609\",\n" +
                "\t\t\t\"name\": \"深圳捷联讯通科技有限公司\",\n" +
                "\t\t\t\"cellphone\": \"13802255316\",\n" +
                "\t\t\t\"type\": 1,\n" +
                "\t\t\t\"contacts\": \"聂小姐\",\n" +
                "\t\t\t\"email\": \"limei@edcwifi.com\",\n" +
                "\t\t\t\"fax\": \"000000\",\n" +
                "\t\t\t\"location\": \"我也不知道\",\n" +
                "\t\t\t\"telephone\": \"我也不知道\",\n" +
                "\t\t\t\"accountInfo\": {\n" +
                "\t\t\t\t\"bankName\": \"浙江义乌农村商业银行股份有限公司北苑支行凯吉分理处\",\n" +
                "\t\t\t\t\"bankAccount\": \"201000230648017\",\n" +
                "\t\t\t\t\"location\": \"我也不知道\",\n" +
                "\t\t\t\t\"telephone\": \"我也不知道\",\n" +
                "\t\t\t\t\"fax\": \"111111\",\n" +
                "\t\t\t\t\"tfn\": \"92330782MA2E7E1H5K\"\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"北京康诚尚德通信技术有限公\",\n" +
                "\t\t\t\"cellphone\": \"15711459123\",\n" +
                "\t\t\t\"type\": 1,\n" +
                "\t\t\t\"contacts\": \"苏雷\",\n" +
                "\t\t\t\"email\": \"lei.su@kc-tech.com\",\n" +
                "\t\t\t\"fax\": \"000000\",\n" +
                "\t\t\t\"location\": \"我也不知道\",\n" +
                "\t\t\t\"telephone\": \"我也不知道\",\n" +
                "\t\t\t\"accountInfo\": {\n" +
                "\t\t\t\t\"bankName\": \"华夏银行北京光华支\",\n" +
                "\t\t\t\t\"bankAccount\": \"4057200001804000031645\",\n" +
                "\t\t\t\t\"location\": \"北京市朝阳区东四环中路62号楼远洋国际中心D座10层1005\",\n" +
                "\t\t\t\t\"telephone\": \"15711459123\",\n" +
                "\t\t\t\t\"fax\": \"111111\",\n" +
                "\t\t\t\t\"tfn\": \"911101056949533316\"\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\n" +
                "\t],\n" +
                "\t\"orderDetailList\": [{\n" +
                "\t\t\t\"id\": 1,\n" +
                "\t\t\t\"orderId\": 8,\n" +
                "\t\t\t\"currency\": \"人民币\",\n" +
                "\t\t\t\"describe\": \"金属LOGO\",\n" +
                "\t\t\t\"pack\": \"1000个\",\n" +
                "\t\t\t\"price\": 180.0,\n" +
                "\t\t\t\"quantity\": 1,\n" +
                "\t\t\t\"supplyDate\": \"现货60片，期货交期15天\",\n" +
                "\t\t\t\"taxRate\": 0.03\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"id\": 1,\n" +
                "\t\t\t\"orderId\": 9,\n" +
                "\t\t\t\"currency\": \"人民币\",\n" +
                "\t\t\t\"describe\": \"金属LOGO\",\n" +
                "\t\t\t\"pack\": \"1000个\",\n" +
                "\t\t\t\"price\": 180.0,\n" +
                "\t\t\t\"quantity\": 1,\n" +
                "\t\t\t\"supplyDate\": \"现货60片，期货交期15天\",\n" +
                "\t\t\t\"taxRate\": 0.03\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"id\": 1,\n" +
                "\t\t\t\"orderId\": 10,\n" +
                "\t\t\t\"currency\": \"人民币\",\n" +
                "\t\t\t\"describe\": \"金属LOGO\",\n" +
                "\t\t\t\"pack\": \"1000个\",\n" +
                "\t\t\t\"price\": 180.0,\n" +
                "\t\t\t\"quantity\": 1,\n" +
                "\t\t\t\"supplyDate\": \"现货60片，期货交期15天\",\n" +
                "\t\t\t\"taxRate\": 0.03\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"id\": 1,\n" +
                "\t\t\t\"orderId\": 11,\n" +
                "\t\t\t\"currency\": \"人民币\",\n" +
                "\t\t\t\"describe\": \"金属LOGO\",\n" +
                "\t\t\t\"pack\": \"1000个\",\n" +
                "\t\t\t\"price\": 180.0,\n" +
                "\t\t\t\"quantity\": 1,\n" +
                "\t\t\t\"supplyDate\": \"现货60片，期货交期15天\",\n" +
                "\t\t\t\"taxRate\": 0.03\n" +
                "\t\t}\n" +
                "\n" +
                "\t]\n" +
                "}";

        Gson gson = new Gson();
        Order order = gson.fromJson(orderJson, new TypeToken<Order>() {}.getType());
        GeneratePdf generatePdf = new GeneratePdf();
        generatePdf.generatePDF(order);
    }
}
