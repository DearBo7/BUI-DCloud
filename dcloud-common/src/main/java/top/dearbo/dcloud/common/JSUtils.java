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
        return wrapJsVar(ajaxResult == null ? "{}" : ajaxResult.toString(), false);
    }

    public static String wrapJsVar(Collection value) {
        return wrapJsVar(JsonUtil.toJson(value), false);
    }

    public static String wrapJsVar(int value) {
        return wrapJsVar(String.valueOf(value), false);
    }

    ///////////异步回调/////////////

    public static void execCallbackOkData(IWebview iWebview, String callbackId, Object data) {
        execCallbackOkData(iWebview, callbackId, data, false);
    }

    public static void execCallbackOkData(IWebview iWebview, String callbackId, Object data, boolean b) {
        execCallback(iWebview, callbackId, JSUtils.toJsResponseText(new AjaxResult(data).toString()), JSUtil.OK, b);
    }

    public static void execCallbackOk(IWebview iWebview, String callbackId, String ajaxResult, boolean b) {
        execCallbackOk(iWebview, callbackId, new AjaxResult(ajaxResult), b);
    }

    public static void execCallbackOk(IWebview iWebview, String callbackId, AjaxResult ajaxResult) {
        execCallbackOk(iWebview, callbackId, ajaxResult, false);
    }

    public static void execCallbackOk(IWebview iWebview, String callbackId, AjaxResult ajaxResult, boolean b) {
        execCallback(iWebview, callbackId, JSUtils.toJsResponseText(ajaxResult.toString()), JSUtil.OK, b);
    }

    ///////////异步错误回调/////////////

    public static void execCallbackError(IWebview iWebview, String callbackId, String ajaxResult) {
        execCallbackError(iWebview, callbackId, new AjaxResult(ajaxResult));
    }

    public static void execCallbackError(IWebview iWebview, String callbackId, String ajaxResult, boolean b) {
        execCallbackError(iWebview, callbackId, new AjaxResult(ajaxResult), b);
    }

    public static void execCallbackError(IWebview iWebview, String callbackId, AjaxResult ajaxResult) {
        execCallbackError(iWebview, callbackId, ajaxResult, false);
    }

    public static void execCallbackError(IWebview iWebview, String callbackId, AjaxResult ajaxResult, boolean b) {
        execCallback(iWebview, callbackId, JSUtils.toJsResponseText(JsonUtil.toJson(ajaxResult)), JSUtil.ERROR, b);
    }

    public static void execCallback(IWebview iWebview, String callbackId, AjaxResult ajaxResult, boolean b, int status) {
        execCallback(iWebview, callbackId, JSUtils.toJsResponseText(JsonUtil.toJson(ajaxResult)), status, b);
    }

    ////////////执行js函数/////////

    public static void evalJsFun(IWebview iWebview, String callbackFunName, AjaxResult result) {
        evalJsFun(iWebview, callbackFunName, result, false);
    }

    public static void evalJsFun(IWebview iWebview, String callbackFunName, AjaxResult result, boolean stringFlag) {
        evalJsFun(iWebview, callbackFunName, JsonUtil.toJson(result), stringFlag);
    }

    public static void evalJsFun(IWebview iWebview, String callbackFunName, String result, boolean stringFlag) {
        if (stringFlag) {
            iWebview.evalJS("javascript:" + callbackFunName + "('" + result + "')");
        } else {
            iWebview.evalJS("javascript:" + callbackFunName + "(" + result + ")");
        }
    }
}
