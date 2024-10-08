package cn.iocoder.yudao.module.callcenter.dal.dataobject.extension;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * CTI 分机 DO
 *
 * @author tianyu
 */
@TableName("cti_extension")
@KeySequence("cti_extension_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtensionDO extends TenantBaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 分机号
     */
    private String extension;
    /**
     * 分机密码
     */
    private String password;
    /**
     * 域
     */
    private String domain;
    /**
     * 所属用户ID
     */
    private Long ownerUserId;
    /**
     * 是否启用
     */
    private Boolean active;

}