package com.seantheprogrammer.cigar_finder_android

import android.app.PendingIntent
import android.content.{Context, Intent}
import android.os.Bundle
import com.google.android.gms.location.{LocationStatusCodes, Geofence, LocationClient}
import com.google.android.gms.common.{ConnectionResult, GooglePlayServicesClient}
import scala.collection.JavaConversions._

class Geofencer(context: Context, stores: IndexedSeq[Store]) {
  private def inProgress = _locationClient.isEmpty
  private val geofenceRadius = 100

  def updateAlerts: Unit = playServices.isAvailable match {
    case true => inProgress match {
      case true => locationClient.disconnect; updateAlerts
      case false => locationClient.connect
    }
    case false => android.util.Log.d("CigarFinder", "Play services unavailable")
  }

  lazy val geofences: java.util.List[Geofence] = stores.map(buildGeofence).toList

  private def buildGeofence(store: Store) = {
    new Geofence.Builder()
      .setRequestId(store.id.toString)
      .setExpirationDuration(Geofence.NEVER_EXPIRE)
      .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
      .setCircularRegion(store.latitude, store.longitude, geofenceRadius)
      .build
  }

  object OnConnect extends GooglePlayServicesClient.ConnectionCallbacks {
    override def onDisconnected = locationClient = None
    override def onConnected(args: Bundle) = {
      locationClient.removeGeofences(inventoryKeeperIntent, OnRemoved)
    }
  }

  object OnFailure extends GooglePlayServicesClient.OnConnectionFailedListener {
    override def onConnectionFailed(result: ConnectionResult) = {
      android.util.Log.d("CigarFinder", "Connection failed with result code %d".format(result.getErrorCode))
    }
  }

  object OnRemoved extends LocationClient.OnRemoveGeofencesResultListener {
    override def onRemoveGeofencesByPendingIntentResult(status: Int, intent: PendingIntent) = status match {
      case LocationStatusCodes.SUCCESS => locationClient.addGeofences(geofences, inventoryKeeperIntent, OnAdded)
      case _ => locationClient.disconnect //Removal failed. What should we do?
    }
    override def onRemoveGeofencesByRequestIdsResult(status: Int, ids: Array[String]) {}
  }

  object OnAdded extends LocationClient.OnAddGeofencesResultListener {
    override def onAddGeofencesResult(status: Int, ids: Array[String]) = {
      locationClient.disconnect
    }
  }

  lazy val inventoryKeeperIntent = {
    val intent = new Intent(context, classOf[InventoryKeeper])
    PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
  }

  private lazy val playServices = new PlayServices(context)

  private var _locationClient: Option[LocationClient] = None
  private def locationClient = _locationClient match {
    case Some(locationClient) => locationClient
    case None => {
      _locationClient = Some(new LocationClient(context, OnConnect, OnFailure))
      _locationClient.get
    }
  }
  private def locationClient_= (client: Option[LocationClient]) = _locationClient = client
}
