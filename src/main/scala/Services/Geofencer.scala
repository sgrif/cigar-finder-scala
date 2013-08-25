package com.seantheprogrammer.cigar_finder_android

import android.content.Context
import com.google.android.gms.location.{Geofence, LocationClient}
import com.google.android.gms.common.GooglePlayServicesClient

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

  private def buildGeofence(store: Store) = {
    new Geofence.Builder()
      .setRequestId(store.id.toString)
      .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
      .setCircularRegion(store.latitude, store.longitude, 25)
      .build
  }

  object OnConnect extends GooglePlayServicesClient.ConnectionCallbacks {
  }

  object OnFailure extends GooglePlayServicesClient.OnConnectionFailedListener {
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
