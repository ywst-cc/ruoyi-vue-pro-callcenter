package cn.iocoder.yudao.module.callcenter.framework.callcenter.core.event.runnables;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.iocoder.yudao.module.callcenter.dal.dataobject.extension.ExtensionDO;
import cn.iocoder.yudao.module.callcenter.dal.dataobject.siptrunk.SiptrunkDO;
import cn.iocoder.yudao.module.callcenter.enums.CommandKey;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.cache.CdrSessionCacheDao;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.cache.SessionCacheDao;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.core.TradeMain;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.core.command.para.ParaBridge;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.core.command.para.ParaUuidKill;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.core.utils.FsUtils;
import cn.iocoder.yudao.module.callcenter.service.extension.ExtensionService;
import cn.iocoder.yudao.module.callcenter.service.siptrunk.SiptrunkService;
import lombok.extern.slf4j.Slf4j;
import org.freeswitch.esl.client.transport.event.EslEvent;

import java.math.BigDecimal;
import java.util.Map;

@Slf4j
public class ChannelParkRunnable extends AbstractEventRunnable{

    private final EslEvent eslEvent;

    private final TradeMain tradeMain;
    private final SessionCacheDao sessionCacheDao;
    private final CdrSessionCacheDao cdrSessionCacheDao;
    private final SiptrunkService siptrunkService;
    private final ExtensionService extensionService;

    public ChannelParkRunnable(EslEvent eslEvent) {
        super(eslEvent);
        this.eslEvent = eslEvent;
        this.tradeMain = SpringUtil.getBean(TradeMain.class);
        this.sessionCacheDao = SpringUtil.getBean(SessionCacheDao.class);
        this.cdrSessionCacheDao = SpringUtil.getBean(CdrSessionCacheDao.class);
        this.siptrunkService = SpringUtil.getBean(SiptrunkService.class);
        this.extensionService = SpringUtil.getBean(ExtensionService.class);
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

        String cdrSessionId = sessionCacheDao.getCdrSessionId(uuid);
        // 指定被叫uuid
        String calleeUuid = IdUtil.randomUUID();

        // 检查 caller 是否禁用
        ExtensionDO extensionDO = extensionService.getExtensionByCaller(caller);
        if (null == extensionDO || !extensionDO.getActive()){
            log.error("分机[{}]已禁用，无法外呼", caller);
            tradeMain.exec(CommandKey.UuidKill, new ParaUuidKill(uuid, null));
            return;
        }

        // 查询被叫应使用的外呼线路
        Long tenantId = cdrSessionCacheDao.getTenantId(cdrSessionId);
        SiptrunkDO siptrunkDO = siptrunkService.getTenantMasterSiptrunk(tenantId);
        if (null == siptrunkDO) {
            // todo: 放音挂机
            log.error("租户[{}]未设置主配置线路号码,执行挂机", tenantId);
            tradeMain.exec(CommandKey.UuidKill, new ParaUuidKill(uuid, null));
            return;
        }

        String no = siptrunkDO.getNo();
        String prefix = siptrunkDO.getPrefix();
        String sipAddr = siptrunkDO.getAddress();
        Long trunkId = siptrunkDO.getId();
        BigDecimal fee = siptrunkDO.getFee();
        sessionCacheDao.bind(calleeUuid, cdrSessionId);
        cdrSessionCacheDao.handlerSessionBindOtherLeg(cdrSessionId, calleeUuid, no, fee, trunkId);
        // 转发线路侧外呼
        tradeMain.exec(CommandKey.Bridge,
                new ParaBridge(
                        uuid,
                        calleeUuid,
                        cdrSessionId,
                        caller,
                        callee,
                        no,
                        prefix,
                        sipAddr
                )
        );
    }
}
