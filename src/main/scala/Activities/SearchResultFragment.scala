package com.seantheprogrammer.cigar_finder_android

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view._

class SearchResultFragment extends Fragment with FindView with OnClick {
  lazy val result = getArguments.getParcelable[SearchResult]("searchResult")

  override def onCreate(b: Bundle) = {
    super.onCreate(b)
    setHasOptionsMenu(true)
  }

  override def onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) = {
    inflater.inflate(R.menu.detail_menu, menu)
  }

  override def onOptionsItemSelected(item: MenuItem) = item.getItemId match {
    case R.id.report_carried => callbacks.updateResult(result, true); true
    case R.id.report_not_carried => callbacks.updateResult(result, false); true
    case _ => super.onOptionsItemSelected(item)
  }

  override def onCreateView(inflater: LayoutInflater, parent: ViewGroup, b: Bundle) = {
    val view = inflater.inflate(R.layout.search_result_detail_fragment, parent, false)

    view.findView(TR.store_name).setText(result.storeName)
    view.findView(TR.store_address).setText(result.store.address)
    setupCarriedDescription(view)
    view.findView(TR.static_map).location = Some(result.store.location)
    view.findView(TR.store_map_button).onClick(openMap)
    view.findView(TR.store_directions_button).onClick(openDirections)
    view.findView(TR.store_call_button).onClick(callStore)
    view.findView(TR.static_map).onClick(openMap)

    view
  }

  private def openMap = new OpenMap(getActivity, result.store).run
  private def openDirections = new OpenDirections(getActivity, result.store).run
  private def callStore = new CallStore(getActivity, result.store).run

  private def setupCarriedDescription(view: View) = {
    view.findView(TR.last_reported).setText(carriedDescription)
    if (result.hasInformation) {
      val time = new TimeToString(result.lastUpdated)
      view.findView(TR.last_updated_at_number).setText(time.numberPart)
      view.findView(TR.last_updated_at_text).setText(time.unitPart)
    } else {
      view.findView(TR.last_updated_container).setVisibility(View.GONE)
    }
  }

  private def carriedDescription = {
    val builder = new StringBuilder

    if (result.hasInformation) {
      builder.append("Time since last report that ")
        .append(result.cigar).append(' ')
        .append(carriedPresentTense)
        .append(" at this location")
    } else {
      builder.append("We have no information on whether ")
        .append(result.cigar)
        .append(" is carried at this location")
    }

    builder.toString
  }

  private def carriedPresentTense = result.isCarried match {
    case true => "is carried"
    case false => "is not carried"
  }

  private var callbacks: SearchResultFragment.Callbacks = DummyCallbacks

  override def onAttach(activity: Activity) = {
    super.onAttach(activity)
    callbacks = activity match {
      case cb: SearchResultFragment.Callbacks => cb
      case _ => DummyCallbacks
    }
  }

  object DummyCallbacks extends SearchResultFragment.Callbacks {
    override def updateResult(result: SearchResult, carried: Boolean) {}
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

  trait Callbacks {
    def updateResult(result: SearchResult, carried: Boolean): Unit
  }
}
