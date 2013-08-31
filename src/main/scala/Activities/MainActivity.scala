package com.seantheprogrammer.cigar_finder_android

import android.app.Activity._
import android.app.DialogFragment
import android.os.Bundle
import android.content.Intent
import org.scaloid.common.SActivity

class MainActivity extends SActivity {
  override def onCreate(b: Bundle) {
    super.onCreate(b)
    checkPlayServicesAvailability()
  }

  override def onActivityResult(req: Int, res: Int, data: Intent) {
    req match {
      case PlayServices.enablePlayServices => res match {
        case RESULT_OK => checkPlayServicesAvailability()
        case _ => android.util.Log.d("CigarFinder", "Unrecognized response code %d"
          .format(res))
      }
      case _ => android.util.Log.d("CigarFinder", "Unrecognized request code %d"
        .format(req))
    }
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
