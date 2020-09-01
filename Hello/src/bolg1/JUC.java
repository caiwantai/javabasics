package bolg1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Ticket {
    private int ticketNum = 30;
    private final Lock lock = new ReentrantLock();

    public void saleTicket() {
        lock.lock();
        try {
            if (ticketNum > 0) {

                System.out.println(Thread.currentThread().getName() + "卖出第几：" + ticketNum + "还剩几张票：" + (--ticketNum));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}

public class JUC {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.saleTicket();
            }
        }, "saleman one").start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.saleTicket();
            }
        }, "saleman two").start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.saleTicket();
            }
        }, "saleman three").start();
    }
}
