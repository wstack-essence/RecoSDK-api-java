package com.hiscene.sdk.RecoSDK.recognize.entity;

public class Instance {

	private long id;
	private long collection_id;
	private String name;
	private String description;
	private String displayImage;
	private int effect_owner;
	private int effect_type;
	private String property;
	private Material material;
	private Resource[] resources;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCollection_id() {
		return collection_id;
	}

	public void setCollection_id(long collection_id) {
		this.collection_id = collection_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDisplayImage() {
		return displayImage;
	}

	public void setDisplayImage(String displayImage) {
		this.displayImage = displayImage;
	}

	public int getEffect_owner() {
		return effect_owner;
	}

	public void setEffect_owner(int effect_owner) {
		this.effect_owner = effect_owner;
	}

	public int getEffect_type() {
		return effect_type;
	}

	public void setEffect_type(int effect_type) {
		this.effect_type = effect_type;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}
	
	public Resource[] getResources() {
		return resources;
	}

	public void setResources(Resource[] resources) {
		this.resources = resources;
	}

	
	public Resource getResource(int i){
		if(i < resources.length){
			return resources[i];
		}
		return null;
	}
}
