package com.seantheprogrammer.cigar_finder_android

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.{View, LayoutInflater, ViewGroup}

class SearchResultFragment extends Fragment with FindView {
  lazy val result = getArguments.getParcelable[SearchResult]("searchResult")

  override def onCreateView(inflater: LayoutInflater, parent: ViewGroup, b: Bundle) = {
    val view = inflater.inflate(R.layout.search_result_detail_fragment, parent, false)

    view.findView(TR.store_name).setText(result.storeName)
    view.findView(TR.store_address).setText(result.store.address)
    setupCarriedDescription(view)
    view.findView(TR.static_map).location = Some(result.store.location)

    view
  }

  private def setupCarriedDescription(view: View) = {
    view.findView(TR.last_reported).setText(carriedDescription)
    if (result.hasInformation) {

    } else {
      view.findView(TR.last_updated_container).setVisibility(View.GONE)
    }
  }

  private def carriedDescription = result.hasInformation match {
    case true => "Foo"
    case false => "Bar"
  }
}

object SearchResultFragment {
  def newInstance(result: SearchResult) = {
    val args = new Bundle
    args.putParcelable("searchResult", result)
    val fragment = new SearchResultFragment
    fragment.setArguments(args)
    fragment
  }
}
