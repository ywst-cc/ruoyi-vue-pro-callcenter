package cn.iocoder.yudao.module.callcenter.framework.callcenter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "freeswitch.callcenter")
@Data
public class FreeswitchProperties {

    /**
     * 连接freeswitch地址
     */
    private String host;
    /**
     * 连接freeswitch esl 端口
     */
    private Integer port;
    /**
     * freeswitch esl 连接密码，默认ClueCon
     */
    private String password;
    /**
     * 连接超时时间，单位：秒
     */
    private Integer timeout;
}
