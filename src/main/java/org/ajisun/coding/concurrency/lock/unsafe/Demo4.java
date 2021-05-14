package org.ajisun.coding.concurrency.lock.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 *
 * 代码中C1未被初始化过，所以unsafe.shouldBeInitialized(C1.class)返回true，然后调用unsafe.ensureClassInitialized(C1.class)进行初始化。
 * 代码中执行C2.count会触发C2进行初始化，所以shouldBeInitialized(C2.class)返回false
 *
 * @Copyright (c) 2020. Ajisun. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock.unsafe
 * @Date: 2020/9/9
 * @author: ajisun
 * @Email: Ajisun
 */
public class Demo4 {

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
        private static int count;
        static {
            count = 10;
            System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + "，C1 static init.");
        }
    }
    static class C2{
        private static int count;

        static {
            count = 11;
            System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + "，C2 static init.");
        }
    }

    public static void main(String[] args) {
        //判断C1类是需要需要初始化，如果已经初始化了，会返回false，如果此类没有被初始化过，返回true
        if (unsafe.shouldBeInitialized(C1.class)){
            System.out.println("C1需要进行初始化");
            //对C1进行初始化
            unsafe.ensureClassInitialized(C1.class);
        }
        System.out.println(C2.count);
        System.out.println(unsafe.shouldBeInitialized(C2.class));
    }

}
