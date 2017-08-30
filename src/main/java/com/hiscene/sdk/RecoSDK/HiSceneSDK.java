/**
 * HiScene SDK 接口信息，该类实例鉴权成功后返回，使用时保存该类后可以
 */
package com.hiscene.sdk.RecoSDK;

import java.io.*;
import java.util.Map;

import com.hiscene.sdk.RecoSDK.auth.Auth;
import com.hiscene.sdk.RecoSDK.recognize.Recognize;
import com.hiscene.sdk.RecoSDK.recognize.entity.RecognizeResult;

public class HiSceneSDK implements Serializable {


	private static final long serialVersionUID = 9209446822443775939L;
    private String authapi = "https://api.hiar.io/auth/sdk";

	private static final String RECOINGZEAPI_KEY = "recognize";
	
	private String token;
	
	private Map<String,String> recognzieAPI; 
	
	private int expire;

	private String appKey;
	
	private String secret;


    private String errorComment;

    private Auth auth ;
    private Recognize recognize;



    public HiSceneSDK(String appKey, String secret) {
        this.appKey = appKey;
        this.secret = secret;
        this.auth = new Auth(this);
        this.recognize = new Recognize(this);
    }

    /**
     * 鉴权
     * * @return
     */
    public boolean auth() throws IOException{
        if(this.appKey == ""){
            this.errorComment = "未设置appKey";
            return false;
        }
        if(this.secret == ""){
            this.errorComment = "未设置secret";
            return false;
        }
        try {
            int retCode = this.auth.DoAuth(0.0, 0.0);
            if(retCode != 0){
                return false;
            }
            System.out.println(this.getToken());
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        return true;
    }


    /**
     * 识别
     * @param filePath 图片文件的路径
     * @param collection_ids 图集数组列表
     * @param number 图片配置 top 值, 默认为5
     * @return
     * @throws IOException
     */
    public RecognizeResult recognize(String filePath,int[] collection_ids,int number)throws IOException{
        File file = new File(filePath);
        try {
            return reccoginzie(file,collection_ids, number);
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 图片文件识别
     * @param file  图片文件
     * @param collection_ids
     * @param number
     * @return
     * @throws IOException
     */
    public RecognizeResult reccoginzie(File file,int[] collection_ids,int number) throws IOException{
        String filename = file.getName();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(
                (int) file.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(file));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            throw e;
        }
        return recognize(bos.toByteArray(),filename, collection_ids,number);
    }

    /**
     * 识别
     * @param fileDta byte[]  文件数据
     * @param fileName String 文件名
     * @param collection_ids int[] 图集
     * @param number int 图片匹配 top 值
     * @return
     * @throws IOException
     */
    public RecognizeResult recognize(byte[] fileDta, String fileName, int[] collection_ids,int number) throws IOException{
        String str = new String();
        if(collection_ids != null && collection_ids.length > 0){
            for(int i = 0; i < collection_ids.length; i++){
                str += collection_ids[i] + ",";
            }
            str = str.substring(0,str.length()-1);
        }

        try {
            return this.recognize.doRecognize(str, fileDta,fileName,number, 0.0, 0.0);
        } catch (IOException e) {

            throw e;
        }

    }



    /**
     * 设置鉴权的接口地址
     * @param authapi
     */
    public void setAuthapi(String authapi){
        this.authapi = authapi;
    }

    /**
     * 获取鉴权接口地址
     * @return
     */
    public String getAuthapi(){
        return this.authapi;
    }

    /**
     * set token
     * @param token
     */
    public void setToken(String token){
        this.token = token;
    }

	/**
	 * 获取token
	 * @return
	 */
	public String getToken(){
		return token;
	}

	/**
	 * 获取识别的API
	 * @return
	 */
	public String getRecoginzeAPI(){
		if(recognzieAPI != null && recognzieAPI.containsKey(RECOINGZEAPI_KEY)){
			return recognzieAPI.get(RECOINGZEAPI_KEY);
		}
		return "";
	}

    /**
     * 设置有效时间
     * @param expire
     */
    public void setExpire(int expire){
        this.expire = expire;
    }
	
	/**
	 * 获取有效时间
	 * 
	 * @return
	 */
	public int getExpire(){
		return expire;
	}
	
	/**
	 * 设置鉴权后返回的识别API
	 * 
	 * @param  recognizeAPI
	 */
	public void setRecognizeAPI(Map<String,String> recognizeAPI){
		this.recognzieAPI = recognizeAPI;
	}

    /**
     * 获取错误信息
     * @return
     */
    public String getErrorComment(){
        return this.errorComment;
    }

    public void setAppkey(String appKey){
        this.appKey = appKey;
    }

    public void setSecret(String secret){
        this.secret = secret;
    }

    public String getAppKey(){
		return appKey;
	}
	
	public String getSecret(){
		return secret;
	}
	
}
