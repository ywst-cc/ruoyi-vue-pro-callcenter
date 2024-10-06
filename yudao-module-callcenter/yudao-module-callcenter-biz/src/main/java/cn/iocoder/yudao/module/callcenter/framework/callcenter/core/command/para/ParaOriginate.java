package cn.iocoder.yudao.module.callcenter.framework.callcenter.core.command.para;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ParaOriginate implements Serializable {
    /**
     * 指定uuid
     */
    private String uuid;
    /**
     * 主叫号码
     */
    private String caller;
    /**
     * 被叫号码
     */
    private String callee;

    public ParaOriginate(String uuid, String caller, String callee) {
        this.uuid = uuid;
        this.caller = caller;
        this.callee = callee;
    }
}
