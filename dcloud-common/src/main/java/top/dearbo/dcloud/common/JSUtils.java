package top.dearbo.dcloud.common;

import java.util.Collection;

import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.util.JSUtil;
import top.dearbo.common.entity.AjaxResult;
import top.dearbo.common.util.JsonUtil;

/**
 * @fileName: JSUtils.java
 * @author: Bo
 * @createDate: 2018/9/12 15:57
 * @description: 扩展原生的 JSUtil
 */
public class JSUtils extends JSUtil {

    public static String wrapJsVarData(Object data) {
        return wrapJsVar(new AjaxResult(data));
    }

    public static String wrapJsVar(AjaxResult ajaxResult) {
        return wrapJsVarString(ajaxResult == null ? "{}" : ajaxResult.toString());
    }

    public static String wrapJsVar(Collection value) {
        return wrapJsVarString(JsonUtil.toJson(value));
    }

    public static String wrapJsVar(int value) {
        return wrapJsVarString(String.valueOf(value));
    }

    /**
     * 参数说明：
     *
     * @param value 要返回到JS层的值
     *              isString：返回值类型是否为原始字符串,默认[false]
     * @return 值
     */
    public static String wrapJsVarString(String value) {
        return wrapJsVar(value, false);
    }

    ///////////异步回调/////////////

    /**
     * 参数说明:
     *
     * @param iWebview      扩展插件方法运行的窗口
     * @param callbackId    回调函数的唯一标识
     * @param value         回调函数的参数
     * @param pKeepCallback 是否可多次触发回调函数
     * @param status        操作是否成功，成功则使用JSUtil.OK，否则使用错误代码
     * @param isJson        回调函数参数是否为JSON数据
     */
    public static void execCallbackString(IWebview iWebview, String callbackId, String value, boolean pKeepCallback, int status, boolean isJson) {
        execCallback(iWebview, callbackId, value, status, isJson, pKeepCallback);
    }

    public static void execCallback(IWebview iWebview, String callbackId, AjaxResult ajaxResult, boolean pKeepCallback, int status) {
        execCallbackString(iWebview, callbackId, ajaxResult == null ? "{}" : ajaxResult.toString(), pKeepCallback, status, true);
    }

    public static void execCallback(IWebview iWebview, String callbackId, AjaxResult ajaxResult, int status) {
        execCallback(iWebview, callbackId, ajaxResult, false, status);
    }

    public static void execCallbackOkData(IWebview iWebview, String callbackId, Object data) {
        execCallbackOk(iWebview, callbackId, new AjaxResult(data), false);
    }

    public static void execCallbackOk(IWebview iWebview, String callbackId, Collection value) {
        execCallbackString(iWebview, callbackId, JsonUtil.toJson(value), false, JSUtil.OK, true);
    }

    public static void execCallbackOk(IWebview iWebview, String callbackId, AjaxResult ajaxResult) {
        execCallbackOk(iWebview, callbackId, ajaxResult, false);
    }

    public static void execCallbackOk(IWebview iWebview, String callbackId, AjaxResult ajaxResult, boolean pKeepCallback) {
        execCallback(iWebview, callbackId, ajaxResult, pKeepCallback, JSUtil.OK);
    }

    public static void execCallbackErrorData(IWebview iWebview, String callbackId, Object data) {
        execCallback(iWebview, callbackId, new AjaxResult(data), JSUtil.ERROR);
    }

    ////////////执行js函数/////////

    public static void evalJsFun(IWebview iWebview, String callbackFunName, AjaxResult result) {
        evalJsFun(iWebview, callbackFunName, result, false);
    }

    public static void evalJsFun(IWebview iWebview, String callbackFunName, AjaxResult result, boolean isString) {
        evalJsFun(iWebview, callbackFunName, JsonUtil.toJson(result), isString);
    }

    public static void evalJsFun(IWebview iWebview, String callbackFunName, String result, boolean isString) {
        if (isString) {
            iWebview.evalJS("javascript:" + callbackFunName + "('" + result + "')");
        } else {
            iWebview.evalJS("javascript:" + callbackFunName + "(" + result + ")");
        }
    }
}
