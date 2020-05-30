package demo2;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.*;

/**
 * 线程创建方式 3：实现 callable接口
 */

public class TestCallable implements Callable<Boolean> {
    private String url;  // 网络图片地址
    private String name;  // 保存的文件名

    public TestCallable(String url, String name) {
        this.url = url;
        this.name = name;
    }

    @Override
    public Boolean call() {

        WebDownloader3 webDownloader = new WebDownloader3();
        webDownloader.downloader(url, name);
        System.out.println("下载了文件名为：" + name);
        return true;

    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        TestCallable testThread1 = new TestCallable("https://wx3.sinaimg.cn/mw690/006t5KMyly1gf63oh130zj30u00zmjwp.jpg", "1_2.jpg");
        TestCallable testThread2 = new TestCallable("https://wx4.sinaimg.cn/mw690/6da726d1ly1gf5owba093j20ke0sgqlc.jpg", "2_2.jpg");
        TestCallable testThread3 = new TestCallable("https://wx1.sinaimg.cn/mw690/006NgMqGgy1gf51v03o26j31921o0qnh.jpg", "3_2.jpg");

        /**
         * - 重写`call()`方法，需要抛出异常
         * - 创建执行服务 `ExecutorService ser = Executors.newFixedThreadPool(1);`
         * - 提交执行 `Future<Boolean> result1 = ser.submit(t1);`
         * - 获取结果 `boolean r1 = result1.get();`，要抛出异常
         * - 关闭服务 `ser.shutdownNow();`
         */

        // 创建执行服务
        ExecutorService ser = Executors.newFixedThreadPool(3);

        // 提交执行
        Future<Boolean> result1 = ser.submit(testThread1);
        Future<Boolean> result2 = ser.submit(testThread2);
        Future<Boolean> result3 = ser.submit(testThread3);

        // 获取结果
        boolean r1 = result1.get();
        boolean r2 = result2.get();
        boolean r3 = result3.get();

        // 关闭服务
        ser.shutdownNow();
    }
}

// 下载器
class WebDownloader3 {
    // 下载方法
    public void downloader(String url, String name) {
        try {
            FileUtils.copyURLToFile(new URL(url), new File(name));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO异常，downloader方法出错");
        }
    }
}