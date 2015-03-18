package com.weiyi.reader.ui;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.IBinder;
import android.util.Log;

import com.weiyi.reader.util.FileUtil;
import com.weiyi.reader.util.ImageUtil;

public class MyService extends Service {
	String bitmapUrl;
	Bitmap bitmap;

	@Override
	public IBinder onBind(Intent arg0) {
		// IBinder iBinder = null;
		// if(null == iBinder){
		// iBinder = new MyBinder();
		// }
		// return iBinder;
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("Myservice", "服务被用户调用。。。。。");
		if (intent != null) {
			bitmapUrl = intent.getStringExtra("bitmapUrl");
			if (FileUtil.validUrl(bitmapUrl)) {// drawable资源部
				BitmapFactory.Options options = new Options();
				options.inSampleSize = 2;
				FileUtil.saveBitmap(
						BitmapFactory.decodeResource(getResources(),
								Integer.valueOf(bitmapUrl),options),
						getApplicationContext(), bitmapUrl + ".jpg");
			} else {
				new Thread() {
					public void run() {
						if (bitmapUrl != null) {
							bitmap = ImageUtil.getBitMap(
									getApplicationContext(), bitmapUrl);
							FileUtil.saveBitmap(bitmap,
									getApplicationContext(), bitmapUrl);
							onDestroy();
						}
					}
				}.start();
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}
	// public class MyBinder extends Binder {
	//
	// @Override
	// protected boolean onTransact(int code, Parcel data, Parcel reply,
	// int flags) throws RemoteException {
	// // TODO Auto-generated method stub
	// return super.onTransact(code, data, reply, flags);
	// }
	//
	// /** Activity中获得Service实例调用的方法 */
	// public MyService getService() {
	// return MyService.this;
	// }
	// }
}
