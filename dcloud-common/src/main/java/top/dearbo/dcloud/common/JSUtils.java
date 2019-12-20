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

    public static String wrapJsVarString(String value) {
        return wrapJsVar(value, false);
    }

    ///////////异步回调/////////////

    public static void execCallbackString(IWebview iWebview, String callbackId, String value, boolean b1, int status) {
        execCallback(iWebview, callbackId, value, status, true, b1);
    }

    public static void execCallback(IWebview iWebview, String callbackId, AjaxResult ajaxResult, boolean b, int status) {
        execCallbackString(iWebview, callbackId, ajaxResult == null ? "{}" : ajaxResult.toString(), b, status);
    }

    public static void execCallback(IWebview iWebview, String callbackId, AjaxResult ajaxResult, int status) {
        execCallback(iWebview, callbackId, ajaxResult, false, status);
    }

    public static void execCallbackOkData(IWebview iWebview, String callbackId, Object data) {
        execCallbackOk(iWebview, callbackId, new AjaxResult(data), false);
    }

    public static void execCallbackOk(IWebview iWebview, String callbackId, Collection value) {
        execCallbackString(iWebview, callbackId, JsonUtil.toJson(value), false, JSUtil.OK);
    }

    public static void execCallbackOk(IWebview iWebview, String callbackId, AjaxResult ajaxResult) {
        execCallbackOk(iWebview, callbackId, ajaxResult, false);
    }

    public static void execCallbackOk(IWebview iWebview, String callbackId, AjaxResult ajaxResult, boolean b) {
        execCallback(iWebview, callbackId, ajaxResult, b, JSUtil.OK);
    }

    public static void execCallbackErrorData(IWebview iWebview, String callbackId, Object data) {
        execCallback(iWebview, callbackId, new AjaxResult(data), JSUtil.ERROR);
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
