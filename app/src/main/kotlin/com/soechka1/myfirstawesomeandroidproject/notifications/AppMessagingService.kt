package com.soechka1.myfirstawesomeandroidproject.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.soechka1.myfirstawesomeandroidproject.MainActivity

class AppMessagingService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "AppMessagingService"

        const val CHANNEL_PROMO = "channel_promo"
        const val CHANNEL_AUTH = "channel_auth"
        const val CHANNEL_GENERAL = "channel_general"

        private const val KEY_KIND = "kind"
        private const val KEY_TITLE = "title"
        private const val KEY_MESSAGE = "message"

        const val EXTRA_PUSH_KIND = "push_kind"

        private const val KIND_PROMO = "promo"
        private const val KIND_AUTH = "auth"
        private const val KIND_GENERAL = "general"

        fun createNotificationChannels(context: Context) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                val promoChannel = NotificationChannel(
                    CHANNEL_PROMO,
                    "Акции и предложения",
                    NotificationManager.IMPORTANCE_DEFAULT,
                ).apply {
                    description = "Промо-уведомления от приложения"
                }

                val authChannel = NotificationChannel(
                    CHANNEL_AUTH,
                    "Безопасность",
                    NotificationManager.IMPORTANCE_DEFAULT,
                ).apply {
                    description = "Уведомления о входе и авторизации"
                }

                val generalChannel = NotificationChannel(
                    CHANNEL_GENERAL,
                    "Общие",
                    NotificationManager.IMPORTANCE_DEFAULT,
                ).apply {
                    description = "Общие уведомления от приложения"
                }

                manager.createNotificationChannels(listOf(promoChannel, authChannel, generalChannel))
            }
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d(TAG, "from: ${message.from}")

        if (message.data.isEmpty()) {
            Log.d(TAG, "empty data")
            return
        }

        val kind = message.data[KEY_KIND] ?: KIND_GENERAL
        val title = message.data[KEY_TITLE] ?: getDefaultTitle(kind)
        val body = message.data[KEY_MESSAGE] ?: getDefaultMessage(kind)

        Log.d(TAG, "kind: $kind")

        showNotification(kind, title, body)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "token: $token")
    }

    private fun showNotification(kind: String, title: String, body: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val (channelId, notificationId) = when (kind) {
            KIND_PROMO -> CHANNEL_PROMO to 1001
            KIND_AUTH -> CHANNEL_AUTH to 1002
            else -> CHANNEL_GENERAL to 1000
        }

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(EXTRA_PUSH_KIND, kind)
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            notificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(notificationId, notification)
    }

    private fun getDefaultTitle(kind: String): String = when (kind) {
        KIND_PROMO -> "Специальное предложение!"
        KIND_AUTH -> "Новый вход в аккаунт"
        else -> "Уведомление"
    }

    private fun getDefaultMessage(kind: String): String = when (kind) {
        KIND_PROMO -> "У нас есть новые акции для вас"
        KIND_AUTH -> "Обнаружен новый вход в ваш аккаунт"
        else -> "У вас новое уведомление"
    }
}
