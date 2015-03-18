package com.weiyi.reader.ui;

import com.weiyi.reader.common.Constant;
import com.weiyi.reader.util.ImageUtil;
import com.weiyi.reader.util.StringUtil;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class HelpActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.help);
		TextView helpContent = (TextView) findViewById(R.id.help_content);
		helpContent.setMovementMethod(LinkMovementMethod.getInstance());
		Button close = (Button) findViewById(R.id.help_close);
		String source =StringUtil.getContentFromAsset("readme.txt", this);
		helpContent.setText(Html.fromHtml(source.toString()));
		close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
