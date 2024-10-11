package cn.iocoder.yudao.module.callcenter.controller.admin.siptrunk.vo;

import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.fhs.core.trans.vo.VO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 外呼线路 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SiptrunkRespVO implements VO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "线路名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("线路名称")
    private String name;

    @Schema(description = "线路号码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("线路号码")
    private String no;

    @Schema(description = "前缀")
    @ExcelProperty("前缀")
    private String prefix;

    @Schema(description = "呼叫地址")
    @ExcelProperty("呼叫地址")
    private String address;

    @Schema(description = "费率")
    @ExcelProperty("费率")
    private BigDecimal fee;

    @Schema(description = "自动加0", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("自动加0")
    private Boolean autoZero;

    @Schema(description = "线路归属地")
    @ExcelProperty("线路归属地")
    private String areaCode;

    @Schema(description = "是否启用", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否启用")
    private Boolean active;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "绑定租户ID")
    @ExcelProperty("租户ID")
    @Trans(type = TransType.SIMPLE, targetClassName = "cn.iocoder.yudao.module.system.dal.dataobject.tenant.TenantDO",
            fields = "name", ref = "tenantName")
    private Long tenantId;

    @Schema(description = "租户名称")
    @ExcelProperty("租户名称")
    private String tenantName;

    @Schema(description = "主配置")
    @ExcelProperty("主配置")
    private Boolean master;

}