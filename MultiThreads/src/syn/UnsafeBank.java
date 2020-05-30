package syn;

/**
 * 不安全地取钱 -> synchronized块
 * 两个人在同一个账户取钱
 */

public class UnsafeBank {
    public static void main(String[] args) {
        Account account = new Account(500, "拆迁补偿");
        Drawing you = new Drawing(account, 50, "我");
        Drawing object = new Drawing(account, 80, "我对象");

        you.start();
        object.start();

    }
}

// 银行账户
class Account {
    int money;  // 余额
    String name;  // 卡名

    public Account(int money, String name) {
        this.money = money;
        this.name = name;
    }

}

// 银行:模拟取款
class Drawing extends Thread {
    Account account;
    int drawingMoney;
    int nowMoney;

    public Drawing(Account account, int drawingMoney, String name) {
        super(name);
        this.account = account;
        this.drawingMoney = drawingMoney;
    }

    @Override
    public void run() {
        synchronized (account) {
            // 判断有没有钱
            if (account.money - drawingMoney < 0) {
                System.out.println(Thread.currentThread().getName() + "-->钱不够，取不了");
                return;
            }

            // 更新取钱后余额和手上现款 模拟延时
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            account.money -= drawingMoney;
            nowMoney += drawingMoney;

            System.out.println(Thread.currentThread().getName() + "取了钱" + drawingMoney);
            System.out.println(account.name + "余额为：" + account.money);
            System.out.println(this.getName() + "手里的钱：" + nowMoney);
        }

    }
}
