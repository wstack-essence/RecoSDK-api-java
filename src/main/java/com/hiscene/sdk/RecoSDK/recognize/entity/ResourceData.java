package com.hiscene.sdk.RecoSDK.recognize.entity;

/**
 * Created by lerry on 15/12/28.
 */
public class ResourceData {
    private String source;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    private String content;
    private String preview;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
