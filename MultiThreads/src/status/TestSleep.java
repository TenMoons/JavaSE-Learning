package status;

/**
 * 模拟网络延时
 */

public class TestSleep implements Runnable {
    private int ticketNums = 10;  // 票数

    @Override
    public void run() {
        while (true) {
            if (ticketNums <= 0) {
                break;
            }
            // 模拟延时
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "拿到了票号：" + ticketNums--);
        }
    }

    public static void main(String[] args) {
        TestSleep testSleep = new TestSleep();
        new Thread(testSleep, "张三").start();
        new Thread(testSleep, "李四").start();
        new Thread(testSleep, "王五").start();
    }
}
