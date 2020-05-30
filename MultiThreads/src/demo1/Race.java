package demo1;

/**
 * 龟兔赛跑
 */

public class Race implements Runnable {
    private static String winner;  // 胜利者

    @Override
    public void run() {
        for (int i = 1; i <= 100; i++) {
            if (Thread.currentThread().getName().equals("兔子") && i % 10 == 0) {  // 兔子每10步睡觉
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            boolean flag = gameOver(i);
            // 比赛结束，停止程序
            if (flag) {
                break;
            }

            System.out.println(Thread.currentThread().getName() + "-->跑了" + i + "步");
        }
    }

    // 判断是否完成比赛
    private boolean gameOver(int steps) {
        if (winner != null) {
            return true;
        }
        if (steps >= 100) {
            winner = Thread.currentThread().getName();
            System.out.println("胜利者是:" + winner);
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Race race = new Race();
        new Thread(race, "兔子").start();
        new Thread(race, "乌龟").start();
    }
}
