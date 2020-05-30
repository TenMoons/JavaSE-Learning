package demo1;

/**
 * 多线程操作同一对象
 */

public class TestThread5 implements Runnable {
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
        TestThread5 testThread5 = new TestThread5();
        new Thread(testThread5, "张三").start();
        new Thread(testThread5, "李四").start();
        new Thread(testThread5, "王五").start();
    }
}
