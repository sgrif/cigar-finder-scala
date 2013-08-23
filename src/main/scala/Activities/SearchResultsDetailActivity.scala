package com.seantheprogrammer.cigar_finder_android

import android.app.Activity
import android.os.Bundle

class SearchResultsDetailActivity extends Activity
with BackNavigation[SearchResultsActivity] {
  override def onCreate(b: Bundle) = {
    super.onCreate(b)

    val results = getIntent.getParcelableExtra[SearchResults]("searchResults")
    results.sorted.foreach( result => android.util.Log.d("CigarFinder", result.storeName))
  }
}
