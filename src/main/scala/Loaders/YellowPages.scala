package com.seantheprogrammer.cigar_finder_android

import android.content.Context
import android.location.Location
import com.loopj.android.http._

class YellowPages(context: Context, location: Location) {
  def lookupStores(callback: IndexedSeq[Store] => Unit) = {
    val url = CigarFinder.baseUrl + "cigar_stores/nearby"
    val params = new RequestParams
    params.put("latitude", location.getLatitude.toString)
    params.put("longitude", location.getLongitude.toString)

    YellowPages.client.get(url, params, new AsyncHttpResponseHandler {
      override def onSuccess(json: String) = callback(parseResult(json))
    })
  }

  def parseResult(json: String) = new StoreListParser(json).stores
}

object YellowPages {
  val client = new AsyncHttpClient(){{
    addHeader("Accept", "application/json")
  }}
}
