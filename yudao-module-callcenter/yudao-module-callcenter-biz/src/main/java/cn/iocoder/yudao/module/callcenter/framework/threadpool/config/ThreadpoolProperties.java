package cn.iocoder.yudao.module.callcenter.framework.threadpool.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "freeswitch.threadpool")
@Data
public class ThreadpoolProperties {

    /**
     * 核心线程数
     */
    private int corePoolSize;

    /**
     * 最大线程数
     */
    private int maximumPoolSize;
}
