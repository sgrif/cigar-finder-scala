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

  private var cigar: Option[String] = None
  private var location: Option[Location] = None

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)
    if (savedInstanceState != null) {
      restoreInstanceState(savedInstanceState)
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
    (cigar, location) match {
      case (Some(cigar), Some(location)) => {
        out.putString("cigar", cigar)
        out.putParcelable("location", location)
      }
      case _ => //Do nothing
    }
  }

  private def restoreInstanceState(in: Bundle) = {
    if (in.containsKey("cigar"))
      cigar = Some(in.getString("cigar"))
    if (in.containsKey("location"))
      location = Some(in.getParcelable("location"))
    displayResults(in.getParcelable("results"))
  }

  def updateResultCarried(index: Int, carried: Boolean) = {
    val results = adapter.results.updateResultCarried(index, carried)
    displayResults(results)
  }

  def displayResults(results: SearchResults): Unit = results.isEmpty match {
    case false => {
      adapter.results = results
      runOnUiThread(setListAdapter(adapter))
    }
    case true => loadData
  }

  def performSearch(cigar: String, location: Location) = {
    clearList()
    this.cigar = Some(cigar)
    this.location = Some(location)
    loadData()
  }

  private def loadData() = (cigar, location) match {
    case (Some(cigar), Some(location)) => performLoad(cigar, location)
    case _ => //No name or location, do nothing
  }

  private def performLoad(cigar: String, location: Location) = {
    val cigarSearcher = new LetMeCigarFinderThatForYou(cigar, location)
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
