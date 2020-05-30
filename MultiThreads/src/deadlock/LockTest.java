package deadlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试Lock锁
 */

public class LockTest {
    public static void main(String[] args) {
        MyLock myLock = new MyLock();

        new Thread(myLock).start();
        new Thread(myLock).start();
        new Thread(myLock).start();
    }
}

class MyLock implements Runnable {
    int ticketNum = 10;

    // 定义Lock锁
    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            try {
                // 获取资源前，加锁
                lock.lock();
                if (ticketNum > 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(ticketNum--);
                } else {
                    break;
                }
            } finally {
                // 使用完毕后，释放锁
                lock.unlock();
            }

        }
    }
}
