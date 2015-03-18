package com.weiyi.reader.anim;

import android.graphics.Matrix;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class ExitAnimation extends Animation {

	private float halfCenterWidth;
	private float halfCenterHeight;

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		// TODO Auto-generated method stub
		super.applyTransformation(interpolatedTime, t);
		Matrix matrix = t.getMatrix();
		if (interpolatedTime < 0.8) {
			matrix.preScale(1 + 0.625f * interpolatedTime,
					(1 - interpolatedTime) / 0.8f + 0.01f, halfCenterWidth,
					halfCenterHeight);
		} else {
			matrix.preScale(7.5f * (1 - interpolatedTime), 0.01f,
					halfCenterWidth, halfCenterHeight);
		}
	}

	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
		setDuration(500);
		setFillAfter(true);
		halfCenterWidth = width / 2;
		halfCenterHeight = height / 2;
		setInterpolator(new AccelerateDecelerateInterpolator());
	}

}
