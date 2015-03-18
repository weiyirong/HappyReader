package com.weiyi.reader.adpater;

import java.util.Random;

import android.util.Log;

import com.weiyi.reader.common.Constant;
import com.weiyi.reader.util.StreamUtil;

public class DownLoadThread extends Thread {
	@Override
	public void run() {
		int i = new Random().nextInt(Constant.DOWNLOADURL.length);
		StreamUtil.getInputStreamByUrl(Constant.DOWNLOADURL[i]);
		Log.e("DownLoadThread...", ""+i);
	}
}
