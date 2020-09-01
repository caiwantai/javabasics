package bolg1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class airCondition1 {
    private int number = 0;
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public  void increment() {
        lock.lock();
        try {
            // 1 判读，要用while重复检查条件
            while (number != 0) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 干活
            number++;
            System.out.println(Thread.currentThread().getName() + "====>" + number);
            // 通知
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public  void decrement() {
        lock.lock();
        try {
            while (number == 0) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            number--;
            System.out.println(Thread.currentThread().getName() + "====>" + number);

            condition.signalAll();
        } catch ( Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }


    }
}

public class JUC2 {
    public static void main(String[] args) {
        // 4个线程
        airCondition1 condition = new airCondition1();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                condition.increment();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                condition.decrement();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                condition.increment();
            }
        }, "C").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                condition.decrement();
            }
        }, "D").start();
    }
}
