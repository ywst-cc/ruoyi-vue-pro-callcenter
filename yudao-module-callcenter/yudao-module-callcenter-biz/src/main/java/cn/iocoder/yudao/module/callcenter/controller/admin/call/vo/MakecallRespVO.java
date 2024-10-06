package cn.iocoder.yudao.module.callcenter.controller.admin.call.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "呼叫中心 - 外呼接口 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MakecallRespVO {

    /**
     * 通话ID
     */
    private String callId;
}
