package com.seantheprogrammer.cigar_finder_android

import android.app.{Activity, ListFragment}
import android.location.Location
import android.os.Bundle
import android.view.{LayoutInflater, View, ViewGroup}
import android.widget.ListView

class CigarSearchResultsFragment extends ListFragment {
  import CigarSearchResultsFragment.Callbacks

  private var callbacks: Callbacks = DummyCallbacks

  lazy val adapter = new CigarSearchResultsAdapter(getActivity)
  lazy val cigarSearcher = new LetMeCigarFinderThatForYou(getActivity, onResultsLoaded)

  override def onAttach(activity: Activity) = {
    super.onAttach(activity)

    callbacks = activity match {
      case cb: Callbacks => cb
      case _ => DummyCallbacks
    }
  }

  override def onCreateView(li: LayoutInflater, vg: ViewGroup, b: Bundle) = {
    val view = super.onCreateView(li, vg, b)
    view.setBackgroundResource(R.drawable.list_bg)
    view
  }

  override def onListItemClick(lv: ListView, v: View, index: Int, id: Long) = {
    val item = adapter.getItem(index) match {
      case result: CigarSearchResult => callbacks.onSearchResultClicked(adapter.results.sorted.toArray, id.toInt)
      case _ => //Do nothing
    }
  }

  def onResultsLoaded(results: Option[CigarSearchResults]) = {
    adapter.results = results
    setListAdapter(results.isEmpty match {
      case false => adapter
      case true => null
    })
  }

  def performSearch(cigarName: String, location: Location) = {
    clearList
    val args = new Bundle
    args.putString("cigarName", cigarName)
    args.putParcelable("location", location)
    getLoaderManager.restartLoader(0, args, cigarSearcher)
  }

  private def clearList = {
    setListAdapter(null)
    setListShown(false)
  }

  object DummyCallbacks extends Callbacks {
    override def onSearchResultClicked(a: Array[CigarSearchResult], i: Int) = {
      android.util.Log.d("CigarFinder", "Search results fragment called onSearchResultClicked on DummyCallbacks")
    }
  }
}

object CigarSearchResultsFragment {
  trait Callbacks {
    def onSearchResultClicked(results: Array[CigarSearchResult], id: Int): Unit
  }
}
