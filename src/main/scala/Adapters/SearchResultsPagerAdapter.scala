package com.seantheprogrammer.cigar_finder_android

import android.support.v4.app.{FragmentManager, FragmentStatePagerAdapter}

class SearchResultsPagerAdapter(fm: FragmentManager, results: IndexedSeq[SearchResult])
extends FragmentStatePagerAdapter(fm) {
  override def getCount = results.size
  override def getItem(index: Int) = SearchResultFragment.newInstance(results(index))
}
