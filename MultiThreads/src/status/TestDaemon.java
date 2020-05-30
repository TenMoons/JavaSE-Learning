package status;

/**
 * 测试守护线程
 */

public class TestDaemon {
    public static void main(String[] args) {
        God god = new God();
        People people = new People();

        Thread thread = new Thread(god);
        thread.setDaemon(true);
        thread.start();

        Thread thread1 = new Thread(people);
        thread1.start();
    }
}

class God implements Runnable {
    @Override
    public void run() {
        while (true) {
            System.out.println("上帝一直活着");
        }
    }
}

class People implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 36500; i++) {
            System.out.println("开开心心活着");
        }
        System.out.println("-----Goodbye world!");
    }
}
