package com.weiyi.reader.adpater;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.weiyi.reader.entity.NicePhoto;
import com.weiyi.reader.ui.R;
import com.weiyi.reader.util.FileUtil;
import com.weiyi.reader.view.MyNetImageView;

public class NiceEyeGridAdapter extends BaseAdapter {
	List<NicePhoto> photos;
	LayoutInflater layoutInflater;
	HashMap<Integer, View> mapViews = new HashMap<Integer, View>();
	Context context;

	public NiceEyeGridAdapter(Context context, List<NicePhoto> photos) {
		this.photos = photos;
		layoutInflater = LayoutInflater.from(context);
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return photos.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return photos.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = mapViews.get(position);
		ViewHolder viewHolder = new ViewHolder();
		NicePhoto photo = new NicePhoto();
		if (view == null) {
			view = layoutInflater.inflate(R.layout.nice_eye_gridview_item_row,
					null);
			viewHolder.myNetImageView = (MyNetImageView) view
					.findViewById(R.id.nice_eye_img);
			viewHolder.textView = (TextView) view
					.findViewById(R.id.nice_eye_title);
			photo = photos.get(position);
			// 设置图标
			String urlFirst;
			boolean isCartoon = false;
			if (photo.getPhotoUrls() == null) {
				urlFirst = photo.cartoonUrl;
				isCartoon = true;
			} else {
				urlFirst = photo.getPhotoUrls().get(0);
			}
			if (urlFirst == null) {
				Toast.makeText(context, "连接超时，请重试！", Toast.LENGTH_SHORT).show();
				viewHolder.textView.setText(photo.getTilte());
				viewHolder.myNetImageView.setImageBitmap(BitmapFactory
						.decodeResource(context.getResources(),
								R.drawable.socket_timeout));
				mapViews.put(position, view);
				view.setTag(viewHolder);
				return view;
			}
			if (FileUtil.validUrl(urlFirst)) {// 本地资源资源库
				BitmapFactory.Options options = new Options();
				options.inSampleSize = 2;
				viewHolder.myNetImageView.setImageBitmap(BitmapFactory
						.decodeResource(context.getResources(),
								Integer.valueOf(urlFirst), options));
			} else {// 网络图库
				if (photo.getIconUrl() != null) {
					viewHolder.myNetImageView.setImgUrl(photo.getIconUrl(),isCartoon);
				}
			}
			viewHolder.textView.setText(photo.getTilte());
			mapViews.put(position, view);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		return view;
	}

	class ViewHolder {
		public MyNetImageView myNetImageView;
		public TextView textView;
	}
}
