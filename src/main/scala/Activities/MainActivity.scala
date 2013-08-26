package com.seantheprogrammer.cigar_finder_android

import android.app.{DialogFragment, Activity}
import android.os.Bundle
import android.content.Intent

class MainActivity extends Activity {
  override def onCreate(b: Bundle) = {
    super.onCreate(b)
    checkPlayServicesAvailability()
  }

  private def checkPlayServicesAvailability() = playServices.isAvailable match {
    case true => startPrimaryActivity()
    case false => displayPlayServicesError()
  }

  private def startPrimaryActivity() = {
    beginLocationUpdates()
    val intent = new Intent(this, classOf[SearchFormActivity])
    startActivity(intent)
  }

  private def displayPlayServicesError() = {
    val dialogFragment = new DialogFragment {
      override def onCreateDialog(b: Bundle) = playServices.errorDialog
    }
    dialogFragment.show(getFragmentManager, "Play Services")
  }

  private def beginLocationUpdates() = new Surveillance(this).beginTracking

  private lazy val playServices = new PlayServices(this)
}
