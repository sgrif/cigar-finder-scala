package com.seantheprogrammer.cigar_finder_android

import android.app.{Activity, LoaderManager}
import android.content.Loader
import android.os.Bundle
import android.widget.ArrayAdapter

class CigarSearchFormActivity extends Activity
with TypedActivity
with LoaderManager.LoaderCallbacks[IndexedSeq[String]] {
  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(R.layout.activity_cigar_search_form)
    getLoaderManager.initLoader(0, null, this)
  }

  override def onLoaderReset(l: Loader[IndexedSeq[String]]) = null
  override def onLoadFinished(l: Loader[IndexedSeq[String]], data: IndexedSeq[String]) = {
    val cigarInput = findView(TR.inputCigarName)
    cigarInput.setThreshold(0)
    cigarInput.setAdapter(new ArrayAdapter[String](this, R.layout.dropdown_item, data.toArray))
  }
  override def onCreateLoader(id: Int, args: Bundle) = new CigarsLoader(this)
}
