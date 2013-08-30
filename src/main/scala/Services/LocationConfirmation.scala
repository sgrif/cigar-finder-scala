package com.seantheprogrammer.cigar_finder_android

import android.content.Context
import com.google.android.gms.common.{ConnectionResult, GooglePlayServicesClient}
import com.google.android.gms.location.ActivityRecognitionClient
import android.os.Bundle
import org.scaloid.common._

class LocationConfirmation(implicit context: Context) {
  def createNotificationIfConfirmed() {
    if (playServices.isAvailable) beginUpdatesClient.connect()
    else android.util.Log.d("CigarFinder", "Play services unavailable")
  }

  def cancel() {
    if (playServices.isAvailable) endUpdatesClient.connect()
  }

  object BeginUpdates extends GooglePlayServicesClient.ConnectionCallbacks {
    override def onConnected(data: Bundle) {
      beginUpdatesClient.requestActivityUpdates(0, serviceIntent)
      beginUpdatesClient.disconnect()
    }

    override def onDisconnected() {}
  }

  object CancelUpdates extends GooglePlayServicesClient.ConnectionCallbacks {
    override def onConnected(data: Bundle) {
      endUpdatesClient.removeActivityUpdates(serviceIntent)
      endUpdatesClient.disconnect()
    }

    override def onDisconnected() {}
  }

  object OnFailure extends GooglePlayServicesClient.OnConnectionFailedListener {
    def onConnectionFailed(result: ConnectionResult) {
      android.util.Log.e("CigarFinder", "Connection failed with result code %d".format(result.getErrorCode))
    }
  }

  private lazy val serviceIntent = {
    val intent = SIntent[LocationConfirmationService]
    pendingService(intent)
  }
  private lazy val playServices = new PlayServices(context)
  private lazy val beginUpdatesClient = new ActivityRecognitionClient(
    context, BeginUpdates, OnFailure)
  private lazy val endUpdatesClient = new ActivityRecognitionClient(
    context, CancelUpdates, OnFailure)
}
