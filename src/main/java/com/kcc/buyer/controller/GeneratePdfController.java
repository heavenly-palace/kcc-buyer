package com.kcc.buyer.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kcc.buyer.common.ResponseEntity;
import com.kcc.buyer.domain.Order;
import com.kcc.buyer.util.GeneratePdf;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("/pdf")
public class GeneratePdfController {

    @GET
    @Path("/generatepdf")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    @Transactional
    public ResponseEntity generatePdf(){
        GeneratePdf generatePdf = new GeneratePdf();
        String orderJson = "{\n" +
                "\t\"comment\": \"这是一条订单\",\n" +
                "\t\"orderUuid\": \"PO201912260609\",\n" +
                "\t\"money\": 968.2,\n" +
                "\t\"upperMoney\": \"玖佰陆拾捌元贰角\",\n" +
                "\t\"atMoney\": 940.0,\n" +
                "\t\"upperAtMoney\": \"玖佰肆拾元\",\n" +
                "\t\"companyInfoList\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"no\": \"20200416002\",\n" +
                "\t\t\t\"name\": \"深圳捷联讯通科技有限公司\",\n" +
                "\t\t\t\"cellphone\": \"13802255316\",\n" +
                "\t\t\t\"type\": 1,\n" +
                "\t\t\t\"contacts\": \"聂小姐\",\n" +
                "\t\t\t\"email\": \"limei@edcwifi.com\",\n" +
                "\t\t\t\"fax\": \"000000\",\n" +
                "\t\t\t\"location\": \"我也不知道\",\n" +
                "\t\t\t\"telephone\": \"我也不知道\",\n" +
                "\t\t\t\"comment\": \"备注\",\n" +
                "\t\t\t\"accountInfo\": {\n" +
                "\t\t\t\t\"bankName\": \"浙江义乌农村商业银行股份有限公司北苑支行凯吉分理处\",\n" +
                "\t\t\t\t\"bankAccount\": \"201000230648017\",\n" +
                "\t\t\t\t\"location\": \"我也不知道\",\n" +
                "\t\t\t\t\"telephone\": \"我也不知道\",\n" +
                "\t\t\t\t\"tfn\": \"92330782MA2E7E1H5K\"\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"no\": \"20200416001\",\n" +
                "\t\t\t\"name\": \"北京康诚尚德通信技术有限公司\",\n" +
                "\t\t\t\"cellphone\": \"13601152963\",\n" +
                "\t\t\t\"type\": 2,\n" +
                "\t\t\t\"contacts\": \"苏雷\",\n" +
                "\t\t\t\"email\": \"lei.su@kc-tech.com\",\n" +
                "\t\t\t\"fax\": \"000000\",\n" +
                "\t\t\t\"location\": \"北京市朝阳区东四环中路62号楼远洋国际中心D坐10层1005\",\n" +
                "\t\t\t\"telephone\": \"010-59139696\",\n" +
                "\t\t\t\"comment\": \"备注\",\n" +
                "\t\t\t\"accountInfo\": {\n" +
                "\t\t\t\t\"bankName\": \"华夏银行北京光华支行\",\n" +
                "\t\t\t\t\"bankAccount\": \"4057200001804000031645\",\n" +
                "\t\t\t\t\"location\": \"我也不知道\",\n" +
                "\t\t\t\t\"telephone\": \"010-59139666\",\n" +
                "\t\t\t\t\"tfn\": \"911101056949533316\"\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"orderDetailList\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"currency\": \"人民币\",\n" +
                "\t\t\t\"describe\": \"金属LOGO\",\n" +
                "\t\t\t\"pack\": \"1000个\",\n" +
                "\t\t\t\"price\": 180.0,\n" +
                "\t\t\t\"aTPrice\": 185.4,\n" +
                "\t\t\t\"totalPrice\": 540.0,\n" +
                "\t\t\t\"aTTotalPrice\": 556.2,\n" +
                "\t\t\t\"quantity\": 3,\n" +
                "\t\t\t\"supplyDate\": \"现货60片，期货交期15天\",\n" +
                "\t\t\t\"taxRate\": 0.03\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"currency\": \"人民币\",\n" +
                "\t\t\t\"describe\": \"交换机\",\n" +
                "\t\t\t\"pack\": \"一台\",\n" +
                "\t\t\t\"price\": 200.0,\n" +
                "\t\t\t\"aTPrice\": 206.0,\n" +
                "\t\t\t\"totalPrice\": 400.0,\n" +
                "\t\t\t\"aTTotalPrice\": 412.0,\n" +
                "\t\t\t\"quantity\": 2,\n" +
                "\t\t\t\"supplyDate\": \"现货60片，期货交期15天\",\n" +
                "\t\t\t\"taxRate\": 0.03\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}";

        Gson gson = new Gson();
        Order order = gson.fromJson(orderJson, new TypeToken<Order>() {}.getType());
        generatePdf.generatePDF(order);
        return ResponseEntity.ok();
    }
}
