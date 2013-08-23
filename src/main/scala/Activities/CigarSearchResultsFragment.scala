package com.seantheprogrammer.cigar_finder_android

import android.app.ListFragment
import android.app.LoaderManager.LoaderCallbacks
import android.content.Loader
import android.location.Location
import android.os.Bundle
import android.view.{LayoutInflater, ViewGroup}

class CigarSearchResultsFragment extends ListFragment
with LoaderCallbacks[CigarSearchResults] {
  type L = Loader[CigarSearchResults]

  lazy val adapter = new CigarSearchResultsAdapter(getActivity)

  override def onCreateView(li: LayoutInflater, vg: ViewGroup, b: Bundle) = {
    val view = super.onCreateView(li, vg, b)
    view.setBackgroundResource(R.drawable.list_bg)
    view
  }

  def performSearch(cigarName: String, location: Location) = {
    clearList
    val args = new Bundle
    args.putString("cigarName", cigarName)
    args.putParcelable("location", location)
    getLoaderManager.restartLoader(0, args, this)
  }

  private def clearList = {
    setListAdapter(null)
    setListShown(false)
  }

  override def onCreateLoader(id: Int, args: Bundle) = {
    val cigarName = args.getString("cigarName")
    val location = args.getParcelable[Location]("location")
    new CigarSearchResultsLoader(getActivity, cigarName, location)
  }
  override def onLoadFinished(l: L, results: CigarSearchResults) = {
    adapter.setResults(Some(results))
    setListAdapter(adapter)
  }
  override def onLoaderReset(l: L) = setListAdapter(null)
}
