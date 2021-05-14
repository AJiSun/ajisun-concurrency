package org.ajisun.coding.concurrency.lock.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Copyright (c) 2020. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.unsafe
 * @Date: 2020/9/5
 * @author: ajisun
 * @Email: Ajisun
 */
public class Demo1 {

    static Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe)field.get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        System.out.println(unsafe);
    }

}
