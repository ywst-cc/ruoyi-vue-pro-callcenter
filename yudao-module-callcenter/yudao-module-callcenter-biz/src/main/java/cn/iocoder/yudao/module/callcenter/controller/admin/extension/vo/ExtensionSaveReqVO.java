package cn.iocoder.yudao.module.callcenter.controller.admin.extension.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - CTI 分机新增/修改 Request VO")
@Data
public class ExtensionSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "分机号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "分机号不能为空")
    private String extension;

    @Schema(description = "分机密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "分机密码不能为空")
    private String password;

    @Schema(description = "域")
    private String domain;

    @Schema(description = "所属用户ID")
    private Long ownerUserId;

    @Schema(description = "租户编号")
    private Long tenantId;

    @Schema(description = "是否启用", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否启用不能为空")
    private Boolean active;

}