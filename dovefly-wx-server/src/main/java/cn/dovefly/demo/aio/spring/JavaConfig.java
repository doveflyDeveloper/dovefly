package cn.dovefly.demo.aio.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by fengchunming on 2017/6/5.
 */
@Configuration
public class JavaConfig {
    @Bean
    public FunctionService functionService() {
        System.out.println("new FunctionService()");
        return new FunctionService();
    }
    @Bean
    public UseFunctionService useFunctionService() {
        UseFunctionService useFunctionService = new UseFunctionService();
        useFunctionService.setFunctionService(functionService());
        useFunctionService.setFunctionService(functionService());
        useFunctionService.setFunctionService(functionService());
        return useFunctionService;
    }
}
