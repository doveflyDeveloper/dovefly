package cn.dovefly.demo.aio;

/**
 * Created by fengchunming on 2017/6/2.
 */
public class Test {
    public static void main(String[] args) {

        test1();

//        test2();

    }

    public static void test1() {
        System.out.println("ssssssssssss");
        //此处系统会自动创建一个HashMap,为什么???,可在HashMap源码里调试看
//        System.out.println(new HashMap<>(10));
    }

}
