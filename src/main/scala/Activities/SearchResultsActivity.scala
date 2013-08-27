package com.seantheprogrammer.cigar_finder_android

import android.app.Activity
import android.content.Intent
import android.location.Location
import android.os.Bundle

class SearchResultsActivity extends Activity
with TypedActivity
with SearchResultsFragment.Callbacks
with BackNavigation[SearchFormActivity] {
  override def parentActivity = classOf[SearchFormActivity]

  lazy val cigarName = getIntent.getStringExtra("cigarName").capitalize
  lazy val locationName = getIntent.getStringExtra("locationName")
  private var location: Option[Location] = None

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.cigar_search_results_activity)
    updateTitle

    if (savedInstanceState == null) {
      loadLocation
    }
  }

  override def onSaveInstanceState(outState: Bundle) = {
    super.onSaveInstanceState(outState)
    location match {
      case Some(location) => outState.putParcelable("location", location)
      case None => //Do nothing
    }
  }

  override def onRestoreInstanceState(state: Bundle) = {
    super.onRestoreInstanceState(state)
    state.getParcelable[Location]("location") match {
      case location: Location => this.location = Some(location)
      case _ => // Do nothing
    }
  }

  override def onSearchResultClicked(results: SearchResults, id: Int) = {
    val intent = new Intent(this, classOf[SearchResultsDetailActivity])
    intent.putExtra("searchResults", results)
    intent.putExtra("tappedId", id.toInt)
    startActivityForResult(intent, 1)
  }

  override def onActivityResult(code: Int, result: Int, data: Intent) = {
    if (result == Activity.RESULT_OK) {
      val index = data.getIntExtra("resultIndex", 0)
      val carried = data.getBooleanExtra("carried", false)
      searchResultsFragment.updateResultCarried(index, carried)
    }
  }

  def loadLocation = {
    new LocationLoader(this, onLocationLoaded).loadLocation(locationName)
  }

  def onLocationLoaded(result: Option[Location]) = result match {
    case Some(location) => performSearch(location)
    case None => android.util.Log.d("CigarFinder", "Location failed to load")
  }

  def performSearch(location: Location) = {
    this.location = Some(location)
    searchResultsFragment.performSearch(cigarName, location)
  }

  def updateTitle = getActionBar.setTitle(cigarName)
  def searchResultsFragment = getFragmentManager.findFragmentById(R.id.cigar_search_results_list).asInstanceOf[SearchResultsFragment]
}
