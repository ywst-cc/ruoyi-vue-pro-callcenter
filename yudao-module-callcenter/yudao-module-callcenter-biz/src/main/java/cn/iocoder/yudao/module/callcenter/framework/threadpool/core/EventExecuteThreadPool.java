package cn.iocoder.yudao.module.callcenter.framework.threadpool.core;

import cn.iocoder.yudao.module.callcenter.framework.threadpool.config.ThreadpoolProperties;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class EventExecuteThreadPool {

    @Resource
    private ThreadpoolProperties threadpoolProperties;

    private ThreadPoolExecutor virtualThreadPool;

    @PostConstruct
    public void init(){
        ThreadFactory virtualThreadFactory = Thread.ofVirtual().factory();
        virtualThreadPool = new ThreadPoolExecutor(
                threadpoolProperties.getCorePoolSize(),
                threadpoolProperties.getMaximumPoolSize(),
                0L, // 空闲线程存活时间
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(100000),
                virtualThreadFactory,
                new ThreadPoolExecutor.AbortPolicy() // 拒绝策略
        );
    }

    @PreDestroy
    public void destroy(){
        virtualThreadPool.shutdown();
    }

    /**
     * 提交线程池runnable
     * @param runnable 线程池runnable
     */
    public void execute(Runnable runnable){
        virtualThreadPool.execute(runnable);
    }
}
