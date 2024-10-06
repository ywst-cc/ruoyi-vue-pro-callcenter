package cn.iocoder.yudao.module.callcenter.framework.callcenter.core.command;

import cn.iocoder.yudao.module.callcenter.framework.callcenter.core.command.para.ParaOriginate;

public class Originate extends AbstractCommand<ParaOriginate> {

    @Override
    public String getCommand(ParaOriginate obj) {
        return String.format("originate {sip_auto_answer=true,sip_copy_custom_headers=true,origination_uuid=%s,origination_caller_id_number=%s,origination_caller_id_name=%s}user/%s %s",
                obj.getUuid(), obj.getCallee(), obj.getCallee(), obj.getCaller(), obj.getCallee());
    }
}
