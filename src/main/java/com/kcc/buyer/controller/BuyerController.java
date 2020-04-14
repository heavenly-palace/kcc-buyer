package com.kcc.buyer.controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.kcc.buyer.common.ObjectUtil;
import com.kcc.buyer.common.ResponseEntity;
import com.kcc.buyer.domain.Account;
import com.kcc.buyer.domain.Company;
import com.kcc.buyer.domain.Product;
import com.kcc.buyer.mapper.AccountMapper;
import com.kcc.buyer.mapper.CompanyMapper;
import com.kcc.buyer.mapper.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Component;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//@Singleton
@Component
@Path("/api")
public class BuyerController {

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private ObjectUtil objectUtil;

    private static final Logger logger = LoggerFactory.getLogger(BuyerController.class);

    @POST
    @Path("/company/supplier")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    @Transactional
    public ResponseEntity createCompany(String request){
        Gson gson = new Gson();
        try {
            Company supplier = gson.fromJson(request,new TypeToken<Company>() {}.getType());
            if(objectUtil.checkObjFieldIsNull(supplier)){
                if(supplier.getId() == null) {
                    SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
                    Integer date = Integer.getInteger(f.format(new Date()));
                }
                companyMapper.insertOrUpdateCompany(supplier);
            }
            if(supplier.getAccount() != null){
                Account account = supplier.getAccount();
                Map<String, Object> map = new ConcurrentHashMap<>();
                map.put("companyId", supplier.getId());
                map.put("account",account);
                accountMapper.insertOrUpdateAccount(map);
            }
            if(!supplier.getProducts().isEmpty()){
                List<Product> products = supplier.getProducts();
                Map<String, Object> map = new ConcurrentHashMap<>();
                map.put("companyId", supplier.getId());
                map.put("products", products);
                productMapper.insertOrUpdateProducts(map);
            }
        } catch (JsonSyntaxException e) {
            logger.debug("supplier json does not match: " + e.getMessage());
            return new ResponseEntity(400,"supplier json does not match");
        } catch (Exception e){
            logger.debug("create supplier failure: " + e.getMessage());
            return new ResponseEntity(500, "create supplier failure");
        }
        return ResponseEntity.ok();
    }

    @GET
    @Path("/test")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    @Transactional(readOnly = true)
    public ResponseEntity test(){
        Company company = new Company();
        company.setType(1);
        try {
            objectUtil.getCurrentNo(company);
        } catch (Exception e) {
            return new ResponseEntity(403,e.getMessage());
        }
        return ResponseEntity.ok();
    }

    @POST
    @Path("/company/buyer")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    @Transactional
    public ResponseEntity createBuyer(String request){
        Gson gson = new Gson();
        try {
            Company buyer = gson.fromJson(request,new TypeToken<Company>() {}.getType());
            if(objectUtil.checkObjFieldIsNull(buyer)){
                companyMapper.insertOrUpdateCompany(buyer);
            }
            if(buyer.getAccount() != null){
                Account account = buyer.getAccount();
                Map<String, Object> map = new ConcurrentHashMap<>();
                map.put("companyId", buyer.getId());
                map.put("account", account);
                accountMapper.insertOrUpdateAccount(map);
            }
        } catch (JsonSyntaxException e) {
            logger.debug("buyer json does not match: " + e.getMessage());
            return new ResponseEntity(400," buyer json does not match");
        } catch (Exception e){
            logger.debug("create buyer failure: " + e.getMessage());
            return new ResponseEntity(500, "create buyer failure");
        }
        return ResponseEntity.ok();
    }

    @GET
    @Path("/company/{companyType}")
    @Transactional(readOnly = true)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public ResponseEntity getCompanies(@PathParam("companyType") String companyType){
        List<Company> companies = null;
        if(companyType.equals("supplier")){
            companies = companyMapper.selectByCompanyType(1);
        }else if(companyType.equals("buyer")){
            companies = companyMapper.selectByCompanyType(2);
        }
        return new ResponseEntity(200, "success", companies);
    }

    @GET
    @Path("/products/{companyId}/{status}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    @Transactional(readOnly = true)
    public ResponseEntity getProductsByStatus(@PathParam("companyId") Integer companyId, @PathParam("status") Integer status){
        List<Product> products;
        if (status == 1 || status == 2){
            products = productMapper.selectByCompanyId(companyId,status);
        }else {
            products = productMapper.selectByCompanyId(companyId,null);
        }
        return new ResponseEntity(200, "success", products);
    }

    @DELETE
    @Path("/company/{companyId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    @Transactional
    public ResponseEntity deleteCompany(@PathParam("companyId") Integer companyId){
        companyMapper.deleteByPrimaryKey(companyId);
        logger.debug("delete the company with id " + companyId);
        return ResponseEntity.ok();
    }

    @DELETE
    @Path("/product/{productId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    @Transactional
    public ResponseEntity deleteProduct(@PathParam("productId") Integer productId){
        productMapper.deleteProductById(productId);
        logger.debug("delete the product with id " + productId);
        return ResponseEntity.ok();
    }



}
