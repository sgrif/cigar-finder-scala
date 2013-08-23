package com.seantheprogrammer.cigar_finder_android

import android.view.View

class ViewWithFindView(view: View) extends TypedViewHolder {
  override def findViewById(id: Int) = view.findViewById(id)
}

trait FindView {
  implicit def addFindViewToViews(view: View) = new ViewWithFindView(view)
}
