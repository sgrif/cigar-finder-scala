package com.seantheprogrammer.cigar_finder_android

import android.content.Context
import com.google.android.gms.location.Geofence
import InventoryKeeper.IntentLocationClient
import android.app.AlarmManager
import org.scaloid.common.Preferences

class InventoryQuery(locationClient: IntentLocationClient)
(implicit context: Context) {
  def updateInventory = locationClient.hasError match {
    case true => handleError
    case false => lookupInventory
  }

  private def handleError = {
    android.util.Log.e("CigarFinder",
      "Geofence error: %d".format(locationClient.errorCode))
  }

  private def lookupInventory = {
    if (locationClient.isEntering) confirmLocation
    else if (locationClient.isExiting) cancelNotifications
  }

  private def confirmLocation = {
    if (enoughTimePassed) {
      val storeIds = locationClient.geofences.map(_.getRequestId)
      queryData.currentStoreIds = storeIds.toSet
      new LocationConfirmation().createNotificationIfConfirmed
    }
  }

  private def cancelNotifications = {
    new InventoryQueryNotification(null).cancel
    new LocationConfirmation().cancel
  }

  private def enoughTimePassed = {
    queryData.timeSinceNotification > AlarmManager.INTERVAL_HOUR &&
    queryData.timeSinceInformationReceived > AlarmManager.INTERVAL_DAY
    true
  }

  private lazy val queryData = new InventoryQueryData
}
