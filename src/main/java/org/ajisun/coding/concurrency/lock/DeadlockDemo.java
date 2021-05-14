package org.ajisun.coding.concurrency.lock;

/**
 * @Copyright (c) 2020. Ajisun Enterprise Solution Company. All right reserved.
 * @ProjectName: concurrency
 * @PackageName: org.sun.concurrency.lock
 * @Date: 2020/6/30
 * @author: ajisun
 *
 */
public class DeadlockDemo {


    public static void main(String[] args) {

        Object1 object1 = new Object1();
        Object2 object2 = new Object2();
        Thread thread = new Thread(new SynAddRunnable(object1,object2,1,2,true));
        thread.setName("Thread1");
        thread.start();

        Thread thread2 = new Thread(new SynAddRunnable(object1,object2,1,6,false));
        thread2.setName("Thread2");
        thread2.start();

    }


    public static class SynAddRunnable implements Runnable{

        Object1 object1;
        Object2 object2;
        int a , b;
        boolean flag ;

        public SynAddRunnable(Object1 one,Object2 two,int a ,int b,boolean flag){

            this.object1 = one;
            this.object2 = two;
            this.a = a;
            this.b = b;
            this.flag = flag;

        }


        public void run() {

            try {

                if (flag){
                    synchronized (object1){
                        System.out.println("====:");
                        Thread.sleep(100);
                        synchronized (object2){
                            System.out.println(a + b);
                        }

                    }
                }else {
                    synchronized (object2){
                        Thread.sleep(100);
                        synchronized (object1){
                            System.out.println(a + b);
                        }
                    }
                }

            }catch (InterruptedException e){
                e.getStackTrace();
            }

        }


    }


    public static class Object1{

    }

    public static class Object2{

    }

}
