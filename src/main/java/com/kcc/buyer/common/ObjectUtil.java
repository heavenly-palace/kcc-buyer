package com.kcc.buyer.common;

import java.lang.reflect.Field;

public class ObjectUtil {

    public static boolean checkObjFieldIsNull(Object obj) throws IllegalAccessException {

        boolean flag = false;
        for(Field f : obj.getClass().getDeclaredFields()){
            f.setAccessible(true);
            if(f.get(obj) != null){
                flag = true;
                return flag;
            }
        }
        return flag;
    }
}
