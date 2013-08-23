package com.seantheprogrammer.cigar_finder_android

import android.app.Activity
import android.content.Intent
import android.location.Location
import android.os.Bundle
import org.apache.commons.lang3.text.WordUtils

class SearchResultsActivity extends Activity
with TypedActivity
with SearchResultsFragment.Callbacks
with BackNavigation[SearchFormActivity] {
  lazy val cigarName = WordUtils.capitalize(getIntent.getStringExtra("cigarName"))
  lazy val locationName = getIntent.getStringExtra("locationName")
  private var location: Option[Location] = None

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.cigar_search_results_activity)

    if (savedInstanceState == null) {
      loadLocation
    }
  }

  override def onSaveInstanceState(outState: Bundle) = location match {
    case Some(location) => outState.putParcelable("location", location)
    case None => //Do nothing
  }

  override def onRestoreInstanceState(state: Bundle) = state.getParcelable[Location]("location") match {
    case location: Location => performSearch(location)
    case _ => // Do nothing
  }

  override def onSearchResultClicked(results: SearchResults, id: Int) = {
    val intent = new Intent(this, classOf[SearchResultsDetailActivity])
    intent.putExtra("searchResults", results)
    intent.putExtra("tappedId", id)
    startActivity(intent)
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
    updateTitle
    searchResultsFragment.performSearch(cigarName, location)
  }

  def updateTitle = getActionBar.setTitle(cigarName)
  def searchResultsFragment = getFragmentManager.findFragmentById(R.id.cigar_search_results_list).asInstanceOf[SearchResultsFragment]
}
