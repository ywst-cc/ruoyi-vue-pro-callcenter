package cn.iocoder.yudao.module.callcenter.framework.callcenter.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(FreeswitchProperties.class)
public class FreeswitchConfiguration {
}
