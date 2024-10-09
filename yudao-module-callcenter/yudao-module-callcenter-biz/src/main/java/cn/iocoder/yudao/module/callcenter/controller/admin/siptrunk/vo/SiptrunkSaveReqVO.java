package cn.iocoder.yudao.module.callcenter.controller.admin.siptrunk.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 外呼线路新增/修改 Request VO")
@Data
public class SiptrunkSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "线路名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "线路名称不能为空")
    private String name;

    @Schema(description = "线路号码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "线路号码不能为空")
    private String no;

    @Schema(description = "前缀")
    private String prefix;

    @Schema(description = "呼叫地址")
    private String address;

    @Schema(description = "费率")
    private BigDecimal fee;

    @Schema(description = "自动加0", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "自动加0不能为空")
    private Boolean autoZero;

    @Schema(description = "线路归属地")
    private String areaCode;

    @Schema(description = "是否启用", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否启用不能为空")
    private Boolean active;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "租户编号")
    private Long tenantId;

}