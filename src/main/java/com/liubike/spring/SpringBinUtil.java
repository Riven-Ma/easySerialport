package com.liubike.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author riven
 */
@Component
public class SpringBinUtil implements ApplicationContextAware {

    public static ApplicationContext context;

 
	@SuppressWarnings("unchecked")
	public static <T> Object getBean(Class<T> clazz) {
    	Object bean=context.getBean(clazz);
    	if(bean!=null) {
    		return (T)bean;
    	}
		return null;
	}

	@Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context=applicationContext;
    }
    
    
}
