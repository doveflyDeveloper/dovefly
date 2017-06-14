package cn.dovefly.framework.schema;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class AnimalBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
    protected Class getBeanClass(Element element) {
        return Animal.class;
    }

    protected void doParse(Element element, BeanDefinitionBuilder bean) {
        String id = element.getAttribute("id");
        String color = element.getAttribute("color");
        String size = element.getAttribute("size");

        if (StringUtils.hasText(id)) {
            bean.addPropertyValue("id", id);
        }
        if (StringUtils.hasText(color)) {
            bean.addPropertyValue("color", color);
        }
        if (StringUtils.hasText(size)) {
            bean.addPropertyValue("size", Integer.valueOf(size));
        }
    }
}