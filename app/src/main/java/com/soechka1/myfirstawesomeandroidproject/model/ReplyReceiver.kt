package com.soechka1.myfirstawesomeandroidproject.model

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import com.soechka1.myfirstawesomeandroidproject.Keys
import com.soechka1.myfirstawesomeandroidproject.Keys.EXTRA_ID_REPLY
import com.soechka1.myfirstawesomeandroidproject.Keys.KEY_TEXT_REPLY

class ReplyReceiver : BroadcastReceiver() {
    override fun onReceive(ctx: Context?, intent: Intent?) {

        if (ctx == null || intent == null)
            return

        val notificationId = intent.getIntExtra(EXTRA_ID_REPLY, Keys.INVALID_NOTIFICATION_ID)

        if (notificationId == Keys.INVALID_NOTIFICATION_ID)
            return

        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        val replyText = remoteInput?.getCharSequence(KEY_TEXT_REPLY)?.toString() ?: ""

        if (replyText.isNotEmpty()) {
            MessagesObject.addMessage(replyText)
        }

        val notificationManager = NotificationManagerCompat.from(ctx)
        notificationManager.cancel(notificationId)
    }
}
