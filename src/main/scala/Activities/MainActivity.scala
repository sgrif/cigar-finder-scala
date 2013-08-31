package com.seantheprogrammer.cigar_finder_android

import android.app.DialogFragment
import android.os.Bundle
import android.content.Intent
import org.scaloid.common.SActivity

class MainActivity extends SActivity {
  override def onCreate(b: Bundle) = {
    super.onCreate(b)
    checkPlayServicesAvailability()
  }

  private def checkPlayServicesAvailability() = playServices.isAvailable match {
    case true => startPrimaryActivity()
    case false => displayPlayServicesError()
  }

  private def startPrimaryActivity() = {
    saveAppWasRun()
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

  private def saveAppWasRun() {
    Prefs().appWasRun = true
  }

  private def beginLocationUpdates() {
    new Surveillance().beginTracking()
  }

  private lazy val playServices = new PlayServices(this)
}
