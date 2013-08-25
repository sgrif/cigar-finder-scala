package com.seantheprogrammer.cigar_finder_android

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.view.ViewPager
import android.support.v4.view.ViewPager.OnPageChangeListener

class SearchResultsDetailActivity extends FragmentActivity with TypedActivity
with BackNavigation[SearchResultsActivity]
with SearchResultFragment.Callbacks {
  override def parentActivity = classOf[SearchResultsActivity]

  lazy val adapter = new SearchResultsPagerAdapter(getSupportFragmentManager, sortedResults)
  lazy val results = getIntent.getParcelableExtra[SearchResults]("searchResults")
  lazy val sortedResults = results.sorted

  override def onCreate(b: Bundle) = {
    super.onCreate(b)

    setContentView(R.layout.cigar_search_results_detail_activity)
    setupPager

    if (b == null) {
      val id = getIntent.getIntExtra("tappedId", 0)
      val index = results.indexForId(id)
      setTitleForIndex(index)
      findView(TR.pager).setCurrentItem(index)
    }
  }

  override def updateResult(result: SearchResult, carried: Boolean) = {
    val intent = new Intent
    intent.putExtra("resultIndex", results.indexOf(result))
    intent.putExtra("carried", carried)
    setResult(Activity.RESULT_OK, intent)
    finish
  }

  private def setupPager = {
    val pager = findView(TR.pager)
    pager.setAdapter(adapter)
    pager.setOnPageChangeListener(new OnPageChangeListener {
      def onPageScrollStateChanged(p1: Int) {}
      def onPageScrolled(p1: Int, p2: Float, p3: Int) {}
      def onPageSelected(page: Int) = {
        setTitleForIndex(page)
      }
    })
  }

  private def setTitleForIndex(index: Int) = getActionBar.setTitle(sortedResults(index).storeName)
}
