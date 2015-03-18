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
 * ���������Ƿ���ǰ��̨����Service
 * 
 * @Description: ���������Ƿ���ǰ��̨����Service
 * 
 * @FileName: AppStatusService.java
 * 
 * @Package com.test.service
 * 
 * @Author Hanyonglu
 * 
 * @Date 2012-4-13 ����04:13:47
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
		System.out.println("��������");

		new Thread() {
			public void run() {
				try {
					while (!isStop) {
						Thread.sleep(1000);

						if (isAppOnForeground()) {
							Log.v(TAG, "ǰ̨����");
						} else {
							Log.v(TAG, "��̨����");
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
	 * �����Ƿ���ǰ̨����
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
		System.out.println("��ֹ����");
		isStop = true;
	}

	// ��ʾNotification
	public void showNotification() {
		// ����һ��NotificationManager������
		NotificationManager notificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);

		// ����Notification�ĸ�������
		Notification notification = new Notification(R.drawable.icon, "�Ķ���",
				System.currentTimeMillis());
		// ����֪ͨ�ŵ�֪ͨ����"Ongoing"��"��������"����
		notification.flags |= Notification.FLAG_ONGOING_EVENT;
		// ������Զ����Notification
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		notification.defaults = Notification.DEFAULT_LIGHTS;
		notification.ledARGB = Color.BLUE;
		notification.ledOnMS = 5000;

		// ����֪ͨ���¼���Ϣ
		CharSequence contentTitle = "�Ķ�����ʾ��Ϣ"; // ֪ͨ������
		CharSequence contentText = "������Ϣ��ʾ����鿴����"; // ֪ͨ������

		Intent notificationIntent = new Intent(AppManager.context,
				AppManager.context.getClass());
		notificationIntent.setAction(Intent.ACTION_MAIN);
		notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		PendingIntent contentIntent = PendingIntent.getActivity(
				AppManager.context, 0, notificationIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo(AppManager.context, contentTitle,
				contentText, contentIntent);
		// ��Notification���ݸ�NotificationManager
		notificationManager.notify(0, notification);
	}
}
