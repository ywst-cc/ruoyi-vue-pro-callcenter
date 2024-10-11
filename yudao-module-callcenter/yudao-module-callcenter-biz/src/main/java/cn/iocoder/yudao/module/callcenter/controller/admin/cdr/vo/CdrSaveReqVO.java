package cn.iocoder.yudao.module.callcenter.controller.admin.cdr.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 话务数据新增/修改 Request VO")
@Data
public class CdrSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "话单ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "话单ID不能为空")
    private String callId;

    @Schema(description = "主叫uuid")
    private String callerUuid;

    @Schema(description = "被叫uuid")
    private String calleeUuid;

    @Schema(description = "主叫号码")
    private String caller;

    @Schema(description = "被叫号码")
    private String callee;

    @Schema(description = "线路号码")
    private String pstn;

    @Schema(description = "备用线路号码")
    private String otherPstn;

    @Schema(description = "呼叫方向")
    private String direction;

    @Schema(description = "呼叫类型")
    private String calltype;

    @Schema(description = "呼叫开始时间")
    private LocalDateTime startTime;

    @Schema(description = "被叫振铃时间")
    private LocalDateTime ringTime;

    @Schema(description = "被叫应答时间")
    private LocalDateTime answerTime;

    @Schema(description = "被叫挂机时间")
    private LocalDateTime endTime;

    @Schema(description = "进入IVR时间")
    private LocalDateTime ivrTime;

    @Schema(description = "进入ACD时间")
    private LocalDateTime acdTime;

    @Schema(description = "通话时长(秒)")
    private Integer billSec;

    @Schema(description = "通话时长（分）")
    private Integer billMin;

    @Schema(description = "振铃时长")
    private Integer ringSec;

    @Schema(description = "持续时长")
    private Integer duration;

    @Schema(description = "是否接通")
    private Boolean answered;

    @Schema(description = "呼叫坐席")
    private Long userId;

    @Schema(description = "录音文件")
    private String recordFile;

    @Schema(description = "被叫归属地")
    private String areaCode;

    @Schema(description = "业务透传字段")
    private String extra;

    @Schema(description = "计费费率")
    private BigDecimal fee;

    @Schema(description = "计费金额")
    private BigDecimal price;

    @Schema(description = "线路ID")
    private Long siptrunkId;

}