package com.seantheprogrammer.cigar_finder_android

import android.content.{Intent, Context}
import java.net.URLEncoder

class OpenDirections(context: Context, store: Store) extends ActivityProcess(context) {
  override def action = Intent.ACTION_VIEW
  override def uri = "https://maps.google.com/maps?daddr=" + encodedQuery.replace("%20", "+")

  lazy val query = "%s, %s".format(store.name, store.address)
  lazy val encodedQuery = URLEncoder.encode(query, "UTF-8")
}
