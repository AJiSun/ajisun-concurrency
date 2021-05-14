package org.ajisun.coding.concurrency.lock;

/**
 * @Copyright (c) 2020. Ajisun Enterprise Solution Company. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock
 * @Date: 2020/7/9
 * @author: ajisun
 *
 */
public class UserGuardThreadDemo {

    /**
     * java线程分为用户线程和守护线程，线程的daemon属性为true表示是守护线程，false表示是用户线程
     * 程序只有守护线程时，系统会自动退出
     * 当程序中所有的用户线程执行完毕之后，不管守护线程是否结束，系统都会自动退出。
     *
     */

    public static class T1 extends Thread{
        public T1(String name){
            super(name);
        }


        @Override
        public void run(){
            System.out.println(this.getName()+"开始执行,"+(this.isDaemon()?"我是守护线程":"我是用户线程"));
            while (true){
                ;
            }

        }

    }

    public static void main(String[] args) {
        T1 t1 = new T1("线程1");
        /**
         * daemon决定是用户线程还是主线程
         * 设置守护线程，需要在start()方法之前进行
         * 如果线程已经开始，不可以设置是否为守护线程
         */
        t1.setDaemon(true);
        t1.start();
        System.out.println("主线程结束");


    }




}
