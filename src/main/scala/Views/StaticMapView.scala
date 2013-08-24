package com.seantheprogrammer.cigar_finder_android

import android.widget.ImageView
import android.util.AttributeSet
import android.content.Context
import android.location.Location
import com.loopj.android.image.SmartImageView

class StaticMapView(context: Context, attrs: AttributeSet)
extends SmartImageView(context, attrs) {
  var location: Option[Location] = None

  override def onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) = location match {
    case Some(loc) => {
      val urlBuilder = new StringBuilder
      urlBuilder.append("http://maps.googleapis.com/maps/api/staticmap?sensor=true&size=")
        .append(w).append('x').append(h)
        .append("&markers=icon:http://s3.amazonaws.com/cigar-finder/map_marker.png%7C")
        .append(loc.getLatitude).append(',').append(loc.getLongitude)
        .append("&key=AIzaSyDWBzeBlZ7hC_NUDIP6WsmpDXXmhzgLdVk")
      setImageUrl(urlBuilder.toString)
    }
    case None => // Do nothing
  }
}
