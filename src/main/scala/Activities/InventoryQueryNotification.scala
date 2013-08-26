package com.seantheprogrammer.cigar_finder_android

import android.content.{Intent, Context}
import android.support.v4.app.NotificationCompat
import android.app.{NotificationManager, Notification, PendingIntent}
import android.os.Parcelable

class InventoryQueryNotification(context: Context, result: SearchResult) {
  def build = {
    val builder = new NotificationCompat.Builder(context)
      .setSmallIcon(R.drawable.ic_launcher)
      .setAutoCancel(true)
      .setOnlyAlertOnce(true)
      .setContentTitle(title)
      .setContentText(body)
      .setStyle(notificationStyle)
      .setDefaults(Notification.DEFAULT_ALL)
      .setContentIntent(createIntent)
      .addAction(R.drawable.ic_action_tick,
        "Yes",
        createIntent(InventoryQueryActivity.reportCarried))
      .addAction(R.drawable.ic_action_cancel,
        "No",
        createIntent(InventoryQueryActivity.reportNotCarried))
    notificationManager.notify(0, builder.build)
  }

  private def notificationStyle = new NotificationCompat.BigTextStyle().bigText(body)

  private def createIntent: PendingIntent = createIntent(None)
  private def createIntent(action: String): PendingIntent = createIntent(Some(action))
  private def createIntent(action: Option[String]) = {
    val intent = new Intent(context, classOf[InventoryQueryActivity])
    intent.putExtra("searchResult", result.asInstanceOf[Parcelable])

    action match {
      case Some(action) => intent.setAction(action)
      case None => // Do nothing
    }

    PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
  }

  private lazy val title = result.storeName
  private lazy val body = "Does %s carry %s?".format(result.storeName, result.cigar)
  private lazy val notificationManager = {
    context.getSystemService(Context.NOTIFICATION_SERVICE).asInstanceOf[NotificationManager]
  }
}