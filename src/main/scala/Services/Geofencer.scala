package com.seantheprogrammer.cigar_finder_android

import android.app.PendingIntent
import android.content.{Context, Intent}
import android.os.Bundle
import com.google.android.gms.location.{Geofence, LocationClient}
import com.google.android.gms.common.GooglePlayServicesClient
import scala.collection.JavaConversions._

class Geofencer(context: Context, stores: IndexedSeq[Store]) {
  private var inProgress = _locationClient.isEmpty

  def updateAlerts: Unit = playServices.isAvailable match {
    case true => inProgress match {
      case true => resetConnection; updateAlerts
      case false => locationClient.connect
    }
    case false => // Do nothing
  }

  private def resetConnection = {
    locationClient.disconnect
    locationClient = None
  }

  lazy val geofences: java.util.List[Geofence] = stores.map(buildGeofence).toList

  private def buildGeofence(store: Store) = {
    new Geofence.Builder()
      .setRequestId(store.id.toString)
      .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
      .setCircularRegion(store.latitude, store.longitude, 25)
      .build
  }

  object OnConnect extends GooglePlayServicesClient.ConnectionCallbacks {
    override def onDisconnected {}
    override def onConnected(args: Bundle) = {
      val intent = new Intent(context, classOf[InventoryKeeper])
      val pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
      locationClient.addGeofences(geofences, pendingIntent, OnCompletion)
    }
  }

  object OnFailure extends GooglePlayServicesClient.OnConnectionFailedListener {
  }

  object OnCompletion extends LocationClient.OnAddGeofencesResultListener {
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
