package com.seantheprogrammer.cigar_finder_android

import android.view.View
import android.view.View.OnClickListener

class ViewWithOnClick(view: View) {
  def onClick(callback: => Unit) = {
    view.setOnClickListener(new OnClickListener {
      def onClick(v: View) = callback
    })
  }
}

trait OnClick {
  implicit def addOnClickToViews(view: View) = new ViewWithOnClick(view)
}
