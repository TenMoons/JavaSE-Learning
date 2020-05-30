# 注解
学习框架的基础

## 注解入门
### 作用
- 不是程序本身，可以对程序作出解释(类似comment)
- **可以被其他程序读取**(e.g.编译器)

### 格式
`@注解名`，还可以添加参数值(e.g.`@SuppressWarnings(value="unchecked"`)

### 在哪里使用
在package/class/method/field上面，相当于添加额外的辅助信息，可以通过**反射机制**编程实现对这些元数据的访问

## 内置注解
- `@Override` 只适用于方法，表示一个方法声明将要重写超类中的另一个方法声明
- `@Deprecated` 适用于方法、属性、类，表示不鼓励程序员使用这样的元素
- `@SippressWarnings` 用来抑制编译时的警告信息，需要添加一个参数才能正确使用
  - `"all"`
  - `"unchecked"`
  - `value={"unchecked","deprecation"}`
  - ...
  

## 自定义注解， 元注解
### 元注解 Meta-Annotation
- 作用：负责注解其他注解
- ⭐`@Target` 描述注解的适用范围
- ⭐`@Retention` 表示需要在什么级别保存该注释信息，用于描述注解的生命周期
  -  SOURCE < CLASS < RUNTIME(多用) (源码 < 类级别 < 运行时)
- `@Documented` 表示该注解将被包含在javadoc中
- `@Inherited` 表示子类可以继承父类中的该注解

```java
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

```

### 自定义注解
- 使用`@interface`自定义注解时，自动继承`java.lang.annotation.Annotation`接口
- 格式 `public @interface 注解名 {定义内容}`
  - 其中的每一个方法实际是声明了一个配置参数，参数名就是方法名，参数类型就是返回值类型(基本类型，Class，String，enum)
  - 可以通过`default`来声明参数的默认值
  - 如果只有一个参数成员，一般参数名为`value`
  - 注解元素必须要有值，定义注解元素时，常用空字符串，0作为默认值

```java
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
```

# 反射
