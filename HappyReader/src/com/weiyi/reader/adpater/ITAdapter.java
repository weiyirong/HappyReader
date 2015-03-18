package com.weiyi.reader.adpater;

import java.util.HashMap;
import java.util.List;

import com.weiyi.reader.entity.ITBlog;
import com.weiyi.reader.ui.R;
import com.weiyi.reader.view.MyNetImageView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * IT阅读内容显示适配器
 * 
 * @author 魏艺荣
 * */
public class ITAdapter extends BaseAdapter {
	private HashMap<Integer, View> mapViews = new HashMap<Integer, View>();
	private List<ITBlog> data;
	private LayoutInflater layoutInflater;

	public ITAdapter(Context context, List<ITBlog> data) {
		this.layoutInflater = LayoutInflater.from(context);
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = mapViews.get(position);
		ViewHolder viewHolder = new ViewHolder();
		ITBlog itBlog = new ITBlog();
		if (view == null) {
			//viewHolder = new ViewHolder();
			view = layoutInflater.inflate(R.layout.it_row, null);
			viewHolder.blogIconView = (MyNetImageView) view
					.findViewById(R.id.row_icon);
			viewHolder.blogTitleView = (TextView) view
					.findViewById(R.id.row_title);
			viewHolder.blogDateView = (TextView) view
					.findViewById(R.id.row_date);
			itBlog = data.get(position);
			if (itBlog.getIconUrl() != null) {// 是否有网络图标，有则设置，无则使用默认
				viewHolder.blogIconView.setImgUrl(itBlog.getIconUrl(),false);
			}
			viewHolder.blogTitleView.setText(itBlog.getTilte());
			viewHolder.blogDateView.setText(itBlog.getDate());
			mapViews.put(position, view);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}	
		return view;
	}

	class ViewHolder {
		public MyNetImageView blogIconView;
		public TextView blogTitleView;
		public TextView blogDateView;
	}
}
