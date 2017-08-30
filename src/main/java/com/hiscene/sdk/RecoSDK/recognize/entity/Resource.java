package com.hiscene.sdk.RecoSDK.recognize.entity;

public class Resource {

	private long id;
	private String name;
	private int resource_type;
	private ResourceData resourceData;

	public ResourceData getResourceData() {
		return resourceData;
	}

	public void setResourceData(ResourceData resourceData) {
		this.resourceData = resourceData;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getResource_type() {
		return resource_type;
	}

	public void setResource_type(int resource_type) {
		this.resource_type = resource_type;
	}


}
