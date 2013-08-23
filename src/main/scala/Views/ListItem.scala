package com.seantheprogrammer.cigar_finder_android

import android.view.View

abstract class ListItem[T](val data: T) {
  def convertView(view: View): View
  def layoutId: Int
  def viewType: Int
  def isEnabled = true
  def id = -1
}
