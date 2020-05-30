package status;

/**
 * 观测线程状态
 */

public class TestStatus {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("----");
        });


        // 观察状态
        Thread.State state = thread.getState();
        System.out.println("状态：" + state);

        // 观察启动后
        thread.start();
        state = thread.getState();
        System.out.println("状态：" + state);

        while (state != Thread.State.TERMINATED) {  // 只要线程不终止，就一直输出状态
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            state = thread.getState();  // 更新线程状态
            System.out.println("状态：" + state);
        }


    }
}
