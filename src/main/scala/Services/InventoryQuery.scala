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
    if (locationClient.isEntering) createNotifications
    else if (locationClient.isExiting) cancelNotifications
  }

  private def createNotifications = {
    if (enoughTimePassed) {
      locationClient.geofences.foreach(createNotification)
    }
  }

  private def cancelNotifications = {
    locationClient.geofences.foreach(cancelNotification)
  }

  private def createNotification(geofence: Geofence) = {
    val result = new MissingInformationLoader(geofence.getRequestId).result
    new InventoryQueryNotification(result).build
    prefs.lastNotification = System.currentTimeMillis
  }

  private def cancelNotification(geofence: Geofence) = {
    new InventoryQueryNotification(null).cancel
  }

  private def enoughTimePassed = {
    timeSinceNotification > AlarmManager.INTERVAL_HOUR &&
    timeSinceInformationReceived > AlarmManager.INTERVAL_DAY
    true
  }

  private def timeSinceNotification =
    System.currentTimeMillis - prefs.lastNotification(0L)

  private def timeSinceInformationReceived =
    System.currentTimeMillis - prefs.lastInformationReceived(0L)

  private lazy val prefs = Preferences()
}
