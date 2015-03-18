package com.weiyi.reader.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.weiyi.reader.common.Constant;

public class ITBroadcastReceiver extends BroadcastReceiver {

	public ITBroadcastReceiver() {

	}

	public ITBroadcastReceiver(ITActivity itActivity) {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (Constant.SOCKETTIMEOUT.equals(action)) {// Á´½Ó³¬Ê±
			Toast.makeText(context, R.string.socket_timeout, Toast.LENGTH_SHORT)
					.show();
		}
	}

}
