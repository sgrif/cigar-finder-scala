package com.seantheprogrammer.cigar_finder_android

import android.content.Context
import com.google.android.gms.common.{ConnectionResult, GooglePlayServicesUtil}

class PlayServices(context: Context) {
  def isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS
}
