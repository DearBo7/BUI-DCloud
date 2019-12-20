package top.dearbo.dcloud.common.plugin;

import android.content.Context;
import android.content.SharedPreferences;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;

import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.DHInterface.StandardFeature;
import io.dcloud.common.util.JSUtil;
import top.dearbo.common.entity.AjaxResult;
import top.dearbo.dcloud.common.JSUtils;

/**
 * Created by Bo on 2018/10/9 8:47
 */
public class SharedPreferencesPlugin extends StandardFeature {

    private final static String FILE_NAME = "system_app_config";

    public String keyExist(IWebview iWebview, JSONArray jsonArray) {
        String keyName = jsonArray.optString(0);
        if (StringUtils.isBlank(keyName)) {
            return JSUtils.wrapJsVar(new AjaxResult(-1, "key不能为空"));
        }
        boolean containsFlag = getDPluginContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).contains(keyName);

        return JSUtils.wrapJsVar(containsFlag);
    }

    /**
     * SharedPreferences 方式增删改查  储存路径：shared_prefs
     */
    public void saveAppConfig(IWebview iWebview, JSONArray jsonArray) {
        //回调
        String callbackId = jsonArray.optString(0);
        String keyName = jsonArray.optString(1);
        String keyValue = jsonArray.optString(2);
        if (StringUtils.isBlank(keyName)) {
            JSUtils.execCallbackOk(iWebview, callbackId, new AjaxResult(-1, "保存的key不能为空!", jsonArray));
            return;
        }
        //获取SharedPreferences
        SharedPreferences sharedPreferences = getDPluginContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        //获取一个编辑对象
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //存放数据
        editor.putString(keyName, keyValue);
        boolean commitFlag = editor.commit();
        JSUtils.execCallbackOk(iWebview, callbackId, new AjaxResult(commitFlag));
    }

    public String getAppConfig(IWebview iWebview, JSONArray jsonArray) {
        //回调
        String keyName = jsonArray.optString(0);
        if (StringUtils.isBlank(keyName)) {
            return JSUtils.wrapJsVar(new AjaxResult(0, "获取的key不能为空!"));
        }
        SharedPreferences sharedPreferences = getDPluginContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        String keyValue = sharedPreferences.getString(keyName, null);
        return JSUtils.wrapJsVar(new AjaxResult(keyValue));
    }

    public String deleteAppConfig(IWebview iWebview, JSONArray jsonArray) {
        String keyName = jsonArray.optString(0);
        if (StringUtils.isBlank(keyName)) {
            return JSUtil.wrapJsVar(new AjaxResult(0, "删除的key不能为空!").toString());
        }
        boolean commitFlag = getDPluginContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().remove(keyName).commit();
        return JSUtils.wrapJsVar(commitFlag);
    }

}
