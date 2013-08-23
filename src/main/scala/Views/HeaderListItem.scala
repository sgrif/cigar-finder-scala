package com.seantheprogrammer.cigar_finder_android

import android.view.View

class HeaderListItem(title: String) extends ListItem[String](title) with FindView {
  override def layoutId = R.layout.header_list_item

  override def convertView(view: View) = {
    view.findView(TR.title).setText(title)
    view
  }

  override def viewType = 0

  override def isEnabled = false
}
