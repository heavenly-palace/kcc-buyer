package com.kcc.buyer.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kcc.buyer.common.ObjectUtil;
import com.kcc.buyer.common.ResponseEntity;
import com.kcc.buyer.domain.*;
import com.kcc.buyer.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

//@Singleton
@Component
@Path("/api")
public class BuyerController {

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ObjectUtil objectUtil;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Autowired
    private Gson gson ;

    private static final Logger logger = LoggerFactory.getLogger(BuyerController.class);

    @POST
    @Path("/company/supplier")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    @Transactional
    public ResponseEntity createSupplier(String request){
        try {
            Company supplier = gson.fromJson(request,new TypeToken<Company>() {}.getType());
            if(!ObjectUtils.isEmpty(supplier)){
                String currentNo = objectUtil.getCompanyNo(supplier);
                supplier.setNo(currentNo);
                companyMapper.insertSelective(supplier);

                Account account = supplier.getAccount();
                account.setCompanyId(supplier.getId());
                accountMapper.insertSelective(account);

                List<Product> productList = supplier.getProductList();
                productList = objectUtil.getProductNo(productList);
                productList.forEach(product -> product.setCompanyId(supplier.getId()));
                productMapper.insert(productList);
            }
        } catch (Exception e){
            logger.debug("create supplier failure: " + e.getMessage());
            throw new RuntimeException("create supplier failure: " + e.getMessage());
        }
        return ResponseEntity.ok();
    }

    @PUT
    @Path("/company/supplier")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    @Transactional
    public ResponseEntity updateSupplier(String request){
        try {
            Company supplier = gson.fromJson(request,new TypeToken<Company>() {}.getType());
            if(!ObjectUtils.isEmpty(supplier)){
                if(supplier.getId() != null)
                    companyMapper.updateByPrimaryKeySelective(supplier);

                Account account = supplier.getAccount();
                if(account != null && account.getId() != null)
                    accountMapper.updateByPrimaryKeySelective(account);

                List<Product> productList = supplier.getProductList();
                if(!ObjectUtils.isEmpty(productList)){
                    for (Product product:productList
                    ) {
                        if(product.getId() == null) productMapper.insertSelective(product);
                        else productMapper.updateByPrimaryKeySelective(product);
                    }
                }
            }
        } catch (Exception e){
            logger.debug("update supplier failure, message: " + e.getMessage());
            throw new RuntimeException("update supplier failure, message: " + e.getMessage());
        }
        return ResponseEntity.ok();
    }

    @POST
    @Path("/company/buyer")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    @Transactional
    public ResponseEntity createBuyer(String request){
        try {
            Company supplier = gson.fromJson(request,new TypeToken<Company>() {}.getType());
            if(!ObjectUtils.isEmpty(supplier)){
                String currentNo = objectUtil.getCompanyNo(supplier);
                supplier.setNo(currentNo);
                companyMapper.insertSelective(supplier);

                Account account = supplier.getAccount();
                account.setCompanyId(supplier.getId());
                accountMapper.insertSelective(account);
            }
        } catch (Exception e){
            logger.debug("create buyer failure: " + e.getMessage());
            throw new RuntimeException("create buyer failure: " + e.getMessage());
        }
        return ResponseEntity.ok();
    }

    @GET
    @Path("/company/{companyType}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    @Transactional(readOnly = true)
    public ResponseEntity getCompany(@PathParam("companyType") String companyType){
        List<Company> companies = null;
        if(companyType.equals("suppliers")){
            companies = companyMapper.selectSelective(new Company(1));
            return new ResponseEntity(200, "success", companies);
        }else if(companyType.equals("buyers")){
            companies = companyMapper.selectSelective(new Company(2));
        }
        return new ResponseEntity(200, "success", companies);
    }

    @GET
    @Path("/products/{supplierId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    @Transactional(readOnly = true)
    public ResponseEntity getProducts(@PathParam("supplierId") Integer supplierId){
        Product product = new Product();
        product.setCompanyId(supplierId);
        List<Product> productList = productMapper.selectBySelective(product);
        return new ResponseEntity(200, "success", productList);
    }

    @POST
    @Path("/order")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    @Transactional(readOnly = true)
    public ResponseEntity createOrder(String request){
        Order order = gson.fromJson(request,new TypeToken<Order>() {}.getType());
        if(!ObjectUtils.isEmpty(order)){
            String orderUuid = UUID.randomUUID().toString();
            order.setOrderUuid(orderUuid);
            orderMapper.insertSelective(order);

            List<CompanyInfo> companyInfoList = order.getCompanyInfoList();
            companyInfoList.forEach(companyInfo -> {
                companyInfo.setOrderId(order.getId());
                companyInfoMapper.insertSelective(companyInfo);

                AccountInfo accountInfo = companyInfo.getAccountInfo();
                accountInfo.setCompanyInfoId(companyInfo.getId());
                accountInfoMapper.insertSelective(accountInfo);
            });

            List<OrderDetail> orderDetailList = order.getOrderDetailList();
            orderDetailList.forEach(orderDetail -> orderDetail.setOrderId(order.getId()));
            orderDetailMapper.insert(orderDetailList);
        }
        return ResponseEntity.ok();
    }

    public ResponseEntity getOrder(){


        return null;
    }

}
