package staticproxy;

/**
 * 静态代理
 */

public class StaticProxy  {
    public static void main(String[] args) {

        new WeddingCompany(new You()).HappyMarry();

    }
}

interface Marry {
    void HappyMarry();
}

class You implements Marry {
    @Override
    public void HappyMarry() {
        System.out.println("要结婚了");
    }
}

// 代理角色
class WeddingCompany implements Marry {
    private Marry target;  // 被代理对象

    public WeddingCompany(Marry target) {
        this.target = target;
    }

    @Override
    public void HappyMarry() {
        before();
        target.HappyMarry();
        after();
    }

    private void after() {
        System.out.println("After:柴米油盐酱醋茶");
    }

    private void before() {
        System.out.println("Before:布置场地");
    }

}