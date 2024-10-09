package cn.iocoder.yudao.module.callcenter.framework.callcenter.core.utils;

import java.util.Map;

public class FsUtils {

    public static final String CALLTYPE_API = "api";

    /**
     * 获取当前channel的uuid
     * @param map fs通道变量
     * @return uuid
     */
    public static String getUuid(Map<String, String> map) {
        return map.getOrDefault("Unique-ID", "");
    }

    /**
     * 获取当前channel的呼叫类型，是api调用呼叫回呼还是话机直呼
     * @param map fs通道变量
     * @return 呼叫类型
     */
    public static String getMyCallType(Map<String, String> map) {
        return map.getOrDefault("variable_my_calltype", "");
    }

    /**
     * 获取电话的主叫号码
     * @param map fs通道变量
     * @return caller
     */
    public static String getCaller(Map<String, String> map) {
        String caller = "";
        if (CALLTYPE_API.equals(getMyCallType(map))) {
            caller = map.getOrDefault("variable_my_caller", "");
        }else {
            caller = map.getOrDefault("Caller-ANI", "");
        }
        return caller;
    }

    /**
     * 获取电话的被叫号码
     * @param map fs通道变量
     * @return callee
     */
    public static String getCallee(Map<String, String> map) {
        String callee = "";
        if (CALLTYPE_API.equals(getMyCallType(map))) {
            callee = map.getOrDefault("variable_my_callee", "");
        }else {
            callee = map.getOrDefault("Caller-Destination-Number", "");
        }
        return callee;
    }

}
