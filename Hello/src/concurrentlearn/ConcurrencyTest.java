package concurrentlearn;

/**
 * 多线程执行并没有都是比单线程块
 * 当并发执行累加操作不超过百万次时，速度会比串行执行累加操作要
 * 慢。那么，为什么并发执行的速度会比串行慢呢？这是因为线程有创建和上下文切换的开销。
 */
public class ConcurrencyTest {
    private static final long count = 10001;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("**********多线程*********");
        concurrency();
        System.out.println("************单线程************");
        serial();
    }

    private static void concurrency() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread thread = new Thread(() -> {
            int a = 0;
            for (long i = 0; i < count; i++) {
                a = a + 5;
            }
            System.out.println("a = "+a);
        });
        thread.start();

        int b = 0;
        for (long i = 0; i < count; i++) {
            b--;
        }
        thread.join();
        long time = System.currentTimeMillis() - start;
        System.out.println("concurrency :" + time + "ms,b=" + b);
    }

    private static void serial() {
        long start = System.currentTimeMillis();
        int a = 0;
        for (long i = 0; i < count; i++) {
            a = a + 5;
        }
        System.out.println("a = " + a);
        int b = 0;
        for (long i = 0; i < count; i++) {
            b--;
        }
        long time = System.currentTimeMillis() - start;
        System.out.println("serial:" + time + "ms,b=" + b + ",a=" + a);

    }
}
