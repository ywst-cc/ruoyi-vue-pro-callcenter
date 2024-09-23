package cn.iocoder.yudao.module.callcenter.framework.web;

import cn.iocoder.yudao.framework.swagger.config.YudaoSwaggerAutoConfiguration;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * callcenter 模块的 Web 组件的 Configuration
 */
@Configuration(proxyBeanMethods = false)
public class CallcenterWebConfiguration {

    /**
     * callcenter 模块的 API 分组
     * @return
     */
    @Bean
    public GroupedOpenApi callcenterGroupedOpenApi() {
        return YudaoSwaggerAutoConfiguration.buildGroupedOpenApi("callcenter");
    }
}
