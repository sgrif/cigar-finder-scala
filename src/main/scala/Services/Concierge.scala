package com.seantheprogrammer.cigar_finder_android

import android.app.Service
import android.content.Intent
import android.location.Location

class Concierge extends Service {
  override def onBind(intent: Intent) = null

  override def onStartCommand(intent: Intent, flags: Int, id: Int) = {
    handleIntent(intent)
    Service.START_STICKY
  }

  private def handleIntent(intent: Intent) = intent match {
    case null => // Do nothing, null passed from system restart
    case _ => {
      lookupStores(intent.getParcelableExtra("location"))
    }
  }

  private def lookupStores(location: Location) = {
    if (shouldUpdateForLocation(location)) {
      new YellowPages(this, location).lookupStores(onStoresLoaded)
    }
  }

  private def onStoresLoaded(stores: IndexedSeq[Store]) = new Geofencer(stores).updateAlerts

  private def shouldUpdateForLocation(location: Location) = {
    val largeEnoughDistance: Boolean = Concierge.lastUpdatedLocation match {
      case None => true
      case Some(lastLocation) => lastLocation.distanceTo(location) > 5000
    }
    enoughTimePassed && largeEnoughDistance
  }

  private def enoughTimePassed = {
    System.currentTimeMillis - Concierge.lastUpdatedTime >= 60000
  }
}

object Concierge {
  var _lastUpdatedLocation: Option[Location] = None
  var lastUpdatedTime: Long = 0

  def lastUpdatedLocation = _lastUpdatedLocation
  def lastUpdatedLocation_= (location: Location) = {
    _lastUpdatedLocation = Some(location)
    lastUpdatedTime = System.currentTimeMillis
  }
}
