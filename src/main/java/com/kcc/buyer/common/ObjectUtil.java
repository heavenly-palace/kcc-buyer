package com.kcc.buyer.common;

import com.kcc.buyer.domain.Company;
import com.kcc.buyer.mapper.CompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ObjectUtil {

    @Autowired
    private CompanyMapper companyMapper;

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
    public String getCurrentNo(Company company) throws Exception {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
        String date = f.format(new Date());
        String lastNo = companyMapper.selectCompanyByDateLast(company.getType(), date);
        if(lastNo.isEmpty()){
            return date + "001";
        }else {
            int intNumber = Integer.parseInt(lastNo.substring(8));
            String currentNo = String.valueOf(++intNumber);
            if(currentNo.length() > 3){
                throw new Exception("今天添加已达上限！请明天再添加");
            }else {
                return date + currentNo;
            }
        }
    }
}
