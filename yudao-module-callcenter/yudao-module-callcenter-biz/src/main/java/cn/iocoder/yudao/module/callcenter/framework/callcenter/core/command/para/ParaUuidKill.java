package cn.iocoder.yudao.module.callcenter.framework.callcenter.core.command.para;

import lombok.Data;

import java.io.Serializable;

@Data
public class ParaUuidKill implements Serializable {

    /**
     * 挂件uuid
     */
    private String uuid;
    /**
     * 挂机cause
     */
    private String hangupCause;

}
