package cn.iocoder.yudao.module.callcenter.controller.admin.extension;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.*;
import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.callcenter.controller.admin.extension.vo.*;
import cn.iocoder.yudao.module.callcenter.dal.dataobject.extension.ExtensionDO;
import cn.iocoder.yudao.module.callcenter.service.extension.ExtensionService;

@Tag(name = "管理后台 - CTI 分机")
@RestController
@RequestMapping("/callcenter/extension")
@Validated
public class ExtensionController {

    @Resource
    private ExtensionService extensionService;

    @PostMapping("/create")
    @Operation(summary = "创建CTI 分机")
    @PreAuthorize("@ss.hasPermission('callcenter:extension:create')")
    public CommonResult<Long> createExtension(@Valid @RequestBody ExtensionSaveReqVO createReqVO) {
        return success(extensionService.createExtension(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新CTI 分机")
    @PreAuthorize("@ss.hasPermission('callcenter:extension:update')")
    public CommonResult<Boolean> updateExtension(@Valid @RequestBody ExtensionSaveReqVO updateReqVO) {
        extensionService.updateExtension(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除CTI 分机")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('callcenter:extension:delete')")
    public CommonResult<Boolean> deleteExtension(@RequestParam("id") Long id) {
        extensionService.deleteExtension(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得CTI 分机")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('callcenter:extension:query')")
    public CommonResult<ExtensionRespVO> getExtension(@RequestParam("id") Long id) {
        ExtensionDO extension = extensionService.getExtension(id);
        return success(BeanUtils.toBean(extension, ExtensionRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得CTI 分机分页")
    @PreAuthorize("@ss.hasPermission('callcenter:extension:query')")
    public CommonResult<PageResult<ExtensionRespVO>> getExtensionPage(@Valid ExtensionPageReqVO pageReqVO) {
        PageResult<ExtensionDO> pageResult = extensionService.getExtensionPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ExtensionRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出CTI 分机 Excel")
    @PreAuthorize("@ss.hasPermission('callcenter:extension:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportExtensionExcel(@Valid ExtensionPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ExtensionDO> list = extensionService.getExtensionPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "CTI 分机.xls", "数据", ExtensionRespVO.class,
                        BeanUtils.toBean(list, ExtensionRespVO.class));
    }

}