package cn.dovefly.demo.aio.spring;

import java.util.function.Function;

/**
 * Created by fengchunming on 2017/6/5.
 */
public class UseFunctionService {
    FunctionService functionService;

    public void setFunctionService(FunctionService functionService) {
        this.functionService = functionService;
    }

    public String sayHello(String word) {
        return functionService.sayHello(word);
    }
}
