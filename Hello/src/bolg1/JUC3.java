package bolg1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareResource {
    private int number = 1;// 1：启动A，2：启动B，3：启动C
    private final Lock lock = new ReentrantLock();
    private final Condition condition1 = lock.newCondition();
    private final Condition condition2 = lock.newCondition();
    private final Condition condition3 = lock.newCondition();

    public void print(int times,int flag){
        lock.lock();
        try {
            // 判断
            while (number!=flag){
                switch (flag){
                    case 1:{
                        condition1.await();
                        break;
                    }
                    case 2:{
                        condition2.await();
                        break;
                    }
                    case 3:{
                        condition3.await();
                        break;
                    }
                }
            }
            // 干活
            for (int i = 1; i <= times ; i++) {
                System.out.println(Thread.currentThread().getName()+"==>"+i);
            }
            // 通知
            switch (flag){
                case 1:{
                    number=2;
                    condition2.signal();
                    break;
                }
                case 2:{
                    number=3;
                    condition3.signal();
                    break;
                }
                case 3:{
                    number=1;
                    condition1.signal();
                    break;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}

public class JUC3 {
    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.print(5,1);
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.print(10,2);
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.print(15,3);
            }
        }, "C").start();

    }
}
