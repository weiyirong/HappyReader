package com.weiyi.reader.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.weiyi.reader.entity.NicePhoto;
import com.weiyi.reader.util.AppManager;
import com.weiyi.reader.view.GestureImageView;

public class ReadCartoonActivity extends Activity {
	View ad;
	GestureImageView content;
	RelativeLayout readCartoon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.read_cartoon);
		readCartoon = (RelativeLayout) findViewById(R.id.readCartoon);
		content = (GestureImageView) findViewById(R.id.contentView);
		ad = View.inflate(readCartoon.getContext(), R.layout.ad, null);
		RelativeLayout.LayoutParams layoutParams = new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		NicePhoto cartoon = (NicePhoto) getIntent().getExtras()
				.getSerializable("cartoon");
		// title.setText(cartoon.getTilte());
		content.setImgUrl(cartoon.getIconUrl());
		readCartoon.addView(ad, layoutParams);
		// content.setOnTouchListener(new OnTouchListener() {
		//
		// @Override
		// public boolean onTouch(View arg0, MotionEvent event) {
		// if (event.getAction() == MotionEvent.ACTION_DOWN) {
		// if (View.GONE == readCartoon.getVisibility()) {
		// ad.setVisibility(View.VISIBLE);
		// } else {
		// ad.setVisibility(View.GONE);
		// }
		// }
		// return false;
		// }
		// });
		AppManager.context = this;
	}
}
