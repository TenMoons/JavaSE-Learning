package demo1;

/**
 * 创建线程方式 2：实现 Runnable接口，重写run，执行线程需要传入Runnable接口实现类，调用start()方法
 */

public class TestThread3 implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println("我在学习-" + i);
        }
    }

    public static void main(String[] args) {

        TestThread3 testThread3 = new TestThread3();
        new Thread(testThread3).start();  // 传入Runnable接口实现类
        // 主线程
        for (int i = 0; i < 500; i++) {
            System.out.println("我在学习多线程-" + i);
        }
    }
}
