package com.seantheprogrammer.cigar_finder_android

import org.scaloid.common._

class InventoryQueryActivity extends SActivity
with InformationQueryDialog.Callbacks {
  onCreate {
    getIntent.getAction match {
      case InventoryQueryActivity.reportCarried => reportCarried()
      case InventoryQueryActivity.reportNotCarried => reportNotCarried()
      case _ => createDialog()
    }
  }

  override def finish() = {
    prefs.lastInformationReceived = System.currentTimeMillis
    notificationManager.cancel(0)
    super.finish()
  }

  override def dialogCanceled() = finish()

  override def reportCarried() =
    updateApi(searchResult.copy(carried = Some(true)))

  override def reportNotCarried() =
    updateApi(searchResult.copy(carried = Some(false)))

  private def updateApi(newResult: SearchResult) = {
    new ResultUpdater(newResult).execute()
    finish()
  }

  private def createDialog() =
    InformationQueryDialog(searchResult).show(getFragmentManager, "InformationQuery")

  private lazy val searchResult = getIntent.getParcelableExtra[SearchResult]("searchResult")
  private lazy val prefs = Preferences()
}

object InventoryQueryActivity {
  val reportCarried = "report_carried"
  val reportNotCarried = "report_not_carried"
}
