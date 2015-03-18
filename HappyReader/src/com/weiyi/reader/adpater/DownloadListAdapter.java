package com.weiyi.reader.adpater;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.weiyi.reader.ui.R;

public class DownloadListAdapter extends BaseAdapter {
	private HashMap<Integer, View> mapViews = new HashMap<Integer, View>();
	private File[] files;
	private LayoutInflater layoutInflater;
	BitmapFactory.Options options = new Options();

	public DownloadListAdapter(Context context, File[] files) {
		this.files = files;
		layoutInflater = LayoutInflater.from(context);
		options.inSampleSize = 2;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return files.length;
	}

	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		return files[index];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHoder viewHoder;
		View view = mapViews.get(position);
		File file = files[position];
		if (view == null) {
			viewHoder = new ViewHoder();
			view = layoutInflater.inflate(R.layout.download_item, null);
			viewHoder.downloadImg = (ImageView) view
					.findViewById(R.id.download_img);
			viewHoder.downloadTxt = (TextView) view
					.findViewById(R.id.download_name);
			viewHoder.downloadImg.setImageBitmap(BitmapFactory.decodeFile(
					file.getAbsolutePath(), options));
			viewHoder.downloadTxt.setText(file.getName());
			mapViews.put(position, view);
			view.setTag(viewHoder);
		} else {
			viewHoder = (ViewHoder) view.getTag();
		}
		return view;
	}

	class ViewHoder {
		ImageView downloadImg;
		TextView downloadTxt;
	}
}
