package com.kcc.buyer.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kcc.buyer.common.ResponseEntity;
import com.kcc.buyer.service.BuyerService;
import com.kcc.buyer.domain.*;
import org.apache.ibatis.annotations.Delete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GET
    @Path("/supplier/productType")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public ResponseEntity getProductType(@QueryParam("supplierId") Integer supplierId){
        return buyerService.getProductType(supplierId);
    }

    @POST
    @Path("/supplier/productType")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public ResponseEntity insertProductType(String requestJson){
        if(requestJson.isEmpty()){
            return ResponseEntity.jsonError();
        }
        ProductType productType = gson.fromJson(requestJson, ProductType.class);
        ResponseEntity entity = buyerService.getProductType(productType);
        if(entity.getData() != null){
            return ResponseEntity.ok("请勿添加重复产品类型！");
        }
        return buyerService.saveProductType(productType);
    }

    @DELETE
    @Path("/supplier/productType/{productTypeId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public ResponseEntity deleteProductType(@PathParam("productTypeId") Integer productTypeId){
        return buyerService.deleteProductType(productTypeId);
    }

    @POST
    @Path("/supplier/product/productType")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity getProductsByType(String requestJson){
        Product product = gson.fromJson(requestJson, Product.class);
        String productType = product.getType();
        Integer supplierId = product.getCompanyId();
        ResponseEntity productsByType = null;
        if(supplierId != null && !productType.isEmpty()){
            productsByType = buyerService.getProductsByType(supplierId, productType);
        }
        return productsByType;
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
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
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
        return buyerService.getOrderAll(pageNum, pageSize);
    }

    @GET
    @Path("/order/{orderId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public ResponseEntity getOrderDetails(@PathParam("orderId") Integer orderId){
        return buyerService.getOrderDetails(orderId);
    }

    @GET
    @Path("/order/conditions/find")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public ResponseEntity getOrderConditions(@QueryParam("pageNum") Integer pageNum,
                                             @QueryParam("pageSize") Integer pageSize,
                                             @QueryParam("orderUuid") Integer orderUuid,
                                             @QueryParam("buyerName") String buyerName,
                                             @QueryParam("supplierName") String supplierName){
        if(orderUuid != null  && orderUuid != 0){
            return buyerService.getOrderConditions(pageNum, pageSize, orderUuid);
        }else if (!buyerName.isEmpty() && !supplierName.isEmpty()){
            return null;
        }else if(!buyerName.isEmpty()){
            return null;
        }else if(!supplierName.isEmpty()){
            return null;
        }
        return null;
    }

    @DELETE
    @Path("/order/{orderId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public ResponseEntity deleteOrder(@PathParam("orderId") Integer orderId){
        return buyerService.deleteOrder(orderId);
    }

    //PDF file generate
    @GET
    @Path("/generatepdf/{orderId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public ResponseEntity generatePdf(@Context HttpServletResponse response, @PathParam("orderId") Integer orderId) throws IOException {
        buyerService.generatePdf(response, orderId);
        return ResponseEntity.ok();
    }

    @POST
    @Path("/supplier/manufacturer")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public ResponseEntity insertManufacturer(String requestJson){
        Manufacturer manufacturer = gson.fromJson(requestJson, Manufacturer.class);
        return buyerService.insertManufacturer(manufacturer);
    }

    @DELETE
    @Path("/supplier/manufacturer/{manufacturerId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public ResponseEntity deleteById(@PathParam("manufacturerId") Integer manufacturerId){
        return buyerService.deleteManufacturerById(manufacturerId);
    }

    @GET
    @Path("/supplier/manufacturer/{manufacturerId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public ResponseEntity selectManufacturerBySupplierId(@PathParam("manufacturerId") Integer manufacturerId){
        return buyerService.selectManufacturerBySupplierId(manufacturerId);
    }

}
