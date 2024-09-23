package cn.iocoder.yudao.module.callcenter.framework.threadpool.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ThreadpoolProperties.class)
public class ThreadpoolConfiguration {
}
