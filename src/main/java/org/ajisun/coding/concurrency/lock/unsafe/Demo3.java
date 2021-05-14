package org.ajisun.coding.concurrency.lock.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Copyright (c) 2020. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.unsafe
 * @Date: 2020/9/9
 * @author: ajisun
 * @Email: Ajisun
 */
public class Demo3 {

    static Unsafe unsafe;
    //静态属性
    private static Object v1;
    //实例属性
    private Object v2;

    static {
//        获取unsafe对象
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws NoSuchFieldException {
        Field v1 = Demo3.class.getDeclaredField("v1");
        Field v2 = Demo3.class.getDeclaredField("v2");

        System.out.println(unsafe.staticFieldOffset(v1));
        System.out.println(unsafe.objectFieldOffset(v2));
        System.out.println(unsafe.staticFieldBase(v1)==Demo3.class);
    }


}
