package com.seantheprogrammer.cigar_finder_android

import android.app.Activity
import android.location.Location
import android.os.Bundle
import org.apache.commons.lang3.text.WordUtils

class CigarSearchResultsActivity extends Activity
with TypedActivity
with BackNavigation[CigarSearchFormActivity] {
  override def parentActivity = classOf[CigarSearchFormActivity]

  lazy val cigarName = WordUtils.capitalize(getIntent.getStringExtra("cigarName"))
  lazy val locationName = getIntent.getStringExtra("locationName")

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)
    if (savedInstanceState == null) {
      loadLocation
    }
  }

  def loadLocation = {
    new LocationLoader(this, onLocationLoaded).loadLocation(locationName)
  }

  def onLocationLoaded(result: Option[Location]) = result match {
    case Some(location) => {
      val lat = location.getLatitude
      val lon = location.getLongitude
      android.util.Log.d("CigarFinder", "Location loaded %f %f".format(lat, lon))
    }
    case None => android.util.Log.d("CigarFinder", "Location failed to load")
  }
}
