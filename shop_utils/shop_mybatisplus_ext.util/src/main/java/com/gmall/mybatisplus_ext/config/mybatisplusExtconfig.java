package com.gmall.mybatisplus_ext.config;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.gmall.mybatisplus_ext.util.CustomInsertBatchSomeColumn;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @version v1.0
 * @ClassName mybatisplusExtconfig
 * @Description TODO
 * @Author Q
 */
@Configuration
public class mybatisplusExtconfig {
    /**
     * 注入sql注入器
     */
    @Bean
    public DefaultSqlInjector sqlInjector() {
        return new DefaultSqlInjector() {
            /** * 注入自定义全局方法 */
            @Override
            public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
                List<AbstractMethod> methodList = super.getMethodList(mapperClass);
                methodList.add(new CustomInsertBatchSomeColumn());
                return methodList;
            }
        };
    }
}
