package com.seantheprogrammer.cigar_finder_android

import android.content.{Context, AsyncTaskLoader}
import android.location.Location

class CigarSearchResultsLoader(context: Context, cigarName: String, location: Location)
extends AsyncTaskLoader[CigarSearchResults](context) {
  var results: Option[CigarSearchResults] = None

  override def onStartLoading = results match {
    case Some(results) => deliverResult(results)
    case None => forceLoad
  }

  override def loadInBackground = {
    val json = new CigarSearchResultsDownloader(cigarName, location).json
    val parser = new CigarSearchResultsParser(json)
    results = Some(new CigarSearchResults(parser.results))
    results.get
  }
}
