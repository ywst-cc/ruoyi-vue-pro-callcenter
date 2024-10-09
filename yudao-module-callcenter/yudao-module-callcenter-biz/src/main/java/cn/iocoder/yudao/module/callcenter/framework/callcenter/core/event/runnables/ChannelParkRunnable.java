package cn.iocoder.yudao.module.callcenter.framework.callcenter.core.event.runnables;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.iocoder.yudao.module.callcenter.enums.CommandKey;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.core.TradeMain;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.core.command.para.ParaBridge;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.core.command.para.ParaUuidKill;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.core.utils.FsUtils;
import lombok.extern.slf4j.Slf4j;
import org.freeswitch.esl.client.transport.event.EslEvent;

import java.util.Map;

@Slf4j
public class ChannelParkRunnable extends AbstractEventRunnable{

    private final EslEvent eslEvent;

    private TradeMain tradeMain;

    public ChannelParkRunnable(EslEvent eslEvent) {
        super(eslEvent);
        this.eslEvent = eslEvent;
        this.tradeMain = SpringUtil.getBean(TradeMain.class);
    }

    @Override
    public void handle() {
        Map<String, String> eventHeaders = eslEvent.getEventHeaders();
        // 当前channel uuid
        String uuid = FsUtils.getUuid(eventHeaders);
        // 获取主叫/被叫号码
        String caller = FsUtils.getCaller(eventHeaders);
        String callee = FsUtils.getCallee(eventHeaders);

        log.info("handle park event, current uuid:[{}] - caller:[{}] - callee:[{}] ", uuid, caller, callee);

        String cdrSessionId = "";
        String calleeUuid = IdUtil.randomUUID();

        //tradeMain.exec(CommandKey.UuidKill, new ParaUuidKill(uuid, null));
        // 转发线路侧外呼
        tradeMain.exec(CommandKey.Bridge, new ParaBridge(uuid, calleeUuid, cdrSessionId, caller, callee, "051287660010", "", "192.168.0.100:5080"));
    }
}
