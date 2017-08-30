/**
 * HiScene SDK Recognize
 *
 * @author lerry.xiao
 * @date 2015-06-04
 */
package com.hiscene.sdk.RecoSDK.recognize;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import com.hiscene.sdk.RecoSDK.recognize.entity.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.hiscene.sdk.RecoSDK.HiSceneSDK;
import com.hiscene.sdk.RecoSDK.SystemParam;
import com.hiscene.sdk.RecoSDK.utils.Utils;


public class Recognize {


    // 鉴权成功后的对象
    private HiSceneSDK hiSceneSDK;

    /**
     * 构造方法
     *
     *
     */
    public Recognize(HiSceneSDK hisceneSDK) {
        this.hiSceneSDK = hisceneSDK;
    }

    /**
     * 识别方法
     *
     * @author lerry
     * @date 2015年6月6日
     * @return
     */
    public RecognizeResult doRecognize(String collection_ids, byte[] image,
                                       String filename, int number, double longitude, double latitude)
            throws ParseException, IOException, JSONException {
        RecognizeResult recoResult = new RecognizeResult();
        if (filename == null || filename.trim().equals("")) {
            recoResult.SetComment("未指定文件名");
            recoResult.setRetCode(1001);
            return recoResult;
        }
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(hiSceneSDK.getRecoginzeAPI());
        // 识别图片
        MultipartEntity requestEntity = new MultipartEntity();
        ContentBody cb = new ByteArrayBody(image, filename);
        requestEntity.addPart("image", cb);
        // 设置识别参数，拼接成key=value的字符串
        StringBuilder params = new StringBuilder();
        params.append("collection_id").append("=").append(collection_ids)
                .append("&");
        params.append("number").append("=").append(number).append("&");
        // 创建系统参数
        SystemParam sp = new SystemParam(hiSceneSDK.getAppKey(),
                hiSceneSDK.getSecret(), longitude, latitude);
        sp.recoSignature();
        Map<String, String> sysParams = sp.getAllParams();
        for (Map.Entry<String, String> entry : sysParams.entrySet()) {
            params.append(entry.getKey()).append("=").append(entry.getValue())
                    .append("&");
        }

        String data = Utils.base64(params.substring(0, params.length() - 1));

        StringBody sBody = StringBody.create(data, HTTP.PLAIN_TEXT_TYPE, Charset.forName(HTTP.UTF_8));
        requestEntity.addPart("data", sBody);
        httpPost.setEntity(requestEntity);
        //header 中设置token
        httpPost.addHeader("token", hiSceneSDK.getToken());
        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            String result = EntityUtils.toString(responseEntity);
            System.out.println(result);
            JSONObject resJson = JSONObject.fromObject(result);
            return parseResponse(resJson);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            throw e;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            throw e;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw e;
        } catch (JSONException e) {
            throw e;
        }
    }

    /**
     * 转换识别返回结果的json
     *
     * @author lerry
     * @date 2015年6月8日
     * @param resJson
     * @return
     */
    private RecognizeResult parseResponse(JSONObject resJson) {
        RecognizeResult recoResult = new RecognizeResult();
        int retCode = resJson.getInt("retCode");
        if (retCode != 0) {
            recoResult.setRetCode(retCode);
            recoResult.SetComment(resJson.getString("comment"));
            return recoResult;
        } else {
            if (resJson.containsKey("instances")) {
                int count = resJson.getInt("count");
                recoResult.setCount(count);
                recoResult.setDateType(0);
                if (count > 0) {
                    JSONArray instanceList = resJson.getJSONArray("instances");
                    for (int i = 0; i < instanceList.size(); i++) {
                        Instance instance = new Instance();
                        JSONObject insJSObj = instanceList.getJSONObject(i);
                        long id = insJSObj.getLong("id");
                        long collection_d = insJSObj.getLong("collection_id");
                        String name = insJSObj.getString("name");
                        String description = insJSObj.getString("description");
                        String displayImage = insJSObj.getString("display_image");
                        int effect_owner = insJSObj.getInt("effect_owner");
                        int effect_type = insJSObj.getInt("effect_type");
                        String property = insJSObj.getString("property");
                        instance.setId(id);
                        instance.setCollection_id(collection_d);
                        instance.setName(name);
                        instance.setDescription(description);
                        instance.setDisplayImage(displayImage);
                        instance.setEffect_owner(effect_owner);
                        instance.setEffect_type(effect_type);
                        instance.setProperty(property);
                        //设置material
                        JSONObject matJsObj = insJSObj.getJSONObject("material");
                        long matID = matJsObj.getLong("id");
                        String url = matJsObj.getString("url");
                        String target_id = matJsObj.getString("target_id");
                        Material material = new Material();
                        material.setId(matID);
                        material.setUrl(url);
                        material.setTarget_id(target_id);
                        instance.setMaterial(material);
                        //设置资源
                        JSONArray resouceJsList = insJSObj.getJSONArray("resources");
                        Resource[] resourceArray = new Resource[resouceJsList.size()];
                        for (int j = 0; j < resouceJsList.size(); j++) {
                            Resource resource = new Resource();
                            JSONObject resJSObj = resouceJsList.getJSONObject(j);
                            long resID = resJSObj.getLong("id");
                            String resName = resJSObj.getString("name");
                            int resource_type = resJSObj.getInt("resource_type");

                            resource.setId(resID);
                            resource.setName(resName);
                            resource.setResource_type(resource_type);

                            JSONObject resDataJSObj = resJSObj.getJSONObject("resource_data");
                            ResourceData rd  = new ResourceData();
                            rd.setSource(resDataJSObj.getString("source"));
                            rd.setContent(resDataJSObj.getString("content"));
                            if(resDataJSObj.containsKey("preview")){
                                rd.setPreview(resDataJSObj.getString("preview"));
                            }
                            resource.setResourceData(rd);
                            resourceArray[j] = resource;
                        }
                        instance.setResources(resourceArray);
                    }
                }
            }else if(resJson.containsKey("imageIDList")){
                JSONArray arr = resJson.getJSONArray("imageIDList");
                String[] imageIDList = new String[arr.size()];
                for(int i=0;i < arr.size();i++){
                    imageIDList[i] = arr.getString(i);
                }
                recoResult.setDateType(1);
                recoResult.setImageIDList(imageIDList);
            }
        }
        return recoResult;
    }

}
