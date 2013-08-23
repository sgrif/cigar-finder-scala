package com.seantheprogrammer.cigar_finder_android

import android.app.LoaderManager.LoaderCallbacks
import android.content.{Context, Loader}
import android.location.Location
import android.os.Bundle
import android.widget.ListAdapter

trait ListHolder {
  def setListAdapter(adapter: ListAdapter)
}

class LetMeCigarFinderThatForYou(listHolder: ListHolder, context: Context)
extends LoaderCallbacks[CigarSearchResults] {
  type L = Loader[CigarSearchResults]

  lazy val adapter = new CigarSearchResultsAdapter(context)

  override def onCreateLoader(id: Int, args: Bundle) = {
    val cigarName = args.getString("cigarName")
    val location = args.getParcelable[Location]("location")
    new CigarSearchResultsLoader(context, cigarName, location)
  }

  override def onLoadFinished(l: L, results: CigarSearchResults) = {
    adapter.setResults(Some(results))
    listHolder.setListAdapter(adapter)
  }

  override def onLoaderReset(l: L) = {
    adapter.setResults(None)
    listHolder.setListAdapter(null)
  }
}
