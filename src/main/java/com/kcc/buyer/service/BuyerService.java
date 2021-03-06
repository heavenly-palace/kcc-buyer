package com.kcc.buyer.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kcc.buyer.common.ResponseEntity;
import com.kcc.buyer.domain.*;
import com.kcc.buyer.mapper.*;
import com.kcc.buyer.util.GeneratePdf;
import com.kcc.buyer.util.ObjectUtil;
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

    @Autowired
    private PayPlanMapper payPlanMapper;

    @Autowired
    private ManufacturerMapper manufacturerMapper;

    @Autowired
    private UnitPackMapper unitPackMapper;

    @Autowired
    private PastCommentMapper pastCommentMapper;

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
    public ResponseEntity getProductType(Integer supplierId){
        List<ProductType> strings = productMapper.selectProductType(supplierId);
        return ResponseEntity.ok(strings);
    }

    @Transactional
    public ResponseEntity deleteProductType(Integer supplierId){
        productMapper.deleteProductTypeById(supplierId);
        return ResponseEntity.ok();
    }

    @Transactional
    public ResponseEntity getProductType(ProductType productType){
        ProductType byBean = productMapper.selectProductTypeByBean(productType);
        return ResponseEntity.ok(byBean);
    }

    @Transactional
    public ResponseEntity saveProductType(ProductType productType){
        productMapper.insertProductType(productType);
        return ResponseEntity.ok();
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
        productMapper.deleteByPrimaryKeyStatus(productId);
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
    public ResponseEntity getProductsByType(Integer supplierId, String productType){
        List<Product> productList = productMapper.selectByProductType(supplierId, productType);
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

        List<PayPlan> payPlanList = order.getPayPlanList();
        payPlanList.forEach(payPlan -> payPlan.setOrderId(order.getId()));
        payPlanMapper.insertPayPlanBatch(payPlanList);
        return ResponseEntity.ok();
    }

    @Transactional(readOnly = true)
    public ResponseEntity getOrderAll(Integer pageNum, Integer pageSize) {
        Page<Object> page = PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectOrderAll();
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("totalPage", page.getTotal());
        dataMap.put("pageNum", pageNum);
        getOrderListMap(orderList, dataMap);
        return ResponseEntity.ok(dataMap);
    }

    @Transactional(readOnly = true)
    public ResponseEntity getOrderConditions(Integer pageNum, Integer pageSize, Integer orderUuid) {
        Page<Object> page = PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectByOrderUuid(orderUuid);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("totalPage", page.getTotal());
        dataMap.put("pageNum", pageNum);
        getOrderListMap(orderList, dataMap);
        return ResponseEntity.ok(dataMap);
    }

    private void getOrderListMap(List<Order> orderList, Map<String,Object> dataMap){
        if(!ObjectUtils.isEmpty(orderList)){
            List<Map<String,Object>> orders = new ArrayList<>();
            for (Order order:orderList
            ) {
                Map<String,Object> orderMap = new HashMap<>();
                orderMap.put("id", order.getId());
                orderMap.put("orderNo", order.getOrderUuid());
                orderMap.put("status", order.getStatus());
                orderMap.put("currentStatus", order.getCurrentStatus());
                Integer orderId = order.getId();
                List<CompanyInfo> companyInfoList = companyInfoMapper.selectOrderNameByOrderId(orderId);
                companyInfoList.forEach(companyInfo -> {
                    if (companyInfo.getType().equals(1)) {
                        orderMap.put("supplier", companyInfo.getName());
                    } else {
                        orderMap.put("buyer", companyInfo.getName());
                    }
                });
                orders.add(orderMap);
            }
            dataMap.put("orders", orders);
        }
        //return dataMap;
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

            List<PayPlan> payPlans = payPlanMapper.selectPayPlanByOrderId(orderId);
            order.setPayPlanList(payPlans);
        }
        return  ResponseEntity.ok(order);
    }

    public ResponseEntity deleteOrder(Integer orderId){
        orderMapper.deleteByPrimaryKeySelective(orderId);
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

            List<PayPlan> payPlans = payPlanMapper.selectPayPlanByOrderId(orderId);
            order.setPayPlanList(payPlans);
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


    /***
     * 厂商相关接口
     * @param manufacturer 厂商对象
     * @return ResponseEntity
     */
    @Transactional
    public ResponseEntity insertManufacturer(Manufacturer manufacturer){
        if(manufacturer == null){
            return ResponseEntity.jsonError("this object is null!");
        }else {
            manufacturerMapper.insertManufacturer(manufacturer);
            return ResponseEntity.ok();
        }
    }

    @Transactional
    public ResponseEntity deleteManufacturerById(Integer manufacturerId){
        if(manufacturerId > 0){
            manufacturerMapper.deleteById(manufacturerId);
            return ResponseEntity.ok();
        }else {
            return ResponseEntity.jsonError("id is null!");
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity selectManufacturerBySupplierId(Integer supplierId){
        if(supplierId > 0){
            List<Manufacturer> manufacturers = manufacturerMapper.selectBySupplierId(supplierId);
            return ResponseEntity.ok(manufacturers);
        }else {
            return ResponseEntity.jsonError("supplierId is null!");
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity selectUnitPack(Integer supplierId, Integer flag){
        List<UnitPack> unitPacks;
        if(supplierId > 0 && flag > 0){
            unitPacks = unitPackMapper.selectUnitPack(new UnitPack(supplierId, flag));
        }else {
            return ResponseEntity.jsonError("object is null!");
        }
        return ResponseEntity.ok(unitPacks);
    }

    @Transactional
    public ResponseEntity insertUnitPack(UnitPack unitPack){
        if(unitPack != null){
            unitPackMapper.insertUnitPack(unitPack);
        }else {
            return ResponseEntity.jsonError("object is null!");
        }
        return ResponseEntity.ok();
    }

    public ResponseEntity deleteUnitOrPack(Integer id){
        if(id > 0){
            unitPackMapper.deleteUnitOrPack(id);
            return ResponseEntity.ok();
        }
        return ResponseEntity.jsonError("id is null!");
    }

    @Transactional
    public ResponseEntity updateOrderStatus(PastComment pastComment){
        if(!ObjectUtils.isEmpty(pastComment)){

            Order order = new Order();
            order.setId(pastComment.getOrderId());
            order.setCurrentStatus(pastComment.getCurrentStatus());

            orderMapper.updateByPrimaryKeySelective(order);

            pastCommentMapper.insertPastComment(pastComment);
            return ResponseEntity.ok();
        }
        throw new RuntimeException("update error!");
    }

    public ResponseEntity selectPastCommentByOrderId(Integer orderId){
        if(orderId > 0){
            Map<String, Object> objectMap = new HashMap<>();
            List<PastComment> pastComments = pastCommentMapper.selectPastCommentByOrderId(new PastComment(orderId));
            Order order = orderMapper.selectByPrimaryKey(orderId);
            objectMap.put("pastComments",pastComments);
            objectMap.put("currentStatus", order.getCurrentStatus());
            return ResponseEntity.ok(objectMap);
        }
        return ResponseEntity.jsonError("return object is null");
    }
}
