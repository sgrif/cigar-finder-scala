package com.seantheprogrammer.cigar_finder_android

import android.view.View

class SearchResultListItem(result: SearchResult) extends ListItem[SearchResult](result) with FindView {
  override def layoutId = R.layout.result_list_item

  override def convertView(view: View) = {
    val titleView = view.findView(TR.title)
    titleView.setLines(1)
    titleView.setText(result.storeName)

    val subtitle = view.findView(TR.subtitle)
    result.hasInformation match {
      case true => subtitle.setText("Last reported " + lastUpdated)
      case false => subtitle.setText("No information")
    }

    val icon = view.findView(TR.carried_marker)
    result.isCarried match {
      case true => icon.setVisibility(View.VISIBLE)
      case false => icon.setVisibility(View.GONE)
    }

    view
  }

  override def viewType = 1

  override def id = result.storeId

  def lastUpdated = new TimeToString(result.lastUpdated).toString
}
