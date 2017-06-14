package cn.dovefly.framework.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
  
public class DoveNamespaceHandler extends NamespaceHandlerSupport {
  
    public void init() {  
        registerBeanDefinitionParser("people", new PeopleBeanDefinitionParser());
        registerBeanDefinitionParser("animal", new AnimalBeanDefinitionParser());
    }
}  