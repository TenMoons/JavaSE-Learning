package deadlock;

public class DeadLock {

    public static void main(String[] args) {
        MakeUp makeUp1 = new MakeUp(0, "石原里虐");
        MakeUp makeUp2 = new MakeUp(1, "石原里美");
        new Thread(makeUp1).start();
        new Thread(makeUp2).start();
    }
}

class Lipstick {

}

class Mirror {

}

class MakeUp implements Runnable {
    // static用于保证资源只有一份
    static Lipstick lipstick = new Lipstick();
    static Mirror mirror = new Mirror();

    int choice;  // 选择
    String name;

    public MakeUp(int choice, String name) {
        this.choice = choice;
        this.name = name;
    }

    @Override
    public void run() {
        // 化妆
        makeUp();
    }

    private void makeUp() {
        if (choice == 0) {
            // 获得口红的锁
            synchronized (lipstick) {
                System.out.println(this.name + "获得口红的锁");
                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 1s后想获得镜子
            synchronized (mirror) {
                System.out.println(this.name + "获得镜子的锁");
            }
        } else {
            // 获得镜子的锁
            synchronized (mirror) {
                System.out.println(this.name + "获得镜子的锁");
                try {
                    Thread.sleep(2000);

                    // 1s后想获得口红

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            synchronized (lipstick) {
                System.out.println(this.name + "获得口红的锁");
            }
        }
    }
}
