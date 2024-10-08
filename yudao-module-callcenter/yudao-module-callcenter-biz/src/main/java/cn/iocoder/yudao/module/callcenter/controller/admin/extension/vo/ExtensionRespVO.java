package cn.iocoder.yudao.module.callcenter.controller.admin.extension.vo;

import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.fhs.core.trans.vo.VO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - CTI 分机 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ExtensionRespVO implements VO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "分机号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("分机号")
    private String extension;

    @Schema(description = "分机密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("分机密码")
    private String password;

    @Schema(description = "域")
    @ExcelProperty("域")
    private String domain;

    @Schema(description = "所属用户ID")
    @ExcelProperty("所属用户ID")
    @Trans(type = TransType.SIMPLE, targetClassName = "cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO",
            fields = "nickname", ref = "ownerUserName")
    private Long ownerUserId;

    @Schema(description = "所属用户")
    @ExcelProperty("所属用户")
    private String ownerUserName;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "是否启用", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否启用")
    private Boolean active;

    @Schema(description = "绑定租户ID")
    @ExcelProperty("租户ID")
    @Trans(type = TransType.SIMPLE, targetClassName = "cn.iocoder.yudao.module.system.dal.dataobject.tenant.TenantDO",
            fields = "name", ref = "tenantName")
    private Long tenantId;

    @Schema(description = "租户名称")
    @ExcelProperty("租户名称")
    private String tenantName;
}