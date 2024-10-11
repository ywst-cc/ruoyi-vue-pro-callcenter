package cn.iocoder.yudao.module.callcenter.framework.callcenter.core.event.runnables;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.iocoder.yudao.module.callcenter.dal.dataobject.cdr.CdrDO;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.cache.CdrSessionCacheDao;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.cache.SessionCacheDao;
import cn.iocoder.yudao.module.callcenter.framework.callcenter.core.utils.FsUtils;
import cn.iocoder.yudao.module.callcenter.service.cdr.CdrService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.freeswitch.esl.client.transport.event.EslEvent;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;

@Slf4j
public class ChannelHangupCompleteRunnable extends AbstractEventRunnable{

    private final EslEvent eslEvent;
    private final RedissonClient redissonClient;
    private final SessionCacheDao sessionCacheDao;
    private final CdrSessionCacheDao cdrSessionCacheDao;
    private final CdrService cdrService;

    public ChannelHangupCompleteRunnable(EslEvent eslEvent) {
        super(eslEvent);
        this.eslEvent = eslEvent;
        this.redissonClient = SpringUtil.getBean(RedissonClient.class);
        this.sessionCacheDao = SpringUtil.getBean(SessionCacheDao.class);
        this.cdrSessionCacheDao = SpringUtil.getBean(CdrSessionCacheDao.class);
        this.cdrService = SpringUtil.getBean(CdrService.class);
    }

    @Override
    public void handle() {
        Map<String, String> eventHeaders = eslEvent.getEventHeaders();

        String uuid = FsUtils.getUuid(eventHeaders);
        String cdrSessionId = sessionCacheDao.getCdrSessionId(uuid);

        // 设置当前 uuid end_time
        String eventDateLocal = FsUtils.getEventDateLocal(eventHeaders);
        cdrSessionCacheDao.handlerSetEndTime(cdrSessionId, uuid, eventDateLocal);

        // cdrSessionId 加锁
        RLock runLock = redissonClient.getFairLock("CDR:SAVE_CDR:" + cdrSessionId);
        runLock.lock();
        try {
            if (cdrSessionCacheDao.allLegsHangup(cdrSessionId)) {
                // check cdr is saved
                if (!cdrSessionCacheDao.isCdrSaved(cdrSessionId)){
                    // save cdr
                    saveCdr(cdrSessionId);
                    cdrSessionCacheDao.setCdrSaved(cdrSessionId);
                    // delete cache
                    cdrSessionCacheDao.removeCdrSessionCache(cdrSessionId);
                }
            }
        }catch (Exception e) {
            log.error("channel hangup handler exception. [{}]", ExceptionUtil.stacktraceToOneLineString(e));
        }finally {
            runLock.unlock();
        }

        // 解绑session cache
        sessionCacheDao.unbind(uuid);
    }


    /**
     * 保存话单
     */
    private void saveCdr(String cdrSessionId) {
        CdrDO cdrDO = new CdrDO();
        cdrDO.setCallId(cdrSessionId);
        cdrDO.setTenantId(cdrSessionCacheDao.getTenantId(cdrSessionId));
        cdrDO.setCallerUuid(cdrSessionCacheDao.getCallerUUID(cdrSessionId));
        cdrDO.setCalleeUuid(cdrSessionCacheDao.getCalleeUUID(cdrSessionId));
        cdrDO.setCaller(cdrSessionCacheDao.getLegAni(cdrSessionId));
        cdrDO.setCallee(cdrSessionCacheDao.getLegDnis(cdrSessionId));
        cdrDO.setPstn(cdrSessionCacheDao.getPstn(cdrSessionId));
        cdrDO.setDirection(cdrSessionCacheDao.getDirection(cdrSessionId));
        // 设置归属地
        // cdrDO.setAreaCode();

        // user_id
        cdrDO.setUserId(cdrSessionCacheDao.getUserId(cdrSessionId));
        cdrDO.setExtra(cdrSessionCacheDao.getExtra(cdrSessionId));

        // 设置开始时间
        String startTime = cdrSessionCacheDao.getStartTime(cdrSessionId, CdrSessionCacheDao.Role.A.name());
        cdrDO.setStartTime(LocalDateTimeUtil.parse(startTime, DatePattern.NORM_DATETIME_PATTERN));

        // 设置振铃时间
        String otherLegStartTime = cdrSessionCacheDao.getStartTime(cdrSessionId, CdrSessionCacheDao.Role.B.name());
        if (StringUtils.isNotBlank(otherLegStartTime)){
            cdrDO.setRingTime(LocalDateTimeUtil.parse(otherLegStartTime, DatePattern.NORM_DATETIME_PATTERN));
        }

        // 设置被叫接通时间
        String otherLegAnswerTime = cdrSessionCacheDao.getAnswerTime(cdrSessionId, CdrSessionCacheDao.Role.B.name());
        if (StringUtils.isNotBlank(otherLegAnswerTime)){
            cdrDO.setAnswerTime(LocalDateTimeUtil.parse(otherLegAnswerTime, DatePattern.NORM_DATETIME_PATTERN));
            cdrDO.setAnswered(true);
        }else {
            cdrDO.setAnswered(false);
        }

        // 设置挂机时间
        String endTime = cdrSessionCacheDao.getEndTime(cdrSessionId, CdrSessionCacheDao.Role.A.name());
        String otherLegEndTime = cdrSessionCacheDao.getEndTime(cdrSessionId, CdrSessionCacheDao.Role.B.name());
        if (StringUtils.isNotBlank(otherLegEndTime)) {
            cdrDO.setEndTime(LocalDateTimeUtil.parse(otherLegEndTime, DatePattern.NORM_DATETIME_PATTERN));
        }else {
            cdrDO.setEndTime(LocalDateTimeUtil.parse(endTime, DatePattern.NORM_DATETIME_PATTERN));
        }

        // 计算通话时长,振铃时长
        int ringSec = 0;
        int billSec = 0;
        if (StringUtils.isNotBlank(otherLegAnswerTime)) {
            Duration ring = Duration.between(LocalDateTimeUtil.parse(otherLegStartTime, DatePattern.NORM_DATETIME_PATTERN),
                    LocalDateTimeUtil.parse(otherLegAnswerTime, DatePattern.NORM_DATETIME_PATTERN));
            ringSec = (int) ring.getSeconds();

            Duration bill = Duration.between(LocalDateTimeUtil.parse(otherLegAnswerTime, DatePattern.NORM_DATETIME_PATTERN),
                    LocalDateTimeUtil.parse(otherLegEndTime, DatePattern.NORM_DATETIME_PATTERN));
            billSec = (int) bill.getSeconds();
        }else if (StringUtils.isNotBlank(otherLegStartTime)) {
            Duration ring = Duration.between(LocalDateTimeUtil.parse(otherLegStartTime, DatePattern.NORM_DATETIME_PATTERN),
                    LocalDateTimeUtil.parse(endTime, DatePattern.NORM_DATETIME_PATTERN));
            ringSec = (int) ring.getSeconds();
        }
        cdrDO.setRingSec(ringSec);
        cdrDO.setBillSec(billSec);
        cdrDO.setBillMin((billSec + 59) / 60);

        // 计算价格
        BigDecimal fee = cdrSessionCacheDao.getFee(cdrSessionId);
        BigDecimal price = fee.multiply(new BigDecimal(cdrDO.getBillMin()));
        cdrDO.setFee(fee);
        cdrDO.setPrice(price);
        cdrDO.setSiptrunkId(cdrSessionCacheDao.getSiptrunkId(cdrSessionId));

        // 设置录音文件地址
        cdrDO.setRecordFile(cdrSessionCacheDao.getRecordFilePath(cdrSessionId));

        cdrService.createCdr(cdrDO);
    }

}
