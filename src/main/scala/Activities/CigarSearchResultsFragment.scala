package com.seantheprogrammer.cigar_finder_android

import android.app.ListFragment
import android.location.Location
import android.os.Bundle
import android.view.{LayoutInflater, ViewGroup}

class CigarSearchResultsFragment extends ListFragment
with ListHolder {
  lazy val cigarSearcher = new LetMeCigarFinderThatForYou(this, getActivity)

  override def onCreateView(li: LayoutInflater, vg: ViewGroup, b: Bundle) = {
    val view = super.onCreateView(li, vg, b)
    view.setBackgroundResource(R.drawable.list_bg)
    view
  }

  def performSearch(cigarName: String, location: Location) = {
    clearList
    val args = new Bundle
    args.putString("cigarName", cigarName)
    args.putParcelable("location", location)
    getLoaderManager.restartLoader(0, args, cigarSearcher)
  }

  private def clearList = {
    setListAdapter(null)
    setListShown(false)
  }
}
