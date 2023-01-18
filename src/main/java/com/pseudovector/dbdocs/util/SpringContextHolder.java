package com.pseudovector.dbdocs.util;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    @SuppressWarnings("java:S2696")
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringContextHolder.context = context;
    }

    public static ApplicationContext getApplicationContext() {
        assertApplicationContext();
        return SpringContextHolder.context;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        assertApplicationContext();
        return (T) context.getBean(beanName);
    }

    public static <T> T getBean(Class<T> beanClass) {
        assertApplicationContext();
        return context.getBean(beanClass);
    }

    public static Map<String, Object> getBeanByAnnotation(Class<? extends Annotation> annotation) {
        assertApplicationContext();
        if (null == annotation) {
            annotation = Annotation.class; 
        }
        return context.getBeansWithAnnotation(annotation);
    }

    public static List<Object> getBeansWithAnnotation(Class<? extends Annotation> annotation) {
        Map<String, Object> m = SpringContextHolder.getBeanByAnnotation(annotation);
        return Lists.newArrayList(m.values());
    }
    
    @SuppressWarnings("java:S112")
    private static void assertApplicationContext() {
        if (SpringContextHolder.context == null) {
            throw new RuntimeException("applicaitonContext is null, Please check SpringContextHolder injection!");
        }
    }
}
