package status;

/**
 * 测试线程停止
 * 设置外部标识位和公开方法来控制停止
 */

public class TestStop implements Runnable {
    private boolean flag = true;

    @Override
    public void run() {
        int i = 0;
        while (flag) {
            System.out.println("run--> Thread " + i++);
        }
    }

    public void stop() {
        this.flag = false;
    }

    public static void main(String[] args) {
        TestStop testStop = new TestStop();
        new Thread(testStop).start();
        for (int i = 0; i < 1000; i++) {
            System.out.println("main-->" + i);
            if (i == 900) {
                testStop.stop();
                System.out.println("该线程停止");
            }
        }
    }
}
