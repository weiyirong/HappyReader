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
		// ��ȡͷ�����ȱ���
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
					.tagName("a");// ��ȡ����class=link_title�ı�ǩԪ��
			Elements dates = doc.getElementsByClass(Constant.BlOG_DATE_CLASS);
			Elements urls = titles.select(Constant.HREF_SELECT);
			for (int i = 0; isRunning && i < titles.size(); ++i) {
				String blogUrl = Constant.ITBLOG_URL
						+ urls.get(i).attributes().get("href");// ÿƪ���µ�URL
				NicePhoto photo = new NicePhoto();
				photo.setTilte(titles.get(i).text());// ��ȡa��ǩ�ڵ��ı��������±���
				photo.setDate(dates.get(i).text());// ��ȡ���·�������
				photo.setUrl(blogUrl);// ��ȡ����������href��ֵ
				// ����ÿƪ������ͼƬ�б��URL
				// ����������б�
				if (Constant.CARTOON_CATEGORY_URL.equals(params[0])) {
					photo.cartoonUrl = BlogUtil.getCartoonUrl(blogUrl);// ��ʱ����
					photo.setIconUrl(photo.cartoonUrl);
				} else {// �������б�
					photo.setPhotoUrls(BlogUtil
							.getImageUrlListByBlogUrl(blogUrl));// ��ʱ����
					String iconUrl = photo.getPhotoUrls().get(0);
					if (iconUrl != null)
						photo.setIconUrl(iconUrl);// ����ÿƪ���µ�ͷͼ��URL
				}
				// ���
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
