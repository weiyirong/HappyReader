package com.weiyi.reader.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;

import com.weiyi.reader.util.ViewUtil;

/**
 * 自定义Gallery效果，展示在线小说列表
 * 
 * @author 魏艺荣
 * @version 1.0
 * */
public class MyGallery extends Gallery {
	private Camera camera = new Camera();
	private int maxRotationAngle = 50;// 最大旋转角度，仿3D切换效果
	private int maxZoom = -200;// 最大缩放值,负数即放大图片（拉近图片）

	public MyGallery(Context context) {
		super(context);
		this.setStaticTransformationsEnabled(true);// TODO Auto-generated
													// constructor stub
	}

	public MyGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setStaticTransformationsEnabled(true);// TODO Auto-generated
													// constructor stub
	}

	public MyGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setStaticTransformationsEnabled(true);// TODO Auto-generated
													// constructor stub
	}

	/**
	 * setStaticTransformationsEnabled这个属性设成true的时候
	 * 每次viewGroup(看Gallery的源码就可以看到它是从ViewGroup 间接继承过来的)
	 * 在重新画它的child的时候都会促发getChildStaticTransformation这个函数。
	 * */
	@Override
	protected boolean getChildStaticTransformation(View child, Transformation t) {
		int childCenter = ViewUtil.getCenterOfView(child);
		int ratationAngle = 0;

		// 开始进行转换
		t.clear();
		t.setTransformationType(Transformation.TYPE_MATRIX);
		if (childCenter == getCenterOfGallery()) {// 如果此View在Gallery中心则不需转化（切换）
			transformWithCamera(child, t, 0);
		} else {
			// 计算所需旋转角度
			ratationAngle = ((getCenterOfGallery() - ViewUtil
					.getCenterOfView(child)) / child.getWidth())
					* maxRotationAngle;// 关键之一
			if (Math.abs(ratationAngle) > maxRotationAngle) {
				ratationAngle = (ratationAngle < 0) ? -maxRotationAngle
						: maxRotationAngle;
			}
			transformWithCamera(child, t, ratationAngle);
		}
		return true;
	}

	/**
	 * 完成图片的旋转效果，通过Camera和Matrix完成
	 * */
	private void transformWithCamera(View child, Transformation t,
			int rotationAngle) {
		camera.save();
		Matrix matrix = t.getMatrix();
		int imgWidth = child.getLayoutParams().width;
		int imgHeight = child.getLayoutParams().height;
		int rotation = Math.abs(rotationAngle);

		camera.translate(0.0f, 0.0f, 100.0f);// 移动camera的视角
		if (rotation < maxRotationAngle) {
			float zoomAmount = (float) (maxZoom + (rotation * 1.5));
			camera.translate(0.0f, 0.0f, zoomAmount);
		}
		camera.rotateY(rotationAngle);
		camera.getMatrix(matrix);
		matrix.preTranslate(-(imgWidth / 2), -(imgHeight / 2));
		matrix.postTranslate((imgWidth / 2), (imgHeight / 2));
		camera.restore();
	}

	public int getCenterOfGallery() {
		return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2
				+ getPaddingLeft();
	}

	public int getMaxRotationAngle() {
		return maxRotationAngle;
	}

	public void setMaxRotationAngle(int maxRotationAngle) {
		this.maxRotationAngle = maxRotationAngle;
	}

	public int getMaxZoom() {
		return maxZoom;
	}

	public void setMaxZoom(int maxZoom) {
		this.maxZoom = maxZoom;
	}

	/**
	 * 一次滑动只滚动一张图片
	 */
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (velocityX > 0) {
			// 往左边滑动
			super.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
		} else {
			// 往右边滑动
			super.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
		}
		return false;
	}
}
