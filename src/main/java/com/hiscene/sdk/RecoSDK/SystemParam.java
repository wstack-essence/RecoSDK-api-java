/**
 * 鉴权及识别的系统参数操作
 * 
 * @author lerry.xiao
 * @date 2015-06-04
 */
package com.hiscene.sdk.RecoSDK;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.hiscene.sdk.RecoSDK.utils.EncoderHandler;
import com.hiscene.sdk.RecoSDK.utils.Utils;

public class SystemParam {

	public static final String VERSION = "1.0";

	private String appKey;

	private long timestamp;

	private String signature;

	private String nonce;

	private double longitude;

	private double latitude;

	private String secret;

	private String nettype;


	/**
	 * 设置鉴权时的系统参数，并生成加密
	 * 
	 * @param longitude
	 * @param latitude
	 */
	public SystemParam(String appKey, String secret, double longitude,
			double latitude) {
		this.appKey = appKey;
		this.timestamp = Calendar.getInstance().getTimeInMillis();
		this.nonce = Utils.randomString(8);
		this.latitude = latitude;
		this.longitude = longitude;
		this.secret = secret;
		this.nettype = "net";
	}
	

	/**
	 * 生成鉴权时的加密串
	 * 
	 * @return String
	 */
	public String authSignature() {
		String[] params = { appKey, Long.toString(timestamp), nonce,
				Double.toString(latitude), Double.toString(longitude), VERSION,
				secret, nettype };
		Arrays.sort(params);
		StringBuilder ps = new StringBuilder();

		for (String p : params) {
            System.out.print(p + " ");
			ps.append(p);
		}
        System.out.println("");

		this.signature = EncoderHandler.encode("SHA1", ps.toString());
		return this.signature;
	}

	/**
	 * 识别接口调用时的加密
	 * 
	 * @author lerry
	 * @date 2015年6月8日
	 * @return
	 */
	public String recoSignature() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("appKey", appKey);
		map.put("timestamp", Long.toString(timestamp));
		map.put("nonce", nonce);
		map.put("longitude", Double.toString(longitude));
		map.put("latitude", Double.toString(latitude));
		map.put("nettype", nettype);
		map.put("version", VERSION);
		Set<String> keys = map.keySet();
		String[] keysArray = new String[keys.size()];
		keysArray = keys.toArray(keysArray);
		Arrays.sort(keysArray);
		StringBuilder sb = new StringBuilder();
		for (String k : keysArray) {
			sb.append(k).append("=").append(map.get(k));
		}
		sb.append(secret);
		this.signature = EncoderHandler.encodeByMD5(sb.toString());
		return this.signature;
	}

	/**
	 * 获取当前的signuature
	 * 
	 * @return
	 */
	public String getSignature() {
		return this.signature;
	}

	/**
	 * 返回系统参数的名称和值
	 * 
	 * @author lerry
	 * @date 2015年6月5日
	 * @return Map<String,String>
	 */
	public Map<String, String> getAllParams() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("appKey", appKey);
		map.put("timestamp", Long.toString(timestamp));

		map.put("signature", signature);
		map.put("nonce", nonce);
		map.put("longitude", Double.toString(longitude));
		map.put("latitude", Double.toString(latitude));
		map.put("nettype", nettype);
		map.put("version", VERSION);
		return map;
	}



}
