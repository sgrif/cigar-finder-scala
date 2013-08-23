package com.seantheprogrammer.cigar_finder_android

import android.content.{Context, AsyncTaskLoader}
import android.location.Location

class SearchResultsLoader(context: Context, cigarName: String, location: Location)
extends AsyncTaskLoader[SearchResults](context) {
  var results: Option[SearchResults] = None

  override def onStartLoading = results match {
    case Some(results) => deliverResult(results)
    case None => forceLoad
  }

  override def loadInBackground = {
    val json = new SearchResultsDownloader(cigarName, location).json
    val parser = new SearchResultsParser(json)
    results = Some(new SearchResults(parser.results))
    results.get
  }
}
