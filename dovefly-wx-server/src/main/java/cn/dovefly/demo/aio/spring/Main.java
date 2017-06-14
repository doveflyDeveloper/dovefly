package cn.dovefly.demo.aio.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by fengchunming on 2017/6/5.
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
        FunctionService functionService = context.getBean(FunctionService.class);
        System.out.println(functionService.sayHello("spring"));
        UseFunctionService useFunctionService = context.getBean(UseFunctionService.class);
        System.out.println(useFunctionService.sayHello("config"));
        context.close();
    }
}
