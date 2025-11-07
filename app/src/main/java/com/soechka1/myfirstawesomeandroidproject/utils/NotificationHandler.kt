package com.soechka1.myfirstawesomeandroidproject.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import com.soechka1.myfirstawesomeandroidproject.Keys
import com.soechka1.myfirstawesomeandroidproject.MainActivity
import com.soechka1.myfirstawesomeandroidproject.R
import com.soechka1.myfirstawesomeandroidproject.model.NotificationModel
import com.soechka1.myfirstawesomeandroidproject.model.NotificationSettings
import com.soechka1.myfirstawesomeandroidproject.model.NotificationType
import com.soechka1.myfirstawesomeandroidproject.model.ReplyReceiver

class NotificationHandler(
    private val ctx: Context,
    private val resManager: ResManager
) {
    private val notificationManager =
        ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    private var notificationCounter = 0

    fun initNotifChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannels()
        }
    }

    fun clearAllNotifications() {
        if (notificationCounter > 0) {
            notificationManager.cancelAll()
            notificationCounter = 0

            val message = ctx.getString(R.string.all_notifications_cleared)
            Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show()
        } else {
            val message = ctx.getString(R.string.no_notifications_to_clear)
            Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun showNotification(
        titleAndContent: Pair<String, String>,
        messageSettings: NotificationSettings
    ) {

        val notificationId = ++notificationCounter

        val messageData = NotificationModel(
            id = notificationId,
            title = titleAndContent.first,
            content = titleAndContent.second,
            type = messageSettings.selectedCategory
        )



        val priority = when (messageData.type) {
            NotificationType.HIGH -> NotificationCompat.PRIORITY_MAX
            NotificationType.URGENT -> NotificationCompat.PRIORITY_DEFAULT
            NotificationType.MEDIUM -> NotificationCompat.PRIORITY_LOW
            NotificationType.LOW -> NotificationCompat.PRIORITY_MIN
        }

        val channelId = when (messageData.type) {
            NotificationType.HIGH -> HIGH_CHANNEL_ID
            NotificationType.URGENT -> URGENT_CHANNEL_ID
            NotificationType.MEDIUM -> MEDIUM_CHANNEL_ID
            NotificationType.LOW -> LOW_CHANNEL_ID
        }

        val builderNotif = NotificationCompat.Builder(ctx, channelId)
            .setSmallIcon(R.drawable.ic_crysis_alert_24)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setContentTitle(messageData.title)
            .setPriority(priority)
            .setAutoCancel(true)

        messageData.content?.let { builderNotif.setContentText(it) }

        if (messageSettings.canOpened) {
            val openedIntent = Intent(ctx, MainActivity::class.java).apply {
                putExtra(Keys.EXTRA_TITLE_KEY, messageData.title)
                putExtra(Keys.EXTRA_TEXT_KEY, messageData.content)
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            }

            val pendingOpenedIntent = PendingIntent.getActivity(
                ctx, REQUEST_CODE_OPEN_ACTIVITY, openedIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            builderNotif.setContentIntent(pendingOpenedIntent)
        }

        if (messageSettings.hasReply) {
            val remoteInput = RemoteInput.Builder(Keys.KEY_TEXT_REPLY).run {
                setLabel(resManager.getString(R.string.reply_for_notification))
                build()
            }

            val replyIntent = Intent(ctx, ReplyReceiver::class.java).apply {
                putExtra(Keys.EXTRA_ID_REPLY, messageData.id)
            }

            val actionReplyIntent = PendingIntent.getBroadcast(
                ctx,
                REQUEST_CODE_REPLY,
                replyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )

            val replyAction = NotificationCompat.Action.Builder(
                R.drawable.ic_reply_24,
                resManager.getString(R.string.answer_for_message),
                actionReplyIntent
            ).addRemoteInput(remoteInput)
                .build()

            builderNotif.addAction(replyAction)
        }

        if (messageSettings.isBigText && messageData.content != null) {
            builderNotif.setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(messageData.content)
            )
        }

        notificationManager.notify(messageData.id, builderNotif.build())
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun updateNotification(id: Int, newText: String) {
        val activeNotifications = notificationManager.activeNotifications
        val targetNotification = activeNotifications.find { it.id == id }

        if (targetNotification == null) {
            Toast.makeText(ctx,
                resManager.getStringPattern(R.string.notification_not_found_error,
                    id), Toast.LENGTH_SHORT).show()
            return
        }

        val originalNotification = targetNotification.notification

        val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            originalNotification.channelId
        } else {
            ""
        }

        val title = originalNotification.extras.getString(NotificationCompat.EXTRA_TITLE)

        val builderNotif = NotificationCompat.Builder(ctx, channelId)
            .setSmallIcon(R.drawable.ic_crysis_alert_24)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setContentTitle(title)
            .setContentText(newText)
            .setPriority(originalNotification.priority)
            .setAutoCancel(true)

        notificationManager.notify(id, builderNotif.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannels() {
        val urgentChannel = NotificationChannel(
            URGENT_CHANNEL_ID,
            resManager.getString(R.string.notification_channel_urgent),
            NotificationManager.IMPORTANCE_HIGH
        )

        val highChannel = NotificationChannel(
            HIGH_CHANNEL_ID,
            resManager.getString(R.string.notification_channel_high),
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val mediumChannel = NotificationChannel(
            MEDIUM_CHANNEL_ID,
            resManager.getString(R.string.notification_channel_medium),
            NotificationManager.IMPORTANCE_LOW
        )

        val lowChannel = NotificationChannel(
            LOW_CHANNEL_ID,
            resManager.getString(R.string.notification_channel_low),
            NotificationManager.IMPORTANCE_MIN
        )

        notificationManager.createNotificationChannels(
            listOf(
                urgentChannel,
                highChannel,
                mediumChannel,
                lowChannel
            )
        )
    }

    private companion object {
        private const val REQUEST_CODE_OPEN_ACTIVITY = 100
        private const val REQUEST_CODE_REPLY = 200
        const val URGENT_CHANNEL_ID = "urgent_channel"
        const val HIGH_CHANNEL_ID = "high_channel"
        const val MEDIUM_CHANNEL_ID = "medium_channel"
        const val LOW_CHANNEL_ID = "low_channel"
    }
}
