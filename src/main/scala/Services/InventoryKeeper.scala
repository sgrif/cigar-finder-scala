package com.seantheprogrammer.cigar_finder_android

import android.app.IntentService
import android.content.Intent
import com.google.android.gms.location.{Geofence, LocationClient}
import Geofence.{GEOFENCE_TRANSITION_ENTER, GEOFENCE_TRANSITION_EXIT}
import scala.collection.JavaConversions._
import org.scaloid.common._

class InventoryKeeper extends IntentService("InventoryKeeper") with SContext {
  import InventoryKeeper.{LocationClientSource, IntentLocationClient}
  var locationClientSource: LocationClientSource = SimpleLocationClient

  override def onHandleIntent(intent: Intent) = {
    val locationClient = locationClientSource(intent)
    new InventoryQuery(locationClient).updateInventory
  }

  class SimpleLocationClient(intent: Intent)
    extends IntentLocationClient {
    override def hasError = LocationClient.hasError(intent)
    override def errorCode = LocationClient.getErrorCode(intent)
    override def isEntering = transitionType == GEOFENCE_TRANSITION_ENTER
    override def isExiting = transitionType == GEOFENCE_TRANSITION_EXIT
    override def geofences = rawGeofences.toIndexedSeq

    def transitionType = LocationClient.getGeofenceTransition(intent)
    def rawGeofences = LocationClient.getTriggeringGeofences(intent)
  }

  object SimpleLocationClient extends LocationClientSource {
    override def apply(intent: Intent) = new SimpleLocationClient(intent)
  }
}

object InventoryKeeper {
  trait LocationClientSource {
    def apply(intent: Intent): IntentLocationClient
  }

  trait IntentLocationClient {
    def hasError: Boolean
    def errorCode: Int
    def isEntering: Boolean
    def isExiting: Boolean
    def geofences: IndexedSeq[Geofence]
  }
}
