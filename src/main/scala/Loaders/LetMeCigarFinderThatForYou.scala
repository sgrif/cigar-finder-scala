package com.seantheprogrammer.cigar_finder_android

import android.app.LoaderManager.LoaderCallbacks
import android.content.{Context, Loader}
import android.location.Location
import android.os.Bundle

class LetMeCigarFinderThatForYou(context: Context, callback: Option[CigarSearchResults] => Unit)
extends LoaderCallbacks[CigarSearchResults] {
  type L = Loader[CigarSearchResults]

  override def onCreateLoader(id: Int, args: Bundle) = {
    val cigarName = args.getString("cigarName")
    val location = args.getParcelable[Location]("location")
    new CigarSearchResultsLoader(context, cigarName, location)
  }

  override def onLoadFinished(l: L, results: CigarSearchResults) = {
    callback(Some(results))
  }

  override def onLoaderReset(l: L) = {
    callback(None)
  }
}
