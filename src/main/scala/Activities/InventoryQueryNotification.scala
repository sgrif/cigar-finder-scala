package com.seantheprogrammer.cigar_finder_android

import android.content.Context
import android.support.v4.app.NotificationCompat
import android.app.{Notification, PendingIntent}
import android.os.Parcelable
import org.scaloid.common._
import ActivityHelpers._

class InventoryQueryNotification(result: SearchResult)
(implicit context: Context) {
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

  def cancel = {
    notificationManager.cancel(0)
  }

  private def notificationStyle = new NotificationCompat.BigTextStyle().bigText(body)

  private def createIntent: PendingIntent = createIntent(None)
  private def createIntent(action: String): PendingIntent = createIntent(Some(action))
  private def createIntent(maybeAction: Option[String]) = {
    val intent = SIntent[InventoryQueryActivity]
    intent.putExtra("searchResult", result.asInstanceOf[Parcelable])

    for (action <- maybeAction) intent.setAction(action)

    ActivityHelpers.pendingActivity(intent, PendingIntent.FLAG_UPDATE_CURRENT)
  }

  private lazy val title = result.storeName
  private lazy val body = "Does %s carry %s?".format(result.storeName, result.cigar)
}
