package com.seantheprogrammer.cigar_finder_android

import android.os.AsyncTask
import com.loopj.android.http._

class ResultUpdater(result: SearchResult) {
  def execute = updateApi(result.isCarried match {
    case true => "report_carried"
    case false => "report_not_carried"
  })

  private def updateApi(uri: String) = {
    val params = new RequestParams
    params.put("cigar_store_id", result.store.id.toString)
    params.put("cigar", result.cigar)

    val url = "%s/cigar_search_results/%s".format(CigarFinder.baseUrl, uri)

    ResultUpdater.client.post(url, params, new AsyncHttpResponseHandler)
  }
}

object ResultUpdater {
  lazy val client = {
    val client = new AsyncHttpClient
    client.addHeader("Accept", "application/json")
    client
  }
}
