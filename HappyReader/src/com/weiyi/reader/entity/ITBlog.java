package com.weiyi.reader.entity;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * IT�Ķ�ʵ����
 * 
 * @author κ����
 * */
public class ITBlog implements Serializable {

	private static final long serialVersionUID = 1L;
	private String category;// ���
	private String iconUrl;// ͼ���URL��ַ
	private String tilte;// ����
	private String content;// ����
	private String url;// ���µĵ�ַ
	private String date;// ��������
	private String blogDescription;

	public ITBlog() {

	}

	public ITBlog(String category, String iconUrl, String tilte,
			String content, String url, String date, String blogDescription) {
		super();
		this.category = category;
		this.iconUrl = iconUrl;
		this.tilte = tilte;
		this.content = content;
		this.url = url;
		this.date = date;
		this.blogDescription = blogDescription;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTilte() {
		return tilte;
	}

	public void setTilte(String tilte) {
		this.tilte = tilte;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getBlogDescription() {
		return blogDescription;
	}

	public void setBlogDescription(String blogDescription) {
		this.blogDescription = blogDescription;
	}

	// @Override
	// public int describeContents() {
	// // TODO Auto-generated method stub
	// return 0;
	// }
	//
	// @Override
	// public void writeToParcel(Parcel dest, int flags) {
	// // TODO Auto-generated method stub
	// dest.writeString(this.category);
	// dest.writeString(this.iconUrl);
	// dest.writeString(this.tilte);
	// dest.writeString(this.content);
	// dest.writeString(this.url);
	// dest.writeString(this.date);
	// dest.writeString(this.blogDescription);
	// }
	//
	// public static final Parcelable.Creator<ITBlog> CREATOR = new
	// Creator<ITBlog>() {
	//
	// @Override
	// public ITBlog[] newArray(int size) {
	// // TODO Auto-generated method stub
	// return new ITBlog[size];
	// }
	//
	// @Override
	// public ITBlog createFromParcel(Parcel source) {
	// // TODO Auto-generated method stub
	// return new ITBlog(source.readString(), source.readString(),
	// source.readString(), source.readString(),
	// source.readString(), source.readString(),
	// source.readString());
	// }
	// };

}
