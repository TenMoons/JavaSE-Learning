package demo1;

/**
 * 创建线程方法 1 ：继承 Thread，重写 run()，调用 start()
 */

public class TestThread1 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println("我在学习-" + i);
        }
    }

    public static void main(String[] args) {

        TestThread1 testThread1 = new TestThread1();
        testThread1.start();
        // 主线程
        for (int i = 0; i < 20; i++) {
            System.out.println("我在学习多线程-" + i);
        }
    }
}
