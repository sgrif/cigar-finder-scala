package com.seantheprogrammer.cigar_finder_android

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.{BitmapDrawable, ColorDrawable, Drawable, TransitionDrawable}
import android.util.AttributeSet
import android.widget.ImageView

trait FadeInImage extends ImageView {
  override def setImageBitmap(image: Bitmap) = {
    val oldDrawable: Drawable = getDrawable match {
      case null => new ColorDrawable(android.R.color.transparent)
      case d => d
    }
    val newDrawable: Drawable = new BitmapDrawable(getContext.getResources, image)
    val d = new TransitionDrawable(Array(oldDrawable, newDrawable))

    setImageDrawable(d)
    d.startTransition(300)
  }
}
