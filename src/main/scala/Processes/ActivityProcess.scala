package com.seantheprogrammer.cigar_finder_android

import android.content.{Context, Intent}
import android.net.Uri

abstract class ActivityProcess(context: Context) {
  def action: String
  def uri: String

  def run = {
    val intent = new Intent(action, Uri.parse(uri))
    context.startActivity(intent)
  }
}
