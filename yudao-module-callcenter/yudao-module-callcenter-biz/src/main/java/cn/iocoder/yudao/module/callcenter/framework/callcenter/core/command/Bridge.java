package cn.iocoder.yudao.module.callcenter.framework.callcenter.core.command;

import cn.iocoder.yudao.module.callcenter.framework.callcenter.core.command.para.ParaBridge;
import org.apache.commons.lang3.StringUtils;

public class Bridge extends AbstractCommand<ParaBridge> {
    @Override
    public String getCommand(ParaBridge obj) {

        // 录音文件处理
        String recordStr = String.format("execute_on_answer='record_session $${recordings_dir}/${strftime(%%Y%%m%%d)}/${strftime(%%Y%%m%%d%%H%%M%%S)}_%s_%s_%s.mp3'",
                obj.getCdrSessionId(), obj.getCaller(), obj.getCallee());

        String data = String.format("{%s,origination_uuid=%s,origination_caller_id_number=%s,origination_caller_id_name=%s}sofia/external/%s%s@%s",
                recordStr, obj.getCalleeUuid(), obj.getNo(), obj.getNo(), StringUtils.isNotBlank(obj.getPrefix())?obj.getPrefix():"", obj.getCallee(), obj.getSipAddress());

        return String.format("uuid_transfer %s bridge:%s inline", obj.getUuid(), data.replace(",", "\\,"));
    }
}
