package com.seantheprogrammer.cigar_finder_android

import android.content.Context
import com.google.android.gms.common.{ConnectionResult, GooglePlayServicesUtil}
import android.app.Activity

class PlayServices(context: Context) {
  lazy val activity = context match {
    case activity: Activity => activity
    case _ => null
  }

  def isAvailable = availabilityCode == ConnectionResult.SUCCESS
  def errorDialog = {
    GooglePlayServicesUtil.getErrorDialog(availabilityCode, activity, PlayServices.enablePlayServices)
  }

  private def availabilityCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context)
}

object PlayServices {
  val enablePlayServices = 9000
}
