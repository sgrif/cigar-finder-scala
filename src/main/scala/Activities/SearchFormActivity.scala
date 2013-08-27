package com.seantheprogrammer.cigar_finder_android

import org.scaloid.common._
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.view.View

class SearchFormActivity extends SActivity
with TypedActivity {
  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_cigar_search_form)

    if (savedInstanceState == null) {
      new CigarsLoader().loadCigars { cigars => runOnUiThread(onCigarsLoaded(cigars)) }
    }
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

  private def onCigarsLoaded(cigars: IndexedSeq[String]) = {
    val cigarInput = findView(TR.inputCigarName)
    cigarInput.setThreshold(0)
    cigarInput.setAdapter(new ArrayAdapter[String](this, R.layout.dropdown_item, cigars.toArray))
  }
}
