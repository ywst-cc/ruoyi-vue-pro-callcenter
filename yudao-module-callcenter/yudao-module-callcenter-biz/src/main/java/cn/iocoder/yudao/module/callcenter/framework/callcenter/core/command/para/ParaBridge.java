package cn.iocoder.yudao.module.callcenter.framework.callcenter.core.command.para;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParaBridge implements Serializable {

    private String uuid;

    /**
     * 指定被叫uuid外呼
     */
    private String calleeUuid;

    /**
     * 话单ID，用于录音文件名拼接使用
     */
    private String cdrSessionId;

    private String caller;

    private String callee;

    /**
     * 线路号码
     */
    private String no;

    /**
     * 号码前缀
     */
    private String prefix;

    /**
     * 线路地址
     */
    private String sipAddress;

}
