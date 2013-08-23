package com.seantheprogrammer.cigar_finder_android

import android.app.LoaderManager.LoaderCallbacks
import android.content.{Context, Loader}
import android.location.Location
import android.os.Bundle

class LetMeCigarFinderThatForYou(context: Context, callback: Option[SearchResults] => Unit)
extends LoaderCallbacks[SearchResults] {
  type L = Loader[SearchResults]

  override def onCreateLoader(id: Int, args: Bundle) = {
    val cigarName = args.getString("cigarName")
    val location = args.getParcelable[Location]("location")
    new SearchResultsLoader(context, cigarName, location)
  }

  override def onLoadFinished(l: L, results: SearchResults) = {
    callback(Some(results))
  }

  override def onLoaderReset(l: L) = {
    callback(None)
  }
}
