package com.yun.software.utils;

import java.lang.reflect.ParameterizedType;

/**
 * 类转换初始化
 */
public class TUtil {
//    if(!(type instanceof ParameterizedType))，这行代码的意思是
//    如果没有实现ParameterizedType接口,即不支持泛型；
    // Type[] params = ((ParameterizedType) genType).getActualTypeArguments();这行代码的意思是，如果支持泛型，返回表示此类型实际类型参数的Type对象的数组,数组里放的都是对应类型的Class，
    // 因为可能有多个，所以是数组。
//    后面的应该你很容易就看懂了。




    public static <T> T getT(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        } catch (ClassCastException e) {
        }
        return null;
    }

    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
