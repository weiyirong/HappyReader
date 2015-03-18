package com.weiyi.reader.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ����ͼƬʵ����,�̳���Blog����Ϊ������Ҳ��һƪ����
 * 
 * @author κ����
 * @version 1.1
 * */
public class NicePhoto extends ITBlog implements Serializable {

	private ArrayList<String> photoUrls;// ���汾ƪ���������е�����ͼƬ��URL
	public String cartoonUrl;

	public NicePhoto() {

	}

	// private NicePhoto(Parcel in) {
	// in.readList(photoUrls, NicePhoto.class.getClassLoader());
	// // this.currentPlacemark = in.readParcelable((ClassLoader)
	// // Placemark.CREATOR);
	// // this.routePlacemark =
	// // in.readParcelable(Placemark.class.getClassLoader());
	// }

	public NicePhoto(ArrayList<String> photoUrls) {
		this.photoUrls = photoUrls;
	}

	public ArrayList<String> getPhotoUrls() {
		return photoUrls;
	}

	public void setPhotoUrls(ArrayList<String> photoUrls) {
		this.photoUrls = photoUrls;
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
	// super.writeToParcel(dest, flags);
	// dest.writeList(photoUrls);
	// }
	//
	// public static final Parcelable.Creator<NicePhoto> CREATOR = new
	// Creator<NicePhoto>() {
	//
	// @Override
	// public NicePhoto[] newArray(int size) {
	// // TODO Auto-generated method stub
	// return new NicePhoto[size];
	// }
	//
	// @Override
	// public NicePhoto createFromParcel(Parcel source) {
	// // TODO Auto-generated method stub
	// return new NicePhoto(source);
	// }
	// };
}
