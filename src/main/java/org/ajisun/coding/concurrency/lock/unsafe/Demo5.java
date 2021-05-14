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
public class Demo5 {

    static Unsafe unsafe;


    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class C1{
        private String name;

        private C1(){
            System.out.println("C1 default constructor!");
        }

        private C1(String name){
            this.name = name;
            System.out.println("C1 有参 constructor!");
        }
    }

    public static void main(String[] args) throws InstantiationException {
        System.out.println(unsafe.allocateInstance(C1.class));
    }

}
