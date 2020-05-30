package demo1;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * 多线程同步下载图片(实现 Runnable)
 */

public class TestThread4 implements Runnable {
    private String url;  // 网络图片地址
    private String name;  // 保存的文件名

    public TestThread4(String url, String name) {
        this.url = url;
        this.name = name;
    }

    @Override
    public void run() {

        WebDownloader webDownloader = new WebDownloader();
        webDownloader.downloader(url, name);
        System.out.println("下载了文件名为：" + name);

    }

    public static void main(String[] args) {
        TestThread4 testThread1 = new TestThread4("https://wx3.sinaimg.cn/mw690/006t5KMyly1gf63oh130zj30u00zmjwp.jpg", "1_1.jpg");
        TestThread4 testThread2 = new TestThread4("https://wx4.sinaimg.cn/mw690/6da726d1ly1gf5owba093j20ke0sgqlc.jpg", "2_1.jpg");
        TestThread4 testThread3 = new TestThread4("https://wx1.sinaimg.cn/mw690/006NgMqGgy1gf51v03o26j31921o0qnh.jpg", "3_1.jpg");

        new Thread(testThread1).start();
        new Thread(testThread2).start();
        new Thread(testThread3).start();

    }
}


// 下载器
class WebDownloader2 {
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