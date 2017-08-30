/**
 * HiScene SDK 鉴权
 * 
 * @author lerry.xiao
 * @date 2015-06-03
 */
package com.hiscene.sdk.RecoSDK.auth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.hiscene.sdk.RecoSDK.HiSceneSDK;
import com.hiscene.sdk.RecoSDK.SystemParam;
import com.hiscene.sdk.RecoSDK.utils.Utils;

public class Auth {

    private static final String CLIENT = "CUSTOMER";
    private HiSceneSDK hisceneSDK;

    /**
     *
     * @param hisceneSDK
     */
    public Auth(HiSceneSDK hisceneSDK){
        this.hisceneSDK = hisceneSDK;
    }

	/**
	 * 鉴权，返回鉴权结果
	 * 
	 * @param longitude
	 * @param latitue
	 * @return
	 */
	public int DoAuth(double longitude, double latitude)
			throws  IOException {
		// 设置同的公共参数
		SystemParam sysParam = new SystemParam(this.hisceneSDK.getAppKey(), this.hisceneSDK.getSecret(), longitude,
				latitude);
		sysParam.authSignature();
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(this.hisceneSDK.getAuthapi());
		// 生成参数，并进行base64加密
		Map<String, String> params = setParams();

		Map<String, String> sysP = sysParam.getAllParams();
		StringBuilder sbParams = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			sbParams.append(entry.getKey()).append("=")
					.append(entry.getValue()).append("&");
		}
		for (Map.Entry<String, String> entry : sysP.entrySet()) {
			sbParams.append(entry.getKey()).append("=")
					.append(entry.getValue()).append("&");
		}
		String data = Utils.base64(sbParams.toString());
		// 设置post参数
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("data", data));
		try {
			post.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 获取服务器返回
		try {
			HttpResponse response = httpClient.execute(post);

			HttpEntity resEntity = response.getEntity();
			String result = EntityUtils.toString(resEntity);
			JSONObject object = JSONObject.fromObject(result);
			int retCode = object.getInt("retCode");
			if (retCode == 0) {
				String token = object.getString("token");
				int expires = object.getInt("expire");
				JSONObject apis = object.getJSONObject("apis");
				JSONObject recoginze = apis.getJSONObject("recognize");
				Map<String, String> recoMap = new HashMap<String, String>();
				recoMap.put("get_keys", recoginze.getString("get_keys"));
				recoMap.put("recognize", recoginze.getString("recognize"));
                this.hisceneSDK.setRecognizeAPI(recoMap);
                this.hisceneSDK.setToken(token);
                this.hisceneSDK.setExpire(expires);
				return retCode;
			} else {
				return retCode;
			}
	    } catch (IOException e) {
			throw e;
		}
	}

	private Map<String, String> setParams() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("udid", "");
		params.put("validSeconds", "");
		params.put("imsi", "");
		params.put("os", "");
		params.put("osVersion", "");
		params.put("client", CLIENT);
		params.put("clientVersion", "");
		params.put("deviceType", "");
		params.put("deviceVersion", "");
		params.put("jailbreak", "");
		params.put("appName", "");
		params.put("appVersion", "");
		return params;
	}

}
