package com.kcc.buyer.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kcc.buyer.common.ResponseEntity;
import com.kcc.buyer.domain.AccountInfo;
import com.kcc.buyer.domain.CompanyInfo;
import com.kcc.buyer.domain.Order;
import com.kcc.buyer.domain.OrderDetail;
import com.kcc.buyer.mapper.AccountInfoMapper;
import com.kcc.buyer.mapper.CompanyInfoMapper;
import com.kcc.buyer.mapper.OrderDetailMapper;
import com.kcc.buyer.mapper.OrderMapper;
import com.kcc.buyer.util.GeneratePdf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Singleton
@Component
@Path("/pdf")
public class GeneratePdfController {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    @Autowired
    private AccountInfoMapper accountInfoMapper;


    @GET
    @Path("/generatepdf/{orderId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public ResponseEntity generatePdf(@Context HttpServletResponse response, @PathParam("orderId") Integer orderId) throws IOException {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if(order != null){
            List<CompanyInfo> companyInfoList = companyInfoMapper.selectSelective(new CompanyInfo(orderId));
            order.setCompanyInfoList(companyInfoList);
            companyInfoList.forEach(companyInfo -> {
                AccountInfo accountInfo = accountInfoMapper.selectSelective(new AccountInfo(companyInfo.getId()));
                companyInfo.setAccountInfo(accountInfo);
            });
            List<OrderDetail> orderDetails = orderDetailMapper.selectSelective(new OrderDetail(orderId));
            order.setOrderDetailList(orderDetails);
        }
        if(!ObjectUtils.isEmpty(order)){
            GeneratePdf generatePdf = new GeneratePdf();
            ByteArrayOutputStream byteArrayOutputStream = generatePdf.generatePDF(order);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition","inline; filename=" + "kcc-order.pdf");
            response.setContentLengthLong(byteArrayOutputStream.size());
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            outputStream.write(byteArrayOutputStream.toByteArray());
            outputStream.flush();
            outputStream.close();
        }
        return ResponseEntity.ok();
    }
}
