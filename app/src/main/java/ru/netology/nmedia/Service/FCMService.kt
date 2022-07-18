package ru.netology.nmedia.Service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import ru.netology.nmedia.R
import kotlin.random.Random

class FCMService() : FirebaseMessagingService(), Parcelable {

    private val gson = Gson()

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_remote_name)
            val descriptionText = getString(R.string.channel_remote_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    //метод вызывается, когда приходит пуш в приложение
    override fun onMessageReceived(message: RemoteMessage) {
        val data = message.data
        //из поля ДАТА вытаскиваем АКТИОН и интерпретируем контент
        val serializedAction = data[Action.KEY] ?: return
        val action = Action.values().find { it.key == serializedAction } ?: return

        when (action) {
            Action.Like -> handleLikeAction(data[CONTENT_KEY] ?: return)
            Action.NewPost -> handleNewPostAction(data[CONTENT_KEY] ?: return)
        }
    }

    //метод вызывается при старте приложения и генерирует токен
    override fun onNewToken(token: String) {
        Log.d("onNewToken", token)
    }

    @SuppressLint("StringFormatInvalid")
    private fun handleLikeAction(content: String) {
        val likeContent = gson.fromJson(content, Like::class.java)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(
                getString(
                    R.string.notification_user_liked,
                    likeContent.userName,
                    likeContent.postAuthor
                )
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        NotificationManagerCompat.from(this)
            .notify(Random.nextInt(100000), notification)
    }

    @SuppressLint("StringFormatInvalid")
    private fun handleNewPostAction(content: String) {
        val postContent = gson.fromJson(content, NewPost::class.java)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(
                getString(
                    R.string.notification_new_post,
                    postContent.userName,
                    postContent.postAuthor
                )
            )
            .setContentText(postContent.postAuthor)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(postContent.postAuthor))
            .build()

        NotificationManagerCompat.from(this)
            .notify(Random.nextInt(100000), notification)
    }

    companion object CREATOR : Parcelable.Creator<FCMService> {

        const val CONTENT_KEY = "content"
        const val CHANNEL_ID = "remote"

        override fun createFromParcel(parcel: Parcel): FCMService {
            return FCMService(parcel)
        }

        override fun newArray(size: Int): Array<FCMService?> {
            return arrayOfNulls(size)
        }
    }

    constructor(parcel: Parcel) : this() {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {}
}