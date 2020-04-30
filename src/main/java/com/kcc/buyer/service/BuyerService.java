package com.kcc.buyer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.kcc.buyer.common.ResponseEntity;
import com.kcc.buyer.domain.*;
import com.kcc.buyer.mapper.*;
import com.kcc.buyer.util.GeneratePdf;
import com.kcc.buyer.util.ObjectUtil;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service
public class BuyerService {

    @Autowired
    private ObjectUtil objectUtil;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private ProductMapper productMapper;

    private static final Logger logger = LoggerFactory.getLogger(BuyerService.class);

    @Transactional(readOnly = true)
    public ResponseEntity getSupplier(Integer supplierId){
        Company supplier = companyMapper.selectByPrimaryKey(supplierId);
        if(supplier != null){
            Account account = accountMapper.selectBySupplierId(supplierId);
            supplier.setAccount(account);

            List<Product> productList = productMapper.selectBySupplier(supplierId);
            supplier.setProductList(productList);
        }
        return ResponseEntity.ok(supplier);
    }

    @Transactional
    public void createSupplier(Company supplier){
        try {
            if(!ObjectUtils.isEmpty(supplier)){
                supplier = objectUtil.getCompanyNo(supplier);
                companyMapper.insertSelective(supplier);

                Account account = supplier.getAccount();
                account.setCompanyId(supplier.getId());
                accountMapper.insertSelective(account);
            }
        } catch (Exception e){
            logger.debug("create supplier failure: " + e.getMessage());
            throw new RuntimeException("create supplier failure: " + e.getMessage());
        }
    }

    @Transactional
    public void createBuyer(Company buyer){
        try {
            if(!ObjectUtils.isEmpty(buyer)){
                buyer = objectUtil.getCompanyNo(buyer);
                companyMapper.insertSelective(buyer);

                Account account = buyer.getAccount();
                account.setCompanyId(buyer.getId());
                accountMapper.insertSelective(account);
            }
        } catch (Exception e){
            logger.debug("create buyer failure: " + e.getMessage());
            throw new RuntimeException("create buyer failure: " + e.getMessage());
        }
    }

    @Transactional
    public void updateBuyer(Company buyer){
        try {
            if(!ObjectUtils.isEmpty(buyer))
                if(buyer.getId() != null){
                    companyMapper.updateByPrimaryKeySelective(buyer);
                Account account = buyer.getAccount();
                if(account != null && account.getId() != null)
                    accountMapper.updateByPrimaryKeySelective(account);
            }
        } catch (Exception e){
            logger.debug("create buyer failure: " + e.getMessage());
            throw new RuntimeException("create buyer failure: " + e.getMessage());
        }
    }

    @Transactional
    public void updateSupplier(Company supplier){
        try {
            if(!ObjectUtils.isEmpty(supplier)){
                if(supplier.getId() != null)
                    companyMapper.updateByPrimaryKeySelective(supplier);
                Account account = supplier.getAccount();
                if(account != null && account.getId() != null)
                    accountMapper.updateByPrimaryKeySelective(account);
            }
        } catch (Exception e){
            logger.debug("update supplier failure, message: " + e.getMessage());
            throw new RuntimeException("update supplier failure, message: " + e.getMessage());
        }
    }

    @Transactional
    public void deleteProduct(Integer productId){
        productMapper.deleteByPrimaryKey(productId);
    }

    @Transactional
    public void updateProductList(Product product){
        productMapper.updateByPrimaryKeySelective(product);
    }

    @Transactional
    public void createProductList(List<Product> productList){
        List<Product> products = objectUtil.getProductNo(productList);
        productMapper.insertProductBatch(products);
    }

    @Transactional
    public void deleteCompany(Integer companyId){
        companyMapper.deleteByPrimaryKey(companyId);
    }

    @Transactional(readOnly = true)
    public ResponseEntity getProducts(Integer supplierId){
        List<Product> productList = productMapper.selectBySupplier(supplierId);
        return ResponseEntity.ok(productList);
    }

    @Transactional(readOnly = true)
    public ResponseEntity getBuyerAll(){
        List<Company> companies = companyMapper.selectByBuyerAll();
        return ResponseEntity.ok(companies);
    }

    @Transactional(readOnly = true)
    public ResponseEntity getSupplierAll(){
        List<Company> companies = companyMapper.selectBySupplierAll();
        return ResponseEntity.ok(companies);
    }

    @Transactional(readOnly = true)
    public ResponseEntity getBuyer(Integer buyerId){
        Company supplier = companyMapper.selectByPrimaryKey(buyerId);
        if(supplier != null){
            Account account = accountMapper.selectBySupplierId(buyerId);
            supplier.setAccount(account);
        }
        return ResponseEntity.ok(supplier);
    }

    @Transactional
    public ResponseEntity createOrder(Order orderSource){
        Order order = objectUtil.getOrderNo(orderSource);
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
        orderDetailMapper.insertBatch(orderDetailList);
        return ResponseEntity.ok();
    }

    @Transactional(readOnly = true)
    public ResponseEntity getOrderAll(Integer pageNum, Integer pageSize) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectOrderAll();
        List<Map<Object,Object>> maps = new ArrayList<>();
        if(!ObjectUtils.isEmpty(orderList)){
            for (Order order:orderList
            ) {
                Map<Object,Object> orderMap = new HashMap<>();
                orderMap.put("id", order.getId());
                orderMap.put("orderNo", order.getOrderUuid());
                orderMap.put("status", order.getStatus());
                Integer orderId = order.getId();
                List<CompanyInfo> companyInfoList = companyInfoMapper.selectOrderNameByOrderId(orderId);
                companyInfoList.forEach(companyInfo -> {
                    if (companyInfo.getType().equals(1)) {
                        orderMap.put("supplier", companyInfo.getName());
                    } else {
                        orderMap.put("buyer", companyInfo.getName());
                    }
                });
                maps.add(orderMap);
            }
        }
        return ResponseEntity.ok(maps);
    }

    @Transactional(readOnly = true)
    public ResponseEntity getOrderDetails(Integer orderId){
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if(!ObjectUtils.isEmpty(order)){
            List<CompanyInfo> companyInfoList = companyInfoMapper.selectByOrderId(orderId);
            order.setCompanyInfoList(companyInfoList);
            companyInfoList.forEach(companyInfo -> {
                AccountInfo accountInfo = accountInfoMapper.selectByCompanyInfoId(companyInfo.getId());
                companyInfo.setAccountInfo(accountInfo);
            });
            List<OrderDetail> orderDetails = orderDetailMapper.selectByOrderId(orderId);
            order.setOrderDetailList(orderDetails);
        }
        return  ResponseEntity.ok(order);
    }

    public ResponseEntity deleteOrder(Integer orderId){
        orderMapper.deleteByPrimaryKey(orderId);
        return ResponseEntity.ok();
    }

    public void generatePdf(HttpServletResponse response, Integer orderId) throws IOException {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if(order != null){
            List<CompanyInfo> companyInfoList = companyInfoMapper.selectByOrderId(orderId);
            order.setCompanyInfoList(companyInfoList);
            companyInfoList.forEach(companyInfo -> {
                AccountInfo accountInfo = accountInfoMapper.selectByCompanyInfoId(companyInfo.getId());
                companyInfo.setAccountInfo(accountInfo);
            });
            List<OrderDetail> orderDetails = orderDetailMapper.selectByOrderId(orderId);
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
    }

}
