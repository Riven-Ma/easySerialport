package com.liubike.serialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该标记，表示需要解析该一般Field字段
 * @author riven
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldTag {


    int index() default -1;


    int len() default 2;
    
    int assembleLen() default 2;

    /**
     * 大小端解析
     * @return
     */
    boolean endian() default true;


}
