package com.seantheprogrammer.cigar_finder_android

import android.location._

class LocationManagerWithExtensions(locationManager: LocationManager) {
  def lastLocationOrReload(listener: LocationListener, isAcceptable: Location => Boolean) = {
    val provider = lowPowerProvider
    val location = locationManager.getLastKnownLocation(provider)

    if (location != null && isAcceptable(location)) {
      listener.onLocationChanged(location)
    } else {
      locationManager.requestSingleUpdate(provider, listener, null)
    }
  }

  def lowPowerProvider = {
    val criteria = new Criteria
    criteria.setPowerRequirement(Criteria.POWER_LOW)
    locationManager.getBestProvider(criteria, true)
  }
}

trait SimpleLocationManager {
  implicit def addExtensionsToLocationManager(locationManager: LocationManager) = {
    new LocationManagerWithExtensions(locationManager)
  }
}
