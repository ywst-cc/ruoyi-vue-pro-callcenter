package cn.iocoder.yudao.module.callcenter.controller.admin.call;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.callcenter.controller.admin.call.vo.MakecallReqVO;
import cn.iocoder.yudao.module.callcenter.controller.admin.call.vo.MakecallRespVO;
import cn.iocoder.yudao.module.callcenter.service.CallService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "呼叫中心 - Call 电话接口")
@RestController
@RequestMapping("/call")
@Validated
public class CallController {

    @Resource
    private CallService callService;

    // 呼叫http接口
    @PostMapping("/makecall")
    @Operation(summary = "发起呼叫")
    //@PreAuthorize("@ss.hasAnyPermissions('')")
    public CommonResult<MakecallRespVO> makecall(@Valid @RequestBody MakecallReqVO reqVO) {
        return CommonResult.success(new MakecallRespVO(callService.makecall(getLoginUserId(), reqVO)));
    }

}
