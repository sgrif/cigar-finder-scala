package com.seantheprogrammer.cigar_finder_android

import android.app.PendingIntent
import android.content.{BroadcastReceiver, Context, Intent}
import android.location.{LocationManager, Location}

class Surveillance(context: Context) {
  lazy val locationManager = context.getSystemService(Context.LOCATION_SERVICE).asInstanceOf[LocationManager]

  def beginTracking = {
    val intent = new Intent(context, classOf[WhiteVan])
    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, pendingIntent)
  }
}

class WhiteVan extends BroadcastReceiver {
  override def onReceive(context: Context, intent: Intent) = {
    val key = LocationManager.KEY_LOCATION_CHANGED
    if (intent.hasExtra(key))
      findNearbyStores(context, intent.getExtras.get(key))
  }

  def findNearbyStores(context: Context, location: Object) = location match {
    case location: Location => {
      val intent = new Intent(context, classOf[Concierge])
      intent.putExtra("location", location)
      context.startService(intent)
    }
    case _ => //Do nothing
  }
}
