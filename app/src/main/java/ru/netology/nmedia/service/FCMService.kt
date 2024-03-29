package ru.netology.nmedia.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import ru.netology.nmedia.R
import kotlin.random.Random

class FCMService : FirebaseMessagingService() {
    private val action = "action"
    private val content = "content"
    private val channelId = "remote"
    private val gson = Gson()

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_remote_name)
            val descriptionText = getString(R.string.channel_remote_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        try {
            message.data[action]?.let {
                when (Action.valueOf(it)) {
                    Action.LIKE -> {
                        val like = gson.fromJson(message.data[content], Like::class.java)
                        handleNotification(getLikeMessage(like), null)
                    }
                    Action.NEW_POST -> {
                        val newPost = gson.fromJson(message.data[content], NewPost::class.java)
                        handleNotification(getNewPostMessage(newPost), newPost.content)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            handleNotification(
                "Не удалось определить тип сообщения. Попробуйте обновить приложение или напишите в тех.поддержку",
                null
            )
        }
    }

    override fun onNewToken(token: String) {
        println(token)
    }

    @SuppressLint("MissingPermission")
    private fun handleNotification(contentTitle: String, contentText: String?) {
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(contentTitle)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        if (contentText != null) {
            notificationBuilder.setContentText(contentText)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                )
        }

        val notification = notificationBuilder.build()


        NotificationManagerCompat.from(this)
            .notify(Random.nextInt(100_000), notification)
    }

    private fun getLikeMessage(like: Like) =
        getString(
            R.string.notification_user_liked,
            like.userName,
            like.postAuthor
        )

    private fun getNewPostMessage(newPost: NewPost) =
        getString(
            R.string.notification_user_new_post,
            newPost.postAuthor
        )
}

enum class Action {
    LIKE, NEW_POST
}

data class Like(
    val userId: Long,
    val userName: String,
    val postId: Long,
    val postAuthor: String
)

data class NewPost(
    val userId: Long,
    val postId: Long,
    val postAuthor: String,
    val content: String
)
