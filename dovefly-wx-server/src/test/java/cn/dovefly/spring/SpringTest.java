package cn.dovefly.spring;

import cn.dovefly.framework.schema.Animal;
import cn.dovefly.framework.schema.People;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by fengchunming on 2017/5/12.
 */
public class SpringTest {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/spring-context-test.xml");
        People p = (People)ctx.getBean("people1");
        System.out.println(p.getId());
        System.out.println(p.getName());
        System.out.println(p.getAge());

        Animal a = (Animal)ctx.getBean("animal1");
        System.out.println(a.getId());
        System.out.println(a.getColor());
        System.out.println(a.getSize());
    }
}
