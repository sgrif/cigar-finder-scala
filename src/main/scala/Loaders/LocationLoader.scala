package com.seantheprogrammer.cigar_finder_android

import android.app.AlarmManager
import android.content.Context
import android.os.Bundle
import android.location._
import java.io.IOException
import org.scaloid.common.SystemService

class LocationLoader(callback: Option[Location] => Any)(implicit context: Context)
extends SimpleLocationManager
with SystemService {
  val worstAccuracy = 1000
  val oldestTime = AlarmManager.INTERVAL_FIFTEEN_MINUTES

  def loadLocation(locationName: String) = {
    if (locationName == null || locationName.isEmpty) {
      getUserLocation
    } else {
      geocodeLocation(locationName)
    }
  }

  def getUserLocation = {
    locationManager.lastLocationOrReload(Listener, isAcceptable)
  }

  def isAcceptable(location: Location) = {
    val earliestTime = System.currentTimeMillis - oldestTime
    location.getTime > earliestTime && location.getAccuracy < worstAccuracy
  }

  def geocodeLocation(locationName: String) = {
    try {
      val address = geocoder.getFromLocationName(locationName, 1).get(0)
      val location = new ConstructedLocation(address.getLatitude, address.getLongitude)
      callback(Some(location))
    } catch {
      case ex: IOException => {
        android.util.Log.d("CigarFinder", "Unable to load location", ex)
        callback(None)
      }
    }
  }

  def geocoder = new Geocoder(context)

  object Listener extends LocationListener {
    override def onLocationChanged(location: Location) = callback(Some(location))
    override def onStatusChanged(s: String, i: Int, b: Bundle) {}
    override def onProviderEnabled(s: String) {}
    override def onProviderDisabled(s: String) {}
  }
}

class ConstructedLocation(lat: Double, lon: Double)
extends Location("CONSTRUCTED_LOCATION_PROVIDER") {
  setLatitude(lat)
  setLongitude(lon)
}
