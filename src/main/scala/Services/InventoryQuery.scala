package com.seantheprogrammer.cigar_finder_android

import android.content.Context
import com.google.android.gms.location.Geofence
import InventoryKeeper.IntentLocationClient

class InventoryQuery(context: Context, locationClient: IntentLocationClient) {
  def updateInventory = locationClient.hasError match {
    case true => handleError
    case false => lookupInventory
  }

  def handleError = {
    android.util.Log.e("CigarFinder",
      "Geofence error: %d".format(locationClient.errorCode))
  }

  def lookupInventory = {
    if (locationClient.isEntering) createNotifications
    else if (locationClient.isExiting) cancelNotifications
  }

  def createNotifications = {
    if (enoughTimePassed) {
      locationClient.geofences.foreach(createNotification)
    }
  }

  def cancelNotifications {}

  def createNotification(geofence: Geofence) = {
    val result = new MissingInformationLoader(geofence.getRequestId).result
    new InventoryQueryNotification(context, result).build
  }

  def enoughTimePassed = true
}
