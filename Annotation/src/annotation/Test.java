package annotation;

import java.lang.annotation.*;

@MyAnnotation
public class Test {
    @MyAnnotation
    public void testMyAnnotation() {

    }
}

// 定义作用在方法和类上，运行时有效
@Target(value = {ElementType.METHOD,ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Inherited
@interface MyAnnotation {

}
