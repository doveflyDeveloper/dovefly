package cn.dovefly.test;

import cn.dovefly.module.test.entity.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by dove.zhang on 2017/6/12.
 */
public class SpringThreadPoolTest {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");

        ThreadPoolTaskExecutor threadPoolTaskExecutor = (ThreadPoolTaskExecutor)ctx.getBean("threadPoolTaskExecutor");
//        threadPoolTaskExecutor.setCorePoolSize(2);
//        threadPoolTaskExecutor.setMaxPoolSize(100);
//        threadPoolTaskExecutor.setQueueCapacity(25);
//        System.out.println("=" + threadPoolTaskExecutor.getMaxPoolSize());
//        threadPoolTaskExecutor.submit(new Runnable() {
//            @Override
//            public void run() {
//                //while (true) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println("ActiveCount=" + threadPoolTaskExecutor.getActiveCount());
//                    System.out.println("CorePoolSize=" + threadPoolTaskExecutor.getCorePoolSize());
//                    System.out.println("MaxPoolSize=" + threadPoolTaskExecutor.getMaxPoolSize());
//                    System.out.println("PoolSize=" + threadPoolTaskExecutor.getPoolSize());
//                    System.out.println("-------------------------");
//                }
//
//           // }
//        });

        for (int i = 0; i < 5000000; i++) {
            final int d = i;
            threadPoolTaskExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Test test1 = new Test();
                        test1.setId(1);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getId() + "=" + d);
//                    if (d > Integer.MAX_VALUE) {
//                    }
                    System.out.println("ActiveCount=" + threadPoolTaskExecutor.getActiveCount());
                    System.out.println("CorePoolSize=" + threadPoolTaskExecutor.getCorePoolSize());
                    System.out.println("MaxPoolSize=" + threadPoolTaskExecutor.getMaxPoolSize());
                    System.out.println("PoolSize=" + threadPoolTaskExecutor.getPoolSize());
                    System.out.println("-------------------------");
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



