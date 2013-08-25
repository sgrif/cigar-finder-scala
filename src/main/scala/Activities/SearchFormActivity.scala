package com.seantheprogrammer.cigar_finder_android

import android.app.{Activity, LoaderManager}
import android.content.{Loader, Intent}
import android.os.Bundle
import android.widget.ArrayAdapter
import android.view.View

class SearchFormActivity extends Activity
with TypedActivity
with LoaderManager.LoaderCallbacks[IndexedSeq[String]] {
  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(R.layout.activity_cigar_search_form)
    getLoaderManager.initLoader(0, null, this)
    beginLocationUpdates
  }

  def performSearch(v: View) = {
    val cigarName = findView(TR.inputCigarName).getText.toString.trim
    val locationName = findView(TR.inputLocationName).getText.toString.trim

    if (!cigarName.isEmpty) {
      val intent = new Intent(this, classOf[SearchResultsActivity])
      intent.putExtra("cigarName", cigarName)
      intent.putExtra("locationName", locationName)
      startActivity(intent)
    }
  }

  override def onLoaderReset(l: Loader[IndexedSeq[String]]) = findView(TR.inputCigarName).getAdapter.asInstanceOf[ArrayAdapter[String]].clear
  override def onLoadFinished(l: Loader[IndexedSeq[String]], data: IndexedSeq[String]) = {
    val cigarInput = findView(TR.inputCigarName)
    cigarInput.setThreshold(0)
    cigarInput.setAdapter(new ArrayAdapter[String](this, R.layout.dropdown_item, data.toArray))
  }
  override def onCreateLoader(id: Int, args: Bundle) = new CigarsLoader(this)

  private def beginLocationUpdates = new Surveillance(this).beginTracking
}
