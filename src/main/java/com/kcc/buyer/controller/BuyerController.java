package com.kcc.buyer.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kcc.buyer.common.ResponseEntity;
import com.kcc.buyer.service.BuyerService;
import com.kcc.buyer.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Singleton
@Component
@Path("/api")
public class BuyerController {

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private Gson gson ;

    private static final Logger logger = LoggerFactory.getLogger(BuyerController.class);

    @GET
    @Path("/company/supplier/{supplierId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public ResponseEntity getSupplier(@PathParam("supplierId") Integer supplierId){
        return buyerService.getSupplier(supplierId);
    }

    @GET
    @Path("/company/supplier")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public ResponseEntity getSupplierAll(){
        return buyerService.getSupplierAll();
    }

    @POST
    @Path("/company/supplier")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public ResponseEntity createSupplier(String request){
        Company supplier = gson.fromJson(request,new TypeToken<Company>() {}.getType());
        buyerService.createSupplier(supplier);
        return ResponseEntity.ok();
    }

    @PUT
    @Path("/company/supplier")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public ResponseEntity updateSupplier(String request){
        Company supplier = gson.fromJson(request,new TypeToken<Company>() {}.getType());
        buyerService.updateSupplier(supplier);
        return ResponseEntity.ok();
    }

    @GET
    @Path("/company/buyer/{buyerId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public ResponseEntity getBuyer(@PathParam("buyerId") Integer buyerId){
        return buyerService.getBuyer(buyerId);
    }

    @GET
    @Path("/company/buyer")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public ResponseEntity getBuyerAll(){
        return buyerService.getBuyerAll();
    }

    @POST
    @Path("/company/buyer")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public ResponseEntity createBuyer(String request){
        Company buyer = gson.fromJson(request,new TypeToken<Company>() {}.getType());
        buyerService.createBuyer(buyer);
        return ResponseEntity.ok();
    }

    @PUT
    @Path("/company/buyer")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public ResponseEntity updateBuyer(String request){
        Company buyer = gson.fromJson(request,new TypeToken<Company>() {}.getType());
        buyerService.updateBuyer(buyer);
        return ResponseEntity.ok();
    }

    @DELETE
    @Path("/company/{companyId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public ResponseEntity deleteCompany(@PathParam("companyId") Integer companyId){
        buyerService.deleteCompany(companyId);
        return ResponseEntity.ok();
    }

    @GET
    @Path("/supplier/product/{supplierId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public ResponseEntity getProducts(@PathParam("supplierId") Integer supplierId){
        return buyerService.getProducts(supplierId);
    }

    @POST
    @Path("/supplier/product")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public ResponseEntity createProductList(String requestJson){
        List<Product> productList = gson.fromJson(requestJson,new TypeToken<List<Product>>() {}.getType());
        if(!ObjectUtils.isEmpty(productList)){
            buyerService.createProductList(productList);
        }else {
            return ResponseEntity.jsonError();
        }
        return ResponseEntity.ok();
    }

    @PUT
    @Path("/supplier/product")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public ResponseEntity updateProduct(String requestJson){
        Product product = gson.fromJson(requestJson,new TypeToken<Product>() {}.getType());
        buyerService.updateProductList(product);
        return ResponseEntity.ok();
    }

    @DELETE
    @Path("/supplier/product/{productId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public ResponseEntity deleteProduct( @PathParam("productId") Integer productId){
        buyerService.deleteProduct(productId);
        return ResponseEntity.ok();
    }

    //create order
    @POST
    @Path("/order")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public ResponseEntity createOrder(String request){
        Order order = gson.fromJson(request,new TypeToken<Order>() {}.getType());
        if(!ObjectUtils.isEmpty(order)){
            return buyerService.createOrder(order);
        }
        return null;
    }

    @GET
    @Path("/order")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public ResponseEntity getOrderAll(@QueryParam("pageNum") Integer pageNum, @QueryParam("pageSize") Integer pageSize){
        try {
            return buyerService.getOrderAll(pageNum, pageSize);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GET
    @Path("/order/{orderId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public ResponseEntity getOrderDetails(@PathParam("orderId") Integer orderId){
        return buyerService.getOrderDetails(orderId);
    }

    //PDF file generate
    @GET
    @Path("/generatepdf/{orderId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public ResponseEntity generatePdf(@Context HttpServletResponse response, @PathParam("orderId") Integer orderId) throws IOException {
        buyerService.generatePdf(response, orderId);
        return ResponseEntity.ok();
    }

}
