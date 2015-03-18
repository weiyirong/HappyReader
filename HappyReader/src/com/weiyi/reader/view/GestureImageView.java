package com.weiyi.reader.view;

import com.weiyi.reader.common.ImageDownloadAsyncTask;
import com.weiyi.reader.util.MyNetImageCacheManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Scroller;

/**
 * 自定义手势缩放控件
 * 
 * @author zhaoyu
 * 
 */
public class GestureImageView extends ImageView {

	private static final String TAG = "gesture";

	private static final int NONE = 0;
	private static final int DRAG = 1;
	private static final int ZOOM = 2;

	private static final int REFRESH = 3;

	private static final float MAX_ZOOM = 3.0f;
	private static final float MIN_ZOOM = 1.0f;

	private Matrix matrix = new Matrix();
	private Matrix savedMatrix = new Matrix();

	private PointF start = new PointF();
	private PointF middle = new PointF();
	private float currentScaleX = 1.0f;
	private float currentScaleY = 1.0f;

	int mode = NONE;

	private float oldDist = 0.0f;
	private float newDist = 0.0f;

	private Scroller scroller;

	int imageWidth = 0;
	int imageHeight = 0;

	float translationX = 0.0f;
	float translationY = 0.0f;

	float top = 0.0f;
	float bottom = 0.0f;
	float left = 0.0f;
	float right = 0.0f;

	float tran_width = 0f;
	float tran_height = 0f;

	// 空白的宽、高
	float fill_width;
	float fill_height;

	float screen_width;
	float screen_height;

	float scale = 0.0f;

	DisplayMetrics dm;
	public String imgUrl;

	public GestureImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		scroller = new Scroller(getContext(), new DecelerateInterpolator());
		dm = getResources().getDisplayMetrics();
	}

	/**
	 * 设置图片地址
	 * 
	 * @param url
	 *            图片地址
	 * */
	public void setImgUrl(String url) {
		this.imgUrl = url;
		loadImg(url);
	}

	/**
	 * 为ImageView加载图片
	 * 
	 * @param url
	 * */
	private void loadImg(String url) {
		if (MyNetImageCacheManager.getInstance().isBitmapExistInLocal(url)) {// 是否有缓存
			Bitmap bitmap = MyNetImageCacheManager.getInstance()
					.getBitmapFromLocal(url);
			setImageBitmap(bitmap);
		} else {// 没有缓存，则从网络下载
			new ImageDownloadAsyncTask(this).execute(url);
		}
	}

	@Override
	protected void onAttachedToWindow() {
		// TODO Auto-generated method stub
		Rect rect = new Rect();
		this.getWindowVisibleDisplayFrame(rect);
		super.onAttachedToWindow();

		this.screen_height = dm.heightPixels;
		this.screen_width = dm.widthPixels;
		init();
	}

	public void init() {
		this.imageWidth = this.getDrawable().getIntrinsicWidth();
		this.imageHeight = this.getDrawable().getIntrinsicHeight();

		Log.v(TAG, "screen_width:" + screen_width+"::::"+this.imageWidth );
		Log.v(TAG, "screen_height:" + screen_height+":::"+this.imageHeight);
		if (screen_width > imageWidth && screen_height > imageHeight) {
			initScalePic();
		}
		layoutToCenter();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		dumpEvent(event);
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN: {
			mode = DRAG;
			savedMatrix.set(matrix);
			start.set(event.getX(), event.getY());
			mode = DRAG;
			break;
		}
		case MotionEvent.ACTION_POINTER_DOWN: {
			Log.v(TAG, "ACTION_POINTER_DOWN");
			oldDist = spacing(event);
			if (oldDist > 10.0f) {
				savedMatrix.set(matrix);
				mid(event, middle);
				mode = ZOOM;
			}
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			if (mode == DRAG) {
				matrix.set(savedMatrix);
				matrix.postTranslate(event.getX() - start.x, event.getY()
						- start.y);
				this.setImageMatrix(matrix);
			} else if (mode == ZOOM) {
				newDist = spacing(event);
				if (newDist > 10.0f) {
					matrix.set(savedMatrix);
					scale = newDist / oldDist;
					matrix.postScale(scale, scale, middle.x, middle.y);
					this.setImageMatrix(matrix);
					calcuteBlankArea();
				}
			}
			break;
		}
		// case MotionEvent.ACTION_POINTER_UP:
		case MotionEvent.ACTION_UP: {
			Log.v(TAG, "ACTION_UP");
			mode = NONE;
			savedMatrix.set(matrix);
			float[] values = new float[9];
			this.getImageMatrix().getValues(values);
			translationX = values[Matrix.MTRANS_X];
			translationY = values[Matrix.MTRANS_Y];
			float width = 0.0f;
			float height = 0.0f;
			width = this.getScaleX() * this.getImageWidth();
			height = this.getScaleY() * this.getImageHeight();

			top = translationY;
			bottom = height + top;
			left = translationX;
			right = width + left;
			Log.v(TAG, "tran_width:" + fill_width);
			Log.v(TAG, "tran_height:" + fill_height);
			if (top > tran_height && left > tran_width) {
				scroller.startScroll((int) 0, (int) 0,
						-(int) (left - tran_width), -(int) (top - tran_height),
						150);
				refreshHandler.sendEmptyMessage(REFRESH);
			} else if (bottom < screen_height - tran_height
					&& right < screen_width - tran_width) {
				scroller.startScroll(0, 0,
						(int) (screen_width - tran_width - right),
						(int) (screen_height - tran_height - bottom), 150);
				refreshHandler.sendEmptyMessage(REFRESH);
			} else if (top > tran_height && right < screen_width - tran_width) {
				scroller.startScroll(0, 0,
						(int) (screen_width - tran_width - right),
						-(int) (top - tran_height), 150);
				refreshHandler.sendEmptyMessage(REFRESH);
			} else if (bottom < screen_height - tran_height
					&& left > tran_width) {
				scroller.startScroll(0, 0, -(int) (left - tran_width),
						(int) (screen_height - tran_height - bottom), 150);
				refreshHandler.sendEmptyMessage(REFRESH);
			} else if (top > tran_height) {
				scroller.startScroll(0, 0, 0, -(int) (top - tran_height), 150);
				refreshHandler.sendEmptyMessage(REFRESH);
			} else if (bottom < screen_height - tran_height) {
				scroller.startScroll(0, 0, 0, (int) (screen_height
						- tran_height - bottom), 150);
				refreshHandler.sendEmptyMessage(REFRESH);
			} else if (left > tran_width) {
				scroller.startScroll(0, 0, -(int) (left - tran_width), 0, 150);
				refreshHandler.sendEmptyMessage(REFRESH);
			} else if (right < screen_width - tran_height) {
				scroller.startScroll(0, 0,
						(int) (screen_width - tran_width - right), 0, 150);
				refreshHandler.sendEmptyMessage(REFRESH);
			}
			break;
		}
		case MotionEvent.ACTION_POINTER_UP: {

			Log.v(TAG, "ACTION_POINTER_UP");

			float[] values = new float[9];
			matrix.getValues(values);
			currentScaleX = values[Matrix.MSCALE_X];
			currentScaleY = values[Matrix.MSCALE_Y];
			if ((currentScaleX) < MIN_ZOOM || (currentScaleY) < MIN_ZOOM) {

				matrix.postScale(1 / currentScaleX, 1 / currentScaleY,
						middle.x, middle.y);
				this.setImageMatrix(matrix);
				savedMatrix.set(matrix);

			}
			if ((currentScaleX) > MAX_ZOOM || (currentScaleY) > MAX_ZOOM) {
				matrix.postScale(MAX_ZOOM / currentScaleX, MAX_ZOOM
						/ currentScaleY, middle.x, middle.y);
				this.setImageMatrix(matrix);
				savedMatrix.set(matrix);
			}

			calcuteBlankArea();
			mode = DRAG;

			break;
		}
		default:
			break;
		}
		return true;
	}

	/**
	 * 设置图片居中显示
	 */
	private void layoutToCenter() {
		calcuteBlankArea();
		Log.v("gesture", "tran_width:" + tran_width);
		Log.v("gesture", "tran_height:" + tran_height);
		matrix.set(savedMatrix);
		matrix.postTranslate(tran_width, tran_height);
		this.setImageMatrix(matrix);
		savedMatrix.set(matrix);
	}

	/**
	 * 计算空白区域
	 */
	private void calcuteBlankArea() {

		// 正在显示的图片实际宽高
		float width = imageWidth * this.getScaleX();
		float height = imageHeight * this.getScaleY();
		// 空白区域宽高
		fill_width = screen_width - width;
		fill_height = screen_height - height;
		// 需要移动的距离
		tran_width = 0f;
		tran_height = 0f;

		if (fill_width > 0)
			tran_width = fill_width / 2;
		if (fill_height > 0)
			tran_height = fill_height / 2;
	}

	private void initScalePic() {
		float scaleWidth = screen_width / (float) imageWidth;
		float scaleHeight = screen_height / (float) imageHeight;
		float scaleRate = Math.min(scaleWidth, scaleHeight);
		Log.v(TAG, "scaleRate:" + scaleRate);
		matrix.set(savedMatrix);
		matrix.postScale(scaleRate, scaleRate);
		this.setImageMatrix(matrix);
		savedMatrix.set(matrix);
	}

	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	/**
	 * 两个手指的中心点
	 * 
	 * @param event
	 * @param mid
	 */
	private void mid(MotionEvent event, PointF mid) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		mid.set(x / 2, y / 2);
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	private float getScaleX() {
		return getScaleX(this.getImageMatrix());
	}

	private float getScaleX(Matrix matrix) {
		return getValue(matrix, Matrix.MSCALE_X);
	}

	private float getScaleY() {
		return getScaleY(this.getImageMatrix());
	}

	private float getScaleY(Matrix matrix) {
		return getValue(matrix, Matrix.MSCALE_Y);
	}

	private float getValue(Matrix matrix, int whichValue) {
		float[] values = new float[9];
		matrix.getValues(values);
		return values[whichValue];
	}

	/**
	 * 刷新移动恢复到边界动画
	 */
	private Handler refreshHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == REFRESH) {
				if (scroller.computeScrollOffset()) {
					matrix.set(savedMatrix);
					matrix.postTranslate(scroller.getCurrX(),
							scroller.getCurrY());
					GestureImageView.this.setImageMatrix(matrix);
					this.sendEmptyMessageDelayed(REFRESH, 30);
				} else {
					savedMatrix.set(matrix);
				}
			}
		}

	};

	/**
	 * dump打印
	 * 
	 * @param event
	 */
	private void dumpEvent(MotionEvent event) {
		String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
				"POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
		StringBuilder sb = new StringBuilder();
		int action = event.getAction();
		int actionCode = action & MotionEvent.ACTION_MASK;
		sb.append("event ACTION_").append(names[actionCode]);
		if (actionCode == MotionEvent.ACTION_POINTER_DOWN
				|| actionCode == MotionEvent.ACTION_POINTER_UP) {
			sb.append("(pid ").append(
					action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
			sb.append(")");
		}
		sb.append("[");
		for (int i = 0; i < event.getPointerCount(); i++) {
			sb.append("#").append(i);
			sb.append("(pid ").append(event.getPointerId(i));
			sb.append(")=").append((int) event.getX(i));
			sb.append(",").append((int) event.getY(i));
			if (i + 1 < event.getPointerCount())
				sb.append(";");
		}
		sb.append("]");
		Log.d("tag", sb.toString());
	}

}
