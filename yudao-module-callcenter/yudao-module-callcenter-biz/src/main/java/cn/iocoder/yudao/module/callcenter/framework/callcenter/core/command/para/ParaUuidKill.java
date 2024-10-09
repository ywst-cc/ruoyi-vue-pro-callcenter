package cn.iocoder.yudao.module.callcenter.framework.callcenter.core.command.para;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
