package com.kcc.buyer.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kcc.buyer.domain.*;
import com.kcc.buyer.mapper.*;
import com.kcc.buyer.util.GeneratePdf;
import com.kcc.buyer.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

@Singleton
@Component
@Path("/api")
public class BuyerController {

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private ObjectUtil objectUtil;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private BuyerAgentMapper buyerAgentMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private Gson gson;

    @GET
    @Path("/supplier")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    @Transactional(readOnly = true)
    public Response getSuppliers(){
        List<Company> suppliers = companyMapper.selectSuppliers();
        return Response.status(HttpStatus.SC_OK).entity(suppliers).build();
    }

    @GET
    @Path("/supplier/{supplierId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    @Transactional(readOnly = true)
    public Response getSupplier(@PathParam("supplierId") Integer supplierId){
        Company supplier = companyMapper.selectByPrimaryKey(supplierId);
        String toJson = gson.toJson(supplier);
        CompanyVO supplierVO = gson.fromJson(toJson, CompanyVO.class);
        Account account = accountMapper.selectByPrimaryKey(supplierVO.getId());
        supplierVO.setAccount(account);
        List<Product> productList = productMapper.selectBySupplierId(supplierVO.getId());
        supplierVO.setProductList(productList);
        return Response.status(HttpStatus.SC_OK).entity(supplierVO).build();
    }

    @POST
    @Path("/supplier")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    @Transactional
    public Response createSupplier(String request){
        CompanyVO supplierVO = gson.fromJson(request, CompanyVO.class);
        supplierVO = objectUtil.getCompanyNo(supplierVO);
        companyMapper.insertSelective(supplierVO);
        Account account = supplierVO.getAccount();
        account.setId(supplierVO.getId());
        accountMapper.insertSelective(account);
        return Response.status(HttpStatus.SC_OK).build();
    }

    @PUT
    @Path("/supplier")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    @Transactional
    public Response updateSupplier(String request){
        CompanyVO supplierVO = gson.fromJson(request, CompanyVO.class);
        if(supplierVO.getId() != null){
            companyMapper.updateByPrimaryKeySelective(supplierVO);
        }
        if(supplierVO.getAccount() != null && supplierVO.getAccount().getId() != null){
            accountMapper.updateByPrimaryKeySelective(supplierVO.getAccount());
        }
        return Response.status(HttpStatus.SC_OK).build();
    }

    @POST
    @Path("/supplier/product")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    @Transactional
    public Response createProducts(String request){
        List<Product> productList = gson.fromJson(request, new TypeToken<List<Product>>() {}.getType());
        productList = objectUtil.getProductNo(productList);
        productMapper.insertBatch(productList);
        return Response.status(HttpStatus.SC_OK).build();
    }

    @PUT
    @Path("/supplier/product")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    @Transactional
    public Response updateProducts(String request){
        List<Product> productList = gson.fromJson(request, new TypeToken<List<Product>>() {}.getType());
        productList = objectUtil.getProductNo(productList);
        productList.forEach(product -> {
            if(product.getId() == null){
                productMapper.insertSelective(product);
            }else {
                productMapper.updateByPrimaryKeySelective(product);
            }
        });
        return Response.status(HttpStatus.SC_OK).build();
    }

    @DELETE
    @Path("/supplier/{supplierId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    @Transactional
    public Response deleteSupplier(@PathParam("supplierId") Integer supplierId){
        companyMapper.deleteByPrimaryKey(supplierId);
        return Response.status(HttpStatus.SC_OK).build();
    }


    @GET
    @Path("/buyer")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    @Transactional(readOnly = true)
    public Response getBuyers(){
        List<Company> buyers = companyMapper.selectBuyers();
        return Response.status(HttpStatus.SC_OK).entity(buyers).build();
    }

    @GET
    @Path("/buyer/{buyerId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    @Transactional(readOnly = true)
    public Response getBuyer(@PathParam("buyerId") Integer buyerId){
        CompanyVO buyerVO = (CompanyVO) companyMapper.selectByPrimaryKey(buyerId);
        Account account = accountMapper.selectByPrimaryKey(buyerId);
        buyerVO.setAccount(account);
        return Response.status(HttpStatus.SC_OK).entity(buyerVO).build();
    }

    @POST
    @Path("/buyer")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    @Transactional
    public Response createBuyer(String request){
        CompanyVO buyerVO = gson.fromJson(request, CompanyVO.class);
        buyerVO = objectUtil.getCompanyNo(buyerVO);
        companyMapper.insertSelective(buyerVO);
        Account account = buyerVO.getAccount();
        account.setId(buyerVO.getId());
        accountMapper.insertSelective(account);
        return Response.status(HttpStatus.SC_OK).build();
    }

    @PUT
    @Path("/buyer")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    @Transactional
    public Response updateBuyer(String request){
        CompanyVO buyeryVO = gson.fromJson(request, CompanyVO.class);
        if(buyeryVO.getId() != null){
            companyMapper.updateByPrimaryKeySelective(buyeryVO);
        }
        if(buyeryVO.getAccount() != null && buyeryVO.getAccount().getId() != null){
            accountMapper.updateByPrimaryKeySelective(buyeryVO.getAccount());
        }
        return Response.status(HttpStatus.SC_OK).build();
    }

    @DELETE
    @Path("/buyer/{buyerId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public Response deleteBuyer(@PathParam("buyerId") Integer buyerId){
        companyMapper.deleteByPrimaryKey(buyerId);
        return Response.status(HttpStatus.SC_OK).build();
    }

    @GET
    @Path("/order")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    @Transactional(readOnly = true)
    public Response getOrders(){
        List<JSONObject> orders = new ArrayList<>();
        List<Order> orderList = orderMapper.selectOrderAll();
        orderList.forEach(order -> {
            Company supplier = companyMapper.selectSupplierById(order.getSupplierId());
            Company buyer = companyMapper.selectBuyerById(order.getBuyerId());
            JSONObject orderJson = new JSONObject();
            orderJson.put("id", order.getId());
            orderJson.put("orderUuid", order.getOrderUuid());
            orderJson.put("status", order.getStatus());
            orderJson.put("supplier", supplier.getName());
            orderJson.put("buyer", buyer.getName());
            orders.add(orderJson);
        });
        return Response.status(HttpStatus.SC_OK).entity(orders).build();
    }

    @GET
    @Path("/order/{orderId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    @Transactional(readOnly = true)
    public Response getOrders(@PathParam("orderId") Integer orderId){
        Order order = orderMapper.selectByPrimaryKey(orderId);
        OrderVO orderVO = gson.fromJson(gson.toJson(order), OrderVO.class);
        Company supplier = companyMapper.selectSupplierById(orderVO.getSupplierId());
        CompanyVO supplierVO = gson.fromJson(gson.toJson(supplier), CompanyVO.class);
        supplierVO.setAccount(accountMapper.selectByPrimaryKey(supplierVO.getId()));
        orderVO.setSupplier(supplierVO);

        Company buyer = companyMapper.selectBuyerById(orderVO.getBuyerId());
        CompanyVO buyerVO = gson.fromJson(gson.toJson(buyer), CompanyVO.class);
        buyerVO.setAccount(accountMapper.selectByPrimaryKey(buyerVO.getId()));
        orderVO.setBuyer(buyerVO);

        orderVO.setBuyerAgent(buyerAgentMapper.selectByPrimaryKey(orderVO.getId()));
        orderVO.setOrderDetailList(orderDetailMapper.selectByOrderId(orderVO.getId()));
        return Response.status(HttpStatus.SC_OK).entity(orderVO).build();
    }

    @POST
    @Path("/order")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    @Transactional
    public Response getOrders(String request){
        OrderVO orderVO = gson.fromJson(request, OrderVO.class);
        orderVO = (OrderVO) objectUtil.getOrderNo(orderVO);
        orderMapper.insertSelective(orderVO);
        Integer orderId = orderVO.getId();
        BuyerAgent buyerAgent = orderVO.getBuyerAgent();
        buyerAgent.setId(orderId);
        buyerAgentMapper.insertSelective(buyerAgent);
        List<OrderDetail> orderDetailList = orderVO.getOrderDetailList();
        orderDetailList.forEach(orderDetail -> orderDetail.setOrderId(orderId));
        orderDetailMapper.insertBatch(orderDetailList);
        return Response.status(HttpStatus.SC_OK).entity(orderVO).build();
    }

    @DELETE
    @Path("/order/{orderId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    @Transactional(readOnly = true)
    public Response deleteOrders(@PathParam("orderId") Integer orderId){
        orderMapper.deleteByPrimaryKey(orderId);
        return Response.status(HttpStatus.SC_OK).build();
    }

    @GET
    @Path("/generatepdf/{orderId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public Response generatePdf(@Context HttpServletResponse response, @PathParam("orderId") Integer orderId) throws IOException {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        OrderVO orderVO = gson.fromJson(gson.toJson(order), OrderVO.class);
        Company supplier = companyMapper.selectSupplierById(orderVO.getSupplierId());
        CompanyVO supplierVO = gson.fromJson(gson.toJson(supplier), CompanyVO.class);
        supplierVO.setAccount(accountMapper.selectByPrimaryKey(supplierVO.getId()));
        orderVO.setSupplier(supplierVO);

        Company buyer = companyMapper.selectBuyerById(orderVO.getBuyerId());
        CompanyVO buyerVO = gson.fromJson(gson.toJson(buyer), CompanyVO.class);
        buyerVO.setAccount(accountMapper.selectByPrimaryKey(buyerVO.getId()));
        orderVO.setBuyer(buyerVO);

        orderVO.setBuyerAgent(buyerAgentMapper.selectByPrimaryKey(orderVO.getId()));
        orderVO.setOrderDetailList(orderDetailMapper.selectByOrderId(orderVO.getId()));

        GeneratePdf generatePdf = new GeneratePdf();
        ByteArrayOutputStream byteArrayOutputStream = generatePdf.generatePDF(orderVO);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition","inline; filename=" + "kcc-order.pdf");
        response.setContentLengthLong(byteArrayOutputStream.size());
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(byteArrayOutputStream.toByteArray());
        outputStream.flush();
        outputStream.close();
        return Response.status(HttpStatus.SC_OK).build();
    }

}
