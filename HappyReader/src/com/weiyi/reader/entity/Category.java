package com.weiyi.reader.entity;

/**
 * ��װһ�����,��ҪΪGallery���ַ�װ����
 * 
 * @author κ����
 * @version 1.0
 * */
public class Category {
	private String title;// �������
	private String url;// ��ַ
	private String summary;// ���

	public Category(String title, String url, String summary) {
		super();
		this.title = title;
		this.url = url;
		this.summary = summary;
	}

	public String getTitle() {
		return title;
	}

	public void setCategory(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSummary() {
		return summary;
	}

}
