# Java多线程
## 线程简介
- 进程是程序的一次执行过程，是一个动态的概念。
- 进程是系统资源分配的单位。
- 通常在一个进程里包含若干个线程，但至少有一个。
- 线程是CPU调度和执行的单位。

#### 核心概念
- 线程就是独立的执行路径;
- 后台本就有多个线程，e.g.主线程(main()，系统的入口，用于执行整个程序)，垃圾回收线程(gc);
- 一个进程中的多线程的运行由调度器安排调度(与OS紧密相关)，先后顺序不可人为干预;
- 对同一份资源进行操作时，会存在资源抢夺问题，需要加入并发控制;
- 线程会带来额外开销;
- 每个线程都在自己的工作内存交互，内存控制不当会造成数据不一致;

#### 注意
真正的多线程指有多个CPU，即多核。一般是**模拟**出来的多线程，即在一个CPU下，同一时刻，只能执行一个线程，但是因为切换的很快，所以导致了同时执行的错觉。


## ⭐ 线程实现
### 创建线程
### ⭐继承 `Thread`类
- 将一个类声明为`Thread`类的子类
- 该类重写`run()`方法，编写线程执行体
- 创建线程对象，调用`start()`方法启动线程
```java
public class TestThread1 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println("我在学习-" + i);
        }
    }

    public static void main(String[] args) {
        
        TestThread1 testThread1 = new TestThread1();
        testThread1.start();
        // 主线程
        for (int i = 0; i < 20; i++) {
            System.out.println("我在学习多线程-" + i);
        }
    }
}
```
- 运行结果：两条线程交替执行

### ⭐实现 `Runnable`接口
- 自定义MyRunner类实现`Runnable`接口
- 实现`run()`方法，编写线程执行体
- 用自定义类的实例去实例化一个线程对象，再调用线程的start()  `new Thread(myRunner).start();`
```java
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
```

#### 两种比较
- 最好使用`Runnable`接口，可以避免单继承局限性，更加灵活方便，方便同一个对象被多个线程使用(代理模式)

#### 注意
多个线程操作同一个资源时，会发生数据紊乱，这是并发的问题

e.g.龟兔赛跑 `Race`

### 实现 `Callable`接口(需要返回值类型) 
- 重写`call()`方法
- 创建目标对象
- 创建执行服务 `ExecutorService ser = Executors.newFixedThreadPool(1);`
- 提交执行 `Future<Boolean> result1 = ser.submit(t1);`
- 获取结果 `boolean r1 = result1.get();`，要抛出异常
- 关闭服务 `ser.shutdownNow();`

e.g.  demo2.TestCallable.java

### 扩展
#### 静态代理模式
代理对象与真实对象都实现同一个接口，代理对象要代理真实角色（即需要真实角色的对象）

对比

`new Thread(() -> System.out.println("啦啦啦")).start();`

`new WeddingCompany(new You()).HappyMarry();`

e.g. staticproxy.StaticProxy.java

## 线程状态(5种)
**创建，就绪，阻塞，运行，死亡**

![screenshot](src/screenshots/1.png)

一个线程可以在某一时刻处于一个状态（不反应任何OS线程状态的虚拟机状态）
- NEW 尚未启动的线程处于此状态（**调用线程的`start()`方法，就会进入就绪态，而不是立即执行**）
- RUNNABLE 在JVM中执行的线程处于此状态
- BLOCKED 被阻塞等待监视器锁定的线程处于此状态
- WAITING 正在等待另一个线程执行特定动作的线程处于此状态
- TIMED_WAITING 正在等待另一个线程执行动作达到指定时间的线程处于此状态
- TERMINATED 已退出的线程处于此状态(**线程中断或结束，一旦进入死亡状态，就不能再次启动**)

### 线程方法
- `setPriority(int newPriority)` 更改线程优先级
- `static void sleep(long millis)` 让当前正在执行的线程休眠millis毫秒
- `void join()` 等待该线程终止
- `static void yield()` 暂停正在执行的线程，并执行其他线程
- `void interrupt()` 中断线程 **慎用**
- `boolean isAlive()` 测试线程是否处于活动状态


### 线程停止 自定义stop
**注意：不要用destroy()或stop()等JDK废弃的方法，让线程停止可以自己实现**

 e.g.status.TestStop.java
 ```java
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
```
### 线程休眠 sleep
- `sleep()`指定当前线程阻塞的**毫秒数**，即进入阻塞状态
- `sleep()`存在`InterruptedException`
- `sleep()`时间结束后，进入就绪状态
- `sleep()`可以可以模拟网络延时、倒计时等 **模拟网络延时可以放大问题的发生性**
- 每个对象都有一个锁，`sleep()`不会释放锁

e.g. 模拟网络延时和时间
```java
public class TestSleep2 {
    // 模拟倒计时
    public static void tenDown() throws InterruptedException {
        int num = 10;

        while (true) {
            Thread.sleep(1000);
            System.out.println(num--);
            if (num <= 0) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        try {
            tenDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 打印当前时间
        Date startTime = new Date(System.currentTimeMillis());
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(new SimpleDateFormat("HH:mm:ss").format(startTime));
            startTime = new Date(System.currentTimeMillis());  // 更新时间
        }
    }
}
```

### 线程礼让 yield
- 礼让线程，让当前正在执行的线程暂停，**但不阻塞**
- 将线程从运行态转为就绪态
- **让CPU重新调度，礼让不一定成功，看CPU心情**
```java
public class TestYield {
    public static void main(String[] args) {
        MyYield myYield = new MyYield();
        new Thread(myYield, "a").start();
        new Thread(myYield, "b").start();
    }

}

class MyYield implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " start");
        Thread.yield();
        System.out.println(Thread.currentThread().getName() + " end");
    }
}
```

### 线程强制执行 join
- `join()`合并线程，待此线程执行完成后，再执行其他线程（阻塞态）
- 在`join()`前，是正常多线程竞争CPU资源
- 可以想象成插队
```java
public class TestJoin implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("线程vip来插队了-->" + i);
        }
    }

    public static void main(String[] args) {
        TestJoin testJoin = new TestJoin();
        Thread thread = new Thread(testJoin);
        thread.start();

        // 主线程
        for (int i = 0; i < 1000; i++) {
            if (i == 200) {
                try {
                    thread.join();   // 200ms时插队, 200之前是两个线程竞争，200开始必须让插队的全部执行完才执行主线程
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("主线程执行-->" + i);
        }
    }
}
```

### 线程优先级
- Java提供一个线程调度器来监控程序中启动后进入就绪状态的所有线程，线程调度器按照优先级决定应该调度哪个线程执行
- 优先级用数字表示，范围为`[1, 10]`
- `Thread.MIN_PRIORITY = 1`
- `Thread.MAX_PRIORITY = 10`
- `Thread.NORM_PRIORITY = 5`
- `getPriority()` 获取优先级
- `setPriority(int)` 改变优先级
- 主线程的优先级是默认的，为**5**，不能修改
- 优先级的设定建议在`start()`之前
- 优先级低只是意味着获得调度的概率低，并不是优先级低就不会被调用了，全靠CPU的调度

### 守护线程 daemon
- 线程分为**用户线程**和**守护线程**
- JVM必须确保用户线程执行完毕
- JVM不用等待守护线程执行完毕
- `setDaemon(true)`设置线程为守护线程，默认为false

e.g. 后台记录操作日志，监控内存，垃圾回收等待

## ⭐ 线程同步
### 并发
- 发生场景：多个线程操作同一资源

### 线程同步
- 线程同步其实就是一种等待机制，多个需要同时访问此对象的线程进入这个对象的**等待池**形成队列，前面的线程使用完毕，后面的才能使用
- 由于同一进程的多个线程共享同一块存储空间，因此会导致访问冲突问题，故需要加入**锁机制synchronized**
- 当一个线程获得对象的排它锁，将会独占资源，其他线程必须等待其使用完毕释放锁

#### 存在问题
- 一个线程持有锁会导致其他所有需要此锁的线程挂起
- 多线程竞争下，加锁、释放锁会导致较多的上下文切换和调度延时，带来性能问题
- 若一个高优先级线程等待一个低优先级线程释放锁，会导致优先级导致，带来性能问题

### 同步方法synchronized方法 和 同步块synchronized块
- 通过`synchronized`关键字对方法进行控制（数据可以通过`priavte`关键字来保证其只被方法访问）
- synchronized方法: `public synchronized void method() {...}` 
- synchronized方法控制对资源对象的访问，每个对象对应一把锁，每个synchronized方法必须获得调用该方法的对象的锁才能执行，
否则线程会阻塞；一旦方法执行，就独占该锁，直到方法返回才释放，其他被阻塞的线程才能获得锁来执行
- 缺陷：若将一个大的方法申明为`synchronized`将会影响效率，方法里需要修改的内容才需要锁
=> `synchronized`块
- `synchronized`块: `synchronized (obj) {...}`
- obj是同步监视器，可以是任何对象(推荐使用共享资源作为同步监视器)，synchronized方法中的同步监视器就是this，或者是class
- 同步监视器执行过程：
  - 第一个线程访问，锁定同步监视器，执行同步块代码
  - 第二个线程访问，发现同步监视器被锁定，无法访问，被阻塞
  - 第一个线程访问完毕，解锁同步监视器
  - 第二个线程发现同步监视器没有锁，然后锁定并执行

- JUC: Java并发包

### 死锁
- 多个线程各自占有一些共享资源，并且互相等待其他线程占有的资源才能运行，而导致两个或多个线程都在等待对方释放资源，都停止星星的情形。
- 某一个同步块同时拥有“两个以上对象的**锁**”时，就可能会发生“死锁”的问题
```java
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

                    // 1s后想获得镜子
                    synchronized (mirror) {
                        System.out.println(this.name + "获得镜子的锁");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // 获得镜子的锁
            synchronized (mirror) {
                System.out.println(this.name + "获得镜子的锁");
                try {
                    Thread.sleep(2000);

                    // 1s后想获得口红
                    synchronized (lipstick) {
                        System.out.println(this.name + "获得口红的锁");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
```
修改为：
```java
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
```
#### 产生死锁的四个**必要**条件
- 互斥：一个资源每次只能被一个进程使用
- 请求与保持：一个进程因请求资源而阻塞时，对已获得的资源保持的不放
- 不剥夺：进程已获得的资源，在未使用完之前，不能强行剥夺
- 循环等待：若干进程之间形成一种头尾相接的循环等待资源关系

**破坏这四个条件之一就可以避免死锁的发生**

#### Lock锁 (JDK 5.0推出)
- 使用Lock对象充当同步锁
- JUC包下的`Lock`接口是控制多个线程对共享资源进行访问的工具，每次只能有一个线程对Lock对象加锁，线程访问共享资源前应该先获得Lock对象
- `ReentrantLock`类(可重用锁)实现了Lock接口，拥有与`synchronized`相同的并发性和内存语义
- 显式地加锁、释放锁操作，一定要记得**关锁**
```java
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
```

#### `synchronized`对比`Lock`
- Lock是显式锁，synchronized是隐式锁，离开了作用域自动释放锁
- Lock只有代码块锁，synchronized有代码块锁和方法锁
- 使用Lock时，JVM的开销较小，性能更好，扩展性更好
- 优先使用顺序：Lock > synchronized块 > synchronized方法

## 线程通信问题
### 生产者和消费者问题
生产者和消费者共享同一个资源，并且之间相互依赖，互为条件
- 生产者：没有生产产品之前，通知消费者等待；生产了产品之后，通知消费者消费
- 消费者：消费后，通知生产者已结束消费，需要生产新的产品以供消费
- synchronized的不足：不能实现线程之间的通信

#### Java提供的线程通信方法(在Object类中，只能用于synchronized方法或synchronized块中)
- `wait()` 表示线程一直等待，直到其他线程通知，等待时释放锁(sleep等待时不会释放锁)
- `wait(long timeout)` 等待指定的时长
- `notify()` 唤醒一个处于等待状态的线程
- `notifyAll()` 唤醒同一个对象上所有调用wait()方法的线程，优先级别高的线程优先调度

## 其他内容
### 线程池
- 高频创建、销毁、使用量特别大的资源，对性能影响很大
- 思路：提前创建好多个线程，放入线程池中，使用时直接获取，使用完放回池中
- 优点：
  - 避免频繁创建销毁，实现重复利用(e.g.共享单车)
  - 提高响应速度(减少了创建新线程的时间)
  - 降低资源消耗(重复利用线程池中线程，不需要每次都创建)
  - 便于线程管理
    - `corePoolSize` 核心池大小
    -  `maximumPoolSize` 最大线程数
    - `keepAliveTime` 线程没有任务时最多保持多长时间后终止

### 使用线程池
- API: `ExecutorService`和`Executors`
- `ExecutorService` 真正的线程池接口，常用子类`ThreadPoolExecutor`
  - `execute(Runnable command)` 执行任务/命令，没有返回值，一般用来执行`Runnable`
  - `submit(Callable<T> task)` 执行任务，有返回值，一般用来执行`Callable`
  - `shutdown() 关闭连接池`
- `Executors` 工具类、线程池的工厂类，用于创建并返回不同类型的线程池
- Callable方法创建线程用的就是这个API
- 步骤
  - 创建线程池，指定容量 `ExecutorService service = Executors.newFixedThreadPool(10);`
  - 执行命令 `service.execute(new MyThread());`
  - 关闭连接 `service.shutdown();`

```java
public class TestThreadPool {
    public static void main(String[] args) {
        // 1.创建线程池,容量为10
        ExecutorService service = Executors.newFixedThreadPool(10);
        // 2.执行
        service.execute(new MyThread());
        service.execute(new MyThread());
        service.execute(new MyThread());
        service.execute(new MyThread());

        // 3.关闭连接
        service.shutdownNow();
    }
}

class MyThread implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}
```