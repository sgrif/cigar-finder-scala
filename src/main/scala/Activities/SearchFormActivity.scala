package com.seantheprogrammer.cigar_finder_android

import org.scaloid.common._
import android.content.Intent
import scala.concurrent.ExecutionContext.Implicits.global
import android.os.Bundle

class SearchFormActivity extends SActivity
with TypedActivity {
  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_cigar_search_form)
    new CigarsLoader().loadCigars.onSuccess { case cigars => setCigarAutocomplete(cigars) }
    findView(TR.searchSubmit).onClick(performSearch())
  }

  def performSearch() {
    val cigarName = findView(TR.inputCigarName).getText.toString.trim
    val locationName = findView(TR.inputLocationName).getText.toString.trim

    if (!cigarName.isEmpty) {
      val intent = new Intent(this, classOf[SearchResultsActivity])
      intent.putExtra("cigarName", cigarName)
      intent.putExtra("locationName", locationName)
      startActivity(intent)
    }
  }

  private def setCigarAutocomplete(cigars: IndexedSeq[String]) = runOnUiThread {
    findView(TR.inputCigarName)
      .threshold(0)
      .adapter(SArrayAdapter(R.layout.dropdown_item, cigars: _*))
  }
}
