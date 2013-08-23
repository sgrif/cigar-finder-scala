package com.seantheprogrammer.cigar_finder_android

import android.os.{Parcelable, Parcel}

class SearchResult(val cigar: String, val store: Store, val carried: Option[Boolean], updatedAt: String)
extends Parcelable with TypedParceling {
  def isCarried = carried match {
    case Some(carried) => carried
    case None => false
  }

  def hasInformation = !carried.isEmpty

  def storeName = store.name
  def storeId = store.id

  override def describeContents = 0
  override def writeToParcel(parcel: Parcel, flags: Int) = {
    parcel.write(cigar)
    parcel.write(store)
    parcel.write(carried)
    parcel.write(updatedAt)
  }
}

object SearchResult extends TypedParceling {
  val CREATOR = new Parcelable.Creator[SearchResult] {
    override def createFromParcel(parcel: Parcel) = new SearchResult(parcel.read, parcel.read, parcel.read, parcel.read)
    override def newArray(size: Int) = new Array[SearchResult](size)
  }
}
