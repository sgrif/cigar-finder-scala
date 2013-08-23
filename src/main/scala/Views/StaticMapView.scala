package com.seantheprogrammer.cigar_finder_android

import android.widget.ImageView
import android.util.AttributeSet
import android.content.Context
import android.location.Location

class StaticMapView(context: Context, attrs: AttributeSet) extends ImageView(context, attrs) {
  var location: Option[Location] = None
}
