package com.waseefakhtar.datareceiver.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.waseefakhtar.notificationreceiver.NotificationInfo
import com.waseefakhtar.notificationreceiver.NotificationsAidlInterface

class AIDLService : Service() {

    private val TAG  = this.javaClass.simpleName

    private val notificationInfos = arrayListOf<NotificationInfo>()

    private val notificationsAidlInterface: NotificationsAidlInterface.Stub  = object: NotificationsAidlInterface.Stub() {
        override fun getNotificationInfoList(): MutableList<NotificationInfo> {
            synchronized(this) {
                Log.i(TAG, "getNotificationInfoList is run: $notificationInfoList")
                return notificationInfoList
            }
        }

        override fun addInfo(notificationInfo: NotificationInfo) {
            synchronized(this) {
                if (!notificationInfos.contains(notificationInfo)) {
                    notificationInfos.add(notificationInfo)
                }

                Log.i(TAG, "Sent data: $notificationInfoList");
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return notificationsAidlInterface;
    }
}