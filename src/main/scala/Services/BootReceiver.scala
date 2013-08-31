package com.seantheprogrammer.cigar_finder_android

import android.content.{BroadcastReceiver, Context, Intent}

class BootReceiver extends BroadcastReceiver {
  override def onReceive(context: Context, intent: Intent) {
    implicit val ctx = context

    if (appWasRun) {
      new Surveillance().beginTracking()
    }
  }

  def appWasRun(implicit context: Context) = Prefs().appWasRun(false)
}
