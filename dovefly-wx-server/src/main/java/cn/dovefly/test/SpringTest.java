package cn.dovefly.test;

import cn.dovefly.framework.schema.Animal;
import cn.dovefly.framework.schema.People;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by fengchunming on 2017/5/12.
 */
public class SpringTest {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");

        ThreadPoolTaskExecutor threadPoolTaskExecutor = (ThreadPoolTaskExecutor)ctx.getBean("threadPoolTaskExecutor");
//        threadPoolTaskExecutor.setCorePoolSize(2);
//        threadPoolTaskExecutor.setMaxPoolSize(100);
        threadPoolTaskExecutor.setQueueCapacity(25);
//        System.out.println("=" + threadPoolTaskExecutor.getMaxPoolSize());
        threadPoolTaskExecutor.initialize();
        threadPoolTaskExecutor.submit(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("ActiveCount=" + threadPoolTaskExecutor.getActiveCount());
                    System.out.println("CorePoolSize=" + threadPoolTaskExecutor.getCorePoolSize());
                    System.out.println("MaxPoolSize=" + threadPoolTaskExecutor.getMaxPoolSize());
                    System.out.println("PoolSize=" + threadPoolTaskExecutor.getPoolSize());
                    System.out.println("-------------------------");
                }

            }
        });

        for (int i = 0; i < 5000; i++) {
            final int d = i;
            threadPoolTaskExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getId() + "=" + d);
//                    if (d > Integer.MAX_VALUE) {
//                    }
//                    System.out.println("ActiveCount=" + threadPoolTaskExecutor.getActiveCount());
//                    System.out.println("CorePoolSize=" + threadPoolTaskExecutor.getCorePoolSize());
//                    System.out.println("MaxPoolSize=" + threadPoolTaskExecutor.getMaxPoolSize());
//                    System.out.println("PoolSize=" + threadPoolTaskExecutor.getPoolSize());
//                    System.out.println("-------------------------");
                }
            });

//            System.out.println("ActiveCount=" + threadPoolTaskExecutor.getActiveCount());
//            System.out.println("CorePoolSize=" + threadPoolTaskExecutor.getCorePoolSize());
//            System.out.println("MaxPoolSize=" + threadPoolTaskExecutor.getMaxPoolSize());
//            System.out.println("PoolSize=" + threadPoolTaskExecutor.getPoolSize());
//            System.out.println("-------------------------");

//            System.out.println("=" + threadPoolTaskExecutor.getPoolSize());
//            if (i % 10 == 0) {
//                try {
//                    Thread.sleep(200);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            System.out.println("===================");
        }

    }
}
