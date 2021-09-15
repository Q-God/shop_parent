package com.gmall.util.web.handler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @version v1.0
 * @ClassName MyExecutor
 * @Description 单元素的枚举类型已经成为实现Singleton的最佳方法
 * -- 出自 《effective java》
 * @Author Q
 */
public class MyExecutor {

    enum SingleThreadExecutorEnum {
        //创建一个枚举对象，该对象天生单例
        INSTANCE;
        private final ThreadPoolExecutor threadPoolExecutor;

        //私有化枚举的构造函数
        SingleThreadExecutorEnum() {
            threadPoolExecutor = new ThreadPoolExecutor(
                    50, 500, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100)
            );
        }

        //暴露共有方法给MyExecutor
        public ThreadPoolExecutor getInstance() {
            return threadPoolExecutor;
        }
    }

    //暴露一个获取ThreadPoolExecutor实例的静态方法
    public static ThreadPoolExecutor getInstance() {
        return SingleThreadExecutorEnum.INSTANCE.getInstance();
    }
}
