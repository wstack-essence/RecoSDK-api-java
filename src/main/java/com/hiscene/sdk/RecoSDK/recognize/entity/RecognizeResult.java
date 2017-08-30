package com.hiscene.sdk.RecoSDK.recognize.entity;

import java.util.List;

public class RecognizeResult {

	private int retCode;
	private int count;
	private List<Instance> instances;
	private String comment;
	private String[] imageIDList;
    private int dataType; //0 表示包含所有信息 1 只有图片的taget_id

	/**
	 * 
	 */
	public int getRetCode() {
		return retCode;
	}

	public void setRetCode(int retCode) {
		this.retCode = retCode;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}


	public List<Instance> getInstances() {
		return instances;
	}

	public void setInstances(List<Instance> instances) {
		this.instances = instances;
	}

	public void addInstance(Instance instance) {
		this.instances.add(instance);
	}

	public Instance getInstacne(int i) {
		return this.instances.get(i);
	}

	public void SetComment(String comment){
		this.comment = comment;
	}
	
	public String getComment(){
		return this.comment;
	}

    public void  setImageIDList(String[] imageIDList){
        this.imageIDList = imageIDList;
    }

    public String[] getImageIDList() {
        return imageIDList;
    }

    public void setDateType(int dataType){
        this.dataType = dataType;
    }

    public int getDataType() {
        return dataType;
    }
}
