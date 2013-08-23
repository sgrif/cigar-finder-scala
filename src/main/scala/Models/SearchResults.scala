package com.seantheprogrammer.cigar_finder_android

import android.os.{Parcelable, Parcel}

class SearchResults(results: IndexedSeq[SearchResult]) extends Parcelable with TypedParceling {
  def sorted = carried ++ notCarried ++ noInformation

  def carried = results.filter { _.isCarried }
  def notCarried = results.filter { result =>  result.hasInformation && !result.isCarried }
  def noInformation = results.filter { !_.hasInformation }

  def size = results.size

  override def describeContents = 0
  override def writeToParcel(out: Parcel, flags: Int) = {
    out.write(results)
  }
}

object SearchResults extends TypedParceling {
  val CREATOR = new Parcelable.Creator[SearchResults] {
    override def createFromParcel(parcel: Parcel) = new SearchResults(parcel.read)
    override def newArray(size: Int) = new Array[SearchResults](size)
  }
}
