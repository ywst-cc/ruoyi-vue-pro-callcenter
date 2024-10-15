package cn.iocoder.yudao.module.callcenter.controller.admin.call.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "呼叫中心 - 挂机接口 Request VO")
@Data
public class HangupReqVO {

    @Schema(description = "话单ID")
    @NotNull
    private String callId;

}
