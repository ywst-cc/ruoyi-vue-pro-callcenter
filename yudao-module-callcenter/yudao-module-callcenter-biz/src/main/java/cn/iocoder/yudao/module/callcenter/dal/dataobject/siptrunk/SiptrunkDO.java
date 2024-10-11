package cn.iocoder.yudao.module.callcenter.dal.dataobject.siptrunk;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 外呼线路 DO
 *
 * @author tianyu
 */
@TableName("cti_siptrunk")
@KeySequence("cti_siptrunk_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiptrunkDO extends TenantBaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 线路名称
     */
    private String name;
    /**
     * 线路号码
     */
    private String no;
    /**
     * 前缀
     */
    private String prefix;
    /**
     * 呼叫地址
     */
    private String address;
    /**
     * 费率
     */
    private BigDecimal fee;
    /**
     * 自动加0
     */
    private Boolean autoZero;
    /**
     * 线路归属地
     */
    private String areaCode;
    /**
     * 是否启用
     */
    private Boolean active;
    /**
     * 备注
     */
    private String remark;
    /**
     * 主配置
     */
    private Boolean master;

}