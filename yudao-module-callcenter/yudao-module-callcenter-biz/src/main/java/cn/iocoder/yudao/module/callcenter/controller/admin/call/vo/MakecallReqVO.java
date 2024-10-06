package cn.iocoder.yudao.module.callcenter.controller.admin.call.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "呼叫中心 - 外呼接口 Request VO")
@Data
public class MakecallReqVO {

    @Schema(description = "被叫号码", example = "13988888888")
    @NotNull(message = "被叫号码不能为空")
    private String callee;

    @Schema(description = "业务透传字段")
    private String extra;

}
