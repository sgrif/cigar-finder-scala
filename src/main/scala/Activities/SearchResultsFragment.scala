package com.seantheprogrammer.cigar_finder_android

import android.app.{ListFragment, Activity}
import android.location.Location
import android.os.Bundle
import android.view.{LayoutInflater, View, ViewGroup}
import android.widget.ListView
import org.scaloid.common._
import scala.concurrent.ExecutionContext.Implicits.global

class SearchResultsFragment extends ListFragment {
  import SearchResultsFragment.Callbacks

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)
    if (savedInstanceState != null) {
      displayResults(savedInstanceState.getParcelable("results"))
    }
  }

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
      case result: SearchResult => callbacks.onSearchResultClicked(adapter.results, id.toInt)
      case _ => //Do nothing
    }
  }

  override def onSaveInstanceState(out: Bundle) = {
    super.onSaveInstanceState(out)
    out.putParcelable("results", adapter.results)
  }

  def updateResultCarried(index: Int, carried: Boolean) = {
    val results = adapter.results.updateResultCarried(index, carried)
    displayResults(results)
  }

  def displayResults(results: SearchResults) = {
    adapter.results = results
    runOnUiThread(setListAdapter(adapter))
  }

  def performSearch(cigarName: String, location: Location) = {
    clearList()
    val cigarSearcher = new LetMeCigarFinderThatForYou(cigarName, location)
    cigarSearcher.loadSearchResults.onSuccess {
      case results => displayResults(results)
    }
  }

  private var callbacks: Callbacks = DummyCallbacks
  implicit lazy val context = getActivity
  private lazy val adapter = new SearchResultsAdapter

  private def clearList() = {
    setListAdapter(null)
    setListShown(false)
  }

  object DummyCallbacks extends Callbacks {
    override def onSearchResultClicked(c: SearchResults, i: Int) = {
      android.util.Log.d("CigarFinder", "Search results fragment called onSearchResultClicked on DummyCallbacks")
    }
  }
}

object SearchResultsFragment {
  trait Callbacks {
    def onSearchResultClicked(results: SearchResults, id: Int): Unit
  }
}
