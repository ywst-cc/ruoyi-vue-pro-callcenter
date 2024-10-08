package cn.iocoder.yudao.module.callcenter.controller.admin.extension.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - CTI 分机分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ExtensionPageReqVO extends PageParam {

    @Schema(description = "分机号")
    private String extension;

    @Schema(description = "域")
    private String domain;

    @Schema(description = "所属用户ID")
    private Long ownerUserId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "租户编号")
    private Long tenantId;

    @Schema(description = "是否启用")
    private Boolean active;

}