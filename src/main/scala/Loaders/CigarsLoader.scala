package com.seantheprogrammer.cigar_finder_android

import android.content.{Context, AsyncTaskLoader}
import org.apache.commons.io.IOUtils
import org.json.JSONArray
import java.net.URL

class CigarsLoader(context: Context)
extends AsyncTaskLoader[IndexedSeq[String]](context) {
  private var results: Option[IndexedSeq[String]] = None

  override def loadInBackground = {
    val json = new JSONArray(cigarsJson)
    results = Some(0 until json.length map(json.getString(_)))
    results.get
  }

  override def onStartLoading = results match {
    case Some(cigars) => deliverResult(cigars)
    case None => forceLoad
  }

  def cigarsJson = IOUtils.toString(apiUrl, "UTF-8")

  lazy val apiUrl = new URL("http://cigar-finder.com/cigars.json")
}
