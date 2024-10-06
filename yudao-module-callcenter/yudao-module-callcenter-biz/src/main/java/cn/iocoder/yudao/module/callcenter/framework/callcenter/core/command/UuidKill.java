package cn.iocoder.yudao.module.callcenter.framework.callcenter.core.command;

import cn.iocoder.yudao.module.callcenter.framework.callcenter.core.command.para.ParaUuidKill;
import org.apache.commons.lang3.StringUtils;

public class UuidKill extends AbstractCommand<ParaUuidKill> {
    @Override
    public String getCommand(ParaUuidKill obj) {
        StringBuilder sb = new StringBuilder("uuid_kill ");
        sb.append(obj.getUuid());
        // 挂机原因可以为空
        if (StringUtils.isNotBlank(obj.getHangupCause())) {
            sb.append(" ").append(obj.getHangupCause());
        }
        return sb.toString();
    }
}
