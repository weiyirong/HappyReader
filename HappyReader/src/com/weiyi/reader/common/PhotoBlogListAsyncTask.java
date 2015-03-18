package com.weiyi.reader.common;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.weiyi.reader.entity.NicePhoto;
import com.weiyi.reader.ui.ITActivity;
import com.weiyi.reader.ui.R;
import com.weiyi.reader.util.BlogUtil;
import com.weiyi.reader.util.StreamUtil;

public class PhotoBlogListAsyncTask extends
		AsyncTask<String, Void, List<NicePhoto>> {
	ITActivity activity;
	boolean isRunning = true;

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public PhotoBlogListAsyncTask(ITActivity activity) {
		super();
		this.activity = activity;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		// 获取头部进度变量
		if (activity.toolbarBack != null) {
			activity.toolbarBack.setVisibility(ImageView.GONE);
		}
		if (activity.toolbarProgress != null) {
			activity.toolbarProgress.setVisibility(ProgressBar.VISIBLE);
		}
		super.onPreExecute();
	}

	@Override
	protected List<NicePhoto> doInBackground(String... params) {
		Document doc;
		try {
			doc = Jsoup.connect(params[0]).timeout(10 * 1000).get();
			Elements titles = doc.getElementsByClass(Constant.BLOG_TITLE_CLASS)
					.tagName("a");// 获取所有class=link_title的标签元素
			Elements dates = doc.getElementsByClass(Constant.BlOG_DATE_CLASS);
			Elements urls = titles.select(Constant.HREF_SELECT);
			for (int i = 0; isRunning && i < titles.size(); ++i) {
				String blogUrl = Constant.ITBLOG_URL
						+ urls.get(i).attributes().get("href");// 每篇文章的URL
				NicePhoto photo = new NicePhoto();
				photo.setTilte(titles.get(i).text());// 获取a标签内的文本，即文章标题
				photo.setDate(dates.get(i).text());// 获取文章发表日期
				photo.setUrl(blogUrl);// 获取超链接属性href的值
				// 加载每篇文章中图片列表的URL
				// 如果是漫画列表
				if (Constant.CARTOON_CATEGORY_URL.equals(params[0])) {
					photo.cartoonUrl = BlogUtil.getCartoonUrl(blogUrl);// 耗时操作
					photo.setIconUrl(photo.cartoonUrl);
				} else {// 是养眼列表
					photo.setPhotoUrls(BlogUtil
							.getImageUrlListByBlogUrl(blogUrl));// 耗时操作
					String iconUrl = photo.getPhotoUrls().get(0);
					if (iconUrl != null)
						photo.setIconUrl(iconUrl);// 设置每篇文章的头图标URL
				}
				// 完成
				activity.photos.add(photo);
				publishProgress();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return activity.photos;
	}

	@Override
	protected void onPostExecute(List<NicePhoto> result) {
		if (activity.adapter != null) {
			activity.adapter.notifyDataSetChanged();
		}
		if (activity.toolbarBack != null) {
			activity.toolbarBack.setVisibility(ImageView.VISIBLE);
		}
		if (activity.toolbarProgress != null) {
			activity.toolbarProgress.setVisibility(ProgressBar.GONE);
		}
		super.onPostExecute(result);
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		if (activity.adapter != null) {
			activity.adapter.notifyDataSetChanged();
		}
		super.onProgressUpdate(values);
	}

}
