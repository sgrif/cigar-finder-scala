package com.seantheprogrammer.cigar_finder_android

import android.content.Context
import com.google.android.gms.location.Geofence
import InventoryKeeper.IntentLocationClient

class InventoryQuery(context: Context, locationClient: IntentLocationClient) {
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
    new InventoryQueryNotification(context, result).build
  }

  private def cancelNotification(geofence: Geofence) = {
    new InventoryQueryNotification(context, null).cancel
  }

  private def enoughTimePassed = true
}
