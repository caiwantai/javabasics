package bolg1;

class airCondition {
    private int number = 0;

    public synchronized void increment() {
        // 1 判读，要用while重复检查条件
        while (number != 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 干活
        number++;
        System.out.println(Thread.currentThread().getName() + "====>" + number);
        // 通知
        this.notifyAll();
    }

    public synchronized void decrement() {
        while (number == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        number--;
        System.out.println(Thread.currentThread().getName() + "====>" + number);
        this.notifyAll();
    }
}

public class JUC1 {
    public static void main(String[] args) {
        // 4个线程
        airCondition condition = new airCondition();
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
