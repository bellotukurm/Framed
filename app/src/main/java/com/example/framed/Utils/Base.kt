package com.example.framed.Utils

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService
import com.example.framed.R

class Base: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        Toast.makeText(context, "Notified", Toast.LENGTH_SHORT).show();
        //val largeIcon: Bitmap = BitmapFactory.decodeResource(context.resources,R.drawable.logo)
        val notification = NotificationCompat.Builder(context, "channel1")
            .setContentTitle(CHANNEL_1_ID)
            .setContentText("is out now!!")
            //.setLargeIcon(largeIcon)
            .setSmallIcon(R.drawable.ic_games)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            //.setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setColor(Color.rgb(255,87,34))
            //.setContentIntent(pending)
            .build()
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(1,notification)
    }

    companion object{
        const val CHANNEL_1_ID = "channel1"
    }
}
/*
class Base: Application() {
    companion object{
        const val CHANNEL_1_ID = "channel1"
    }
    override fun onCreate() {
        super.onCreate()

        createNotificationChannels();
    }

    private fun createNotificationChannels(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel1 = NotificationChannel(
                CHANNEL_1_ID,
                "Channel 1",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel1.description = "testing Notification"

            val manager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel1)
        }
    }
}*/
