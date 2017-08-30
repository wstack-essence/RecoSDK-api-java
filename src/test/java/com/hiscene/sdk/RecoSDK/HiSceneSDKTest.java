package com.hiscene.sdk.RecoSDK;

import com.hiscene.sdk.RecoSDK.recognize.entity.RecognizeResult;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by lerry on 15/11/5.
 */
public class HiSceneSDKTest {

    private HiSceneSDK hs;

    @Before
    public void init(){
        System.out.println("init");
        //hs = new HiSceneSDK("5461656177", "e9bc913d45f5f5aa296c51780545b60e");
        hs = new HiSceneSDK("nzShtWOaYL", "52689a2b5ca45152bc0a6f3a58d6fed3");
    }

    @Test
    public void TestAtuh(){
        boolean result = false;
        try {
            result = hs.auth();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(result, true);
    }

    @Test
    public void TestRecognize(){
        boolean authResult = false;
        try {
            authResult = hs.auth();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(authResult,true);
        String filename = "testData/100.jpg";
        int[] colids = new int[]{};
        try {
            RecognizeResult rr = hs.recognize(filename, colids, 5);
            Assert.assertEquals(rr.getRetCode(), 0 );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @After
    public void destroy(){
        System.out.println("destroy");
    }
}
