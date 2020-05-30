package syn;

/**
 * 不安全版本的买票 -> synchronized方法
 * 线程不安全，有负数
 */

public class UnsafeBuyTicket {
    public static void main(String[] args) {
        BuyTicket buyTicket = new BuyTicket();

        new Thread(buyTicket, "小A").start();
        new Thread(buyTicket, "小B").start();
        new Thread(buyTicket, "小C").start();

    }
}

class BuyTicket implements Runnable {
    // 票
    private int ticketNums = 10;
    // 标志位
    private boolean flag = true;

    @Override
    public void run() {
        // 买票
        while (flag) {
            buy();
        }

    }

    private synchronized void buy() {
        // 判断是否有票
        if (ticketNums <= 0) {
            flag = false;
            return;
        }

        // 模拟延时，放大效果
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "买票-->" + ticketNums--);
    }
}
