package com.weiyi.reader.ui;

import java.util.List;

import com.weiyi.reader.util.AppManager;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.util.Log;

/**
 * 监听程序是否在前后台运行Service
 * 
 * @Description: 监听程序是否在前后台运行Service
 * 
 * @FileName: AppStatusService.java
 * 
 * @Package com.test.service
 * 
 * @Author Hanyonglu
 * 
 * @Date 2012-4-13 下午04:13:47
 * 
 * @Version V1.0
 */
public class AppStatusService extends Service {
	private static final String TAG = "AppStatusService";
	private ActivityManager activityManager;
	private String packageName;
	private boolean isStop = false;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		activityManager = (ActivityManager) this
				.getSystemService(Context.ACTIVITY_SERVICE);
		packageName = this.getPackageName();
		System.out.println("启动服务");

		new Thread() {
			public void run() {
				try {
					while (!isStop) {
						Thread.sleep(1000);

						if (isAppOnForeground()) {
							Log.v(TAG, "前台运行");
						} else {
							Log.v(TAG, "后台运行");
							showNotification();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();

		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * 程序是否在前台运行
	 * 
	 * @return
	 */
	public boolean isAppOnForeground() {
		// Returns a list of application processes that are running on the
		// device
		List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
		if (appProcesses == null)
			return false;

		for (RunningAppProcessInfo appProcess : appProcesses) {
			// The name of the process that this object is associated with.
			if (appProcess.processName.equals(packageName)
					&& appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("终止服务");
		isStop = true;
	}

	// 显示Notification
	public void showNotification() {
		// 创建一个NotificationManager的引用
		NotificationManager notificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);

		// 定义Notification的各种属性
		Notification notification = new Notification(R.drawable.icon, "阅读器",
				System.currentTimeMillis());
		// 将此通知放到通知栏的"Ongoing"即"正在运行"组中
		notification.flags |= Notification.FLAG_ONGOING_EVENT;
		// 点击后自动清除Notification
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		notification.defaults = Notification.DEFAULT_LIGHTS;
		notification.ledARGB = Color.BLUE;
		notification.ledOnMS = 5000;

		// 设置通知的事件消息
		CharSequence contentTitle = "阅读器显示信息"; // 通知栏标题
		CharSequence contentText = "推送信息显示，请查看……"; // 通知栏内容

		Intent notificationIntent = new Intent(AppManager.context,
				AppManager.context.getClass());
		notificationIntent.setAction(Intent.ACTION_MAIN);
		notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		PendingIntent contentIntent = PendingIntent.getActivity(
				AppManager.context, 0, notificationIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo(AppManager.context, contentTitle,
				contentText, contentIntent);
		// 把Notification传递给NotificationManager
		notificationManager.notify(0, notification);
	}
}
