package cn.iocoder.yudao.module.callcenter.framework.callcenter.cache;

import cn.iocoder.yudao.module.callcenter.dal.dataobject.extension.ExtensionDO;
import cn.iocoder.yudao.module.callcenter.service.extension.ExtensionService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class CdrSessionCacheDao {

    public static final String CDR_SESSION_CACHE_KEY = "cdr_session_cache:%s";

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private ExtensionService extensionService;

    public enum Role {
        A, B, UNKNOWN
    }

    public enum Direction {
        INBOUND, OUTBOUND, UNKNOWN
    }

    private static String formatKey(String cdrSessionId) {
        return String.format(CDR_SESSION_CACHE_KEY, cdrSessionId);
    }

    /**
     * 设置主叫号码
     * @param cdrSessionId
     * @param caller
     */
    private void setLegAni(String cdrSessionId, String caller) {
        stringRedisTemplate.opsForHash().put(formatKey(cdrSessionId),"LEG_ANI", caller);
    }

    public String getLegAni(String cdrSessionId) {
        return (String) stringRedisTemplate.opsForHash().get(formatKey(cdrSessionId),"LEG_ANI");
    }

    private void setLegDnis(String cdrSessionId, String callee) {
        stringRedisTemplate.opsForHash().put(formatKey(cdrSessionId),"LEG_DNIS", callee);
    }

    public String getLegDnis(String cdrSessionId) {
        return (String) stringRedisTemplate.opsForHash().get(formatKey(cdrSessionId),"LEG_DNIS");
    }

    private void setPstn(String cdrSessionId, String pstn) {
        stringRedisTemplate.opsForHash().put(formatKey(cdrSessionId),"PSTN", pstn);
    }

    public String getPstn(String cdrSessionId) {
        return (String) stringRedisTemplate.opsForHash().get(formatKey(cdrSessionId),"PSTN");
    }

    private void setUserId(String cdrSessionId, Long userId) {
        stringRedisTemplate.opsForHash().put(formatKey(cdrSessionId),"USER_ID", String.valueOf(userId));
    }

    public Long getUserId(String cdrSessionId) {
        String userId = (String) stringRedisTemplate.opsForHash().get(formatKey(cdrSessionId),"USER_ID");
        return StringUtils.isBlank(userId) ? null : Long.valueOf(userId);
    }

    private void setSiptrunkId(String cdrSessionId, Long siptrunkId) {
        stringRedisTemplate.opsForHash().put(formatKey(cdrSessionId),"SIPTRUNK_ID", String.valueOf(siptrunkId));
    }

    public Long getSiptrunkId(String cdrSessionId) {
        String siptrunkId = (String) stringRedisTemplate.opsForHash().get(formatKey(cdrSessionId),"SIPTRUNK_ID");
        return StringUtils.isBlank(siptrunkId) ? null : Long.valueOf(siptrunkId);
    }

    private void setFee(String cdrSessionId, BigDecimal fee) {
        stringRedisTemplate.opsForHash().put(formatKey(cdrSessionId),"FEE", fee.toString());
    }

    public BigDecimal getFee(String cdrSessionId) {
        String fee = (String) stringRedisTemplate.opsForHash().get(formatKey(cdrSessionId),"FEE");
        return StringUtils.isBlank(fee) ? null : new BigDecimal(fee);
    }

    /**
     * 设置租户ID
     * @param cdrSessionId
     * @param tenantId
     */
    private void setTenantId(String cdrSessionId, Long tenantId) {
        stringRedisTemplate.opsForHash().put(formatKey(cdrSessionId),"TENANT_ID", String.valueOf(tenantId));
    }

    private void setDirection(String cdrSessionId, String direction) {
        stringRedisTemplate.opsForHash().put(formatKey(cdrSessionId),"DIRECTION", direction);
    }

    public String getDirection(String cdrSessionId) {
        return (String) stringRedisTemplate.opsForHash().get(formatKey(cdrSessionId),"DIRECTION");
    }

    private void setUUID(String cdrSessionId, String uuid, Role role) {
        stringRedisTemplate.opsForHash().put(formatKey(cdrSessionId),"UUID_" + role.name(), uuid);
    }

    private void setCallerUUID(String cdrSessionId, String uuid) {
        stringRedisTemplate.opsForHash().put(formatKey(cdrSessionId),"CALLER_UUID", uuid);
    }

    public String getCallerUUID(String cdrSessionId) {
        return (String) stringRedisTemplate.opsForHash().get(formatKey(cdrSessionId),"CALLER_UUID");
    }

    private void setCalleeUUID(String cdrSessionId, String uuid) {
        stringRedisTemplate.opsForHash().put(formatKey(cdrSessionId),"CALLEE_UUID", uuid);
    }

    public String getCalleeUUID(String cdrSessionId) {
        return (String) stringRedisTemplate.opsForHash().get(formatKey(cdrSessionId),"CALLEE_UUID");
    }

    public String getStartTime(String cdrSessionId, String role) {
        return (String) stringRedisTemplate.opsForHash().get(formatKey(cdrSessionId), "START_TIME_" + role);
    }

    private void setStartTime(String cdrSessionId, String role, String time) {
        stringRedisTemplate.opsForHash().put(formatKey(cdrSessionId),"START_TIME_" + role, time);
    }

    public String getAnswerTime(String cdrSessionId, String role) {
        return (String) stringRedisTemplate.opsForHash().get(formatKey(cdrSessionId), "ANSWER_TIME_" + role);
    }

    private void setAnswerTime(String cdrSessionId, String role, String time) {
        stringRedisTemplate.opsForHash().put(formatKey(cdrSessionId),"ANSWER_TIME_" + role, time);
    }

    public String getEndTime(String cdrSessionId, String role) {
        return (String) stringRedisTemplate.opsForHash().get(formatKey(cdrSessionId), "END_TIME_" + role);
    }

    private void setEndTime(String cdrSessionId, String role, String time) {
        stringRedisTemplate.opsForHash().put(formatKey(cdrSessionId),"END_TIME_" + role, time);
    }

    private String getUUIDByRole(String cdrSessionId, String role) {
        return (String) stringRedisTemplate.opsForHash().get(formatKey(cdrSessionId), "UUID_" + role);
    }

    private void setExtra(String cdrSessionId, String extra) {
        stringRedisTemplate.opsForHash().put(formatKey(cdrSessionId),"EXTRA", extra);
    }

    public String getExtra(String cdrSessionId) {
        return (String) stringRedisTemplate.opsForHash().get(formatKey(cdrSessionId), "EXTRA");
    }

    /**
     * 判断通话是否存在
     * @param cdrSessionId 话单ID
     * @return 通话是否存在
     */
    public boolean sessionIsExists(String cdrSessionId) {
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(formatKey(cdrSessionId)));
    }


    /**
     * 移除cdr session cache
     * @param cdrSessionId
     */
    public void removeCdrSessionCache(String cdrSessionId) {
        stringRedisTemplate.delete(formatKey(cdrSessionId));
    }

    /**
     * 获取当前uuid对应的role
     * @param cdrSessionId 话单ID
     * @param uuid
     * @return
     */
    private Role getRole(String cdrSessionId, String uuid) {
        if (uuid.equals(getUUIDByRole(cdrSessionId, Role.A.name()))){
            return Role.A;
        }else if (uuid.equals(getUUIDByRole(cdrSessionId, Role.B.name()))){
            return Role.B;
        }else {
            return Role.UNKNOWN;
        }
    }

    /**
     * 根据当前uuid role是否已经挂机
     * @param cdrSessionId 话单ID
     * @param role 角色类型
     * @return
     */
    private boolean legIsHangup(String cdrSessionId, String role) {
        return StringUtils.isBlank(getStartTime(cdrSessionId, role)) || StringUtils.isNotBlank(getEndTime(cdrSessionId, role));
    }


    /**
     * 判断是否所有的leg都已经挂机
     * @param cdrSessionId 话单ID
     * @return
     */
    public boolean allLegsHangup(String cdrSessionId) {
        return legIsHangup(cdrSessionId, Role.A.name()) && legIsHangup(cdrSessionId, Role.B.name());
    }

    /**
     * 处理呼叫会话创建
     * @param cdrSessionId 话单ID
     * @param caller 主叫
     * @param callee 被叫
     * @param uuid channel id
     */
    public void handlerSessionCreate(String cdrSessionId, String caller, String callee, String uuid, String extra) {
        setUUID(cdrSessionId, uuid, Role.A);
        setCallerUUID(cdrSessionId, uuid);
        setLegAni(cdrSessionId, caller);
        setLegDnis(cdrSessionId, callee);
        setDirection(cdrSessionId, Direction.OUTBOUND.name());
        if (StringUtils.isNotBlank(extra)) {
            setExtra(cdrSessionId, extra);
        }

        // 根据主叫号码查询 租户ID
        ExtensionDO extensionDO = extensionService.getExtensionByCaller(caller);
        if (null != extensionDO) {
            setTenantId(cdrSessionId, extensionDO.getTenantId());
            setUserId(cdrSessionId, extensionDO.getId());
        }
    }

    public void handlerSessionBindOtherLeg(String cdrSessionId, String uuid, String pstn, BigDecimal fee, Long trunkId) {
        setUUID(cdrSessionId, uuid, Role.B);
        setCalleeUUID(cdrSessionId, uuid);
        setPstn(cdrSessionId, pstn);
        setFee(cdrSessionId, fee);
        setSiptrunkId(cdrSessionId, trunkId);
    }

    public void handlerSetCreateTime(String cdrSessionId, String uuid, String time) {
        Role role = getRole(cdrSessionId, uuid);
        setStartTime(cdrSessionId, role.name(), time);
    }

    public void handlerSetAnswerTime(String cdrSessionId, String uuid, String time) {
        Role role = getRole(cdrSessionId, uuid);
        setAnswerTime(cdrSessionId, role.name(), time);
    }

    public void handlerSetEndTime(String cdrSessionId, String uuid, String time) {
        Role role = getRole(cdrSessionId, uuid);
        setEndTime(cdrSessionId, role.name(), time);
    }

    public void setCdrSaved(String cdrSessionId) {
        stringRedisTemplate.opsForHash().put(formatKey(cdrSessionId),"CDR_SAVED", String.valueOf(true));
    }

    public boolean isCdrSaved(String cdrSessionId) {
        return Boolean.parseBoolean((String) stringRedisTemplate.opsForHash().get(formatKey(cdrSessionId),"CDR_SAVED"));
    }

    public Long getTenantId(String cdrSessionId) {
        String tenantId = (String) Objects.requireNonNull(stringRedisTemplate.opsForHash().get(formatKey(cdrSessionId), "TENANT_ID"));
        return StringUtils.isBlank(tenantId) ? null : Long.valueOf(tenantId);
    }

    public void setRecordFilePath(String cdrSessionId, String recordFilePath) {
        stringRedisTemplate.opsForHash().put(formatKey(cdrSessionId),"RECORD_FILE_PATH", recordFilePath);
    }

    public String getRecordFilePath(String cdrSessionId) {
        return (String) stringRedisTemplate.opsForHash().get(formatKey(cdrSessionId), "RECORD_FILE_PATH");
    }
}
