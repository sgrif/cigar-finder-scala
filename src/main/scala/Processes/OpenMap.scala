package com.seantheprogrammer.cigar_finder_android

import android.content.{Intent, Context}
import java.net.URLEncoder

class OpenMap(context: Context, store: Store) extends ActivityProcess(context) {
  override def action = Intent.ACTION_VIEW
  override def uri = "geo:0,0?q=" + encodedName

  lazy val encodedName = URLEncoder.encode(store.name, "UTF-8").replace("%20", "+")
}
