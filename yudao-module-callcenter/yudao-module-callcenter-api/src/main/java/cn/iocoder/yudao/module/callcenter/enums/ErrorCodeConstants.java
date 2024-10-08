package cn.iocoder.yudao.module.callcenter.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * CRM 错误码枚举类
 * <p>
 * callcenter 系统，使用 1-100-000-000 段
 */
public interface ErrorCodeConstants {

    // ========== 呼叫中心 1-100-000-000 ==========
    ErrorCode EXTENSION_NOT_EXISTS = new ErrorCode(1-100-000-000, "CTI 分机不存在");

}
