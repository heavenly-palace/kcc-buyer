package com.kcc.buyer.common;

import com.kcc.buyer.domain.Company;
import com.kcc.buyer.domain.Product;
import com.kcc.buyer.mapper.CompanyMapper;
import com.kcc.buyer.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ObjectUtil {

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private ProductMapper productMapper;

    public boolean checkObjFieldIsNull(Object obj) {

        boolean flag = false;
        for(Field f : obj.getClass().getDeclaredFields()){
            f.setAccessible(true);
            try {
                if(f.get(obj) != null){
                    flag = true;
                    return flag;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }
    public String getCompanyNo(Company company){
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
        String date = f.format(new Date());
        String lastNo = companyMapper.selectCurrentLastNo(company.getType(), date);;
        if(lastNo == null){
            return date + "001";
        }else {
            long intNumber = Long.parseLong(lastNo);
            return String.valueOf(++intNumber);
        }
    }

    public List<Product> getProductNo(List<Product> productList) {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
        String date = f.format(new Date());
        String lastNo = productMapper.selectCurrentLastNo(date);
        for (Product product:productList
        ) {
            if(lastNo == null){
                lastNo = date + "001";
            }else {
                long intNumber = Long.parseLong(lastNo);
                lastNo = String.valueOf(++intNumber);
            }
            product.setNo(lastNo);
        }
        return productList;
    }
}
