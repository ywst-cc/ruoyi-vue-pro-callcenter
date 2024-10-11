package cn.iocoder.yudao.module.callcenter.dal.dataobject.cdr;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 话务数据 DO
 *
 * @author tianyu
 */
@TableName("cti_cdr")
@KeySequence("cti_cdr_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CdrDO extends TenantBaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 话单ID
     */
    private String callId;
    /**
     * 主叫uuid
     */
    private String callerUuid;
    /**
     * 被叫uuid
     */
    private String calleeUuid;
    /**
     * 主叫号码
     */
    private String caller;
    /**
     * 被叫号码
     */
    private String callee;
    /**
     * 线路号码
     */
    private String pstn;
    /**
     * 备用线路号码
     */
    private String otherPstn;
    /**
     * 呼叫方向
     */
    private String direction;
    /**
     * 呼叫类型
     */
    private String calltype;
    /**
     * 呼叫开始时间
     */
    private LocalDateTime startTime;
    /**
     * 被叫振铃时间
     */
    private LocalDateTime ringTime;
    /**
     * 被叫应答时间
     */
    private LocalDateTime answerTime;
    /**
     * 被叫挂机时间
     */
    private LocalDateTime endTime;
    /**
     * 进入IVR时间
     */
    private LocalDateTime ivrTime;
    /**
     * 进入ACD时间
     */
    private LocalDateTime acdTime;
    /**
     * 通话时长(秒)
     */
    private Integer billSec;
    /**
     * 通话时长（分）
     */
    private Integer billMin;
    /**
     * 振铃时长
     */
    private Integer ringSec;
    /**
     * 持续时长
     */
    private Integer duration;
    /**
     * 是否接通
     */
    private Boolean answered;
    /**
     * 呼叫坐席
     */
    private Long userId;
    /**
     * 录音文件
     */
    private String recordFile;
    /**
     * 被叫归属地
     */
    private String areaCode;
    /**
     * 业务透传字段
     */
    private String extra;
    /**
     * 计费费率
     */
    private BigDecimal fee;
    /**
     * 计费金额
     */
    private BigDecimal price;
    /**
     * 线路ID
     */
    private Long siptrunkId;

}