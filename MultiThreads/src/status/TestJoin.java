package status;

/**
 * 测试join -插队
 */

public class TestJoin implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("线程vip来插队了-->" + i);
        }
    }

    public static void main(String[] args) {
        TestJoin testJoin = new TestJoin();
        Thread thread = new Thread(testJoin);
        thread.start();

        // 主线程
        for (int i = 0; i < 1000; i++) {
            if (i == 200) {
                try {
                    thread.join();  // 200ms时插队, 200之前是两个线程竞争，200开始必须让插队的全部执行完才执行主线程
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("主线程执行-->" + i);
        }
    }
}
