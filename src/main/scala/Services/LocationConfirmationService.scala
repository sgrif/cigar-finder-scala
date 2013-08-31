package com.seantheprogrammer.cigar_finder_android

import android.app.IntentService
import android.content.Intent
import com.google.android.gms.location.{DetectedActivity, ActivityRecognitionResult}
import org.scaloid.common.SContext

class LocationConfirmationService extends IntentService("LocationConfirmationService")
with SContext {
  import DetectedActivity._

  override def onHandleIntent(intent: Intent) {
    if (ActivityRecognitionResult.hasResult(intent)) {
      val result = ActivityRecognitionResult.extractResult(intent)
      val activity = result.getMostProbableActivity

      if (activity.getType == STILL)
        createNotifications()
    }
  }

  private def possibleTypes = Map(
    IN_VEHICLE -> "in vehicle",
    ON_BICYCLE -> "on bicycle",
    ON_FOOT -> "on foot",
    STILL -> "still",
    TILTING -> "tilting",
    UNKNOWN -> "unkown"
  )

  private def createNotifications() {
    data.currentStoreIds.foreach(createNotification)
  }

  private def createNotification(id: String) = {
     val result = new MissingInformationLoader(id).result
     new InventoryQueryNotification(result).build
     data.notificationSent()
     new LocationConfirmation().cancel()
  }

  private lazy val data = new InventoryQueryData
}
