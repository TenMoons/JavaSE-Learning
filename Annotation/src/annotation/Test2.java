package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Test2 {
    // 没有默认值则必须给注解赋值
    @MyAnnotation2(name = "TenMoons", schools = {"AHU", "NJU"})
    public void test() {

    }

    // 若注解只有一个参数，且参数名为value，则可以省略value=
    @MyAnnotation3("test")
    public void test2() {

    }
}

// 作用在类、方法上
@Target(value = {ElementType.TYPE, ElementType.METHOD})
// 运行时有效
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation2 {
    // 注解的参数： 参数类型 + 参数名()
    String name() default "";
    int age() default 0;
    int id() default -1;  // 默认值为-1，代表不存在

    String[] schools();
}

// 作用在类、方法上
@Target(value = {ElementType.TYPE, ElementType.METHOD})
// 运行时有效
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation3 {
    // 注解的参数： 参数类型 + 参数名()
    String value();
}