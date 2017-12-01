package com.titan.util;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanyw on 2017/11/22/022.
 * 实体类工具
 */

public class JavaBeanUtil {
    /**
     * 实体类转list
     * @param obj
     * @return
     */
    public static List<String[]> ConvertObjToList(Object obj) {
        List<String[]> list = new ArrayList<>();
        if (obj == null)
            return null;
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                String[] array = new String[2];
                try {
                    if (field.getName().equals("serialVersionUID")||field.getName().equals("$change")){
                        continue;
                    }
                    Field f = obj.getClass().getDeclaredField(field.getName());
                    f.setAccessible(true);
                    Object o = f.get(obj);
                    array[0] = field.getName();
                    if (o==null||o.toString().trim().equals("")){
                        array[1] = "无";
                    }else {
                        array[1] = String.valueOf(o);
                    }
                    list.add(array);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return list;
    }
}
