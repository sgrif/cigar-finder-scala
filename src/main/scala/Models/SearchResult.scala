package com.seantheprogrammer.cigar_finder_android

import android.os.{Parcelable, Parcel}
import java.text.SimpleDateFormat;
import java.util.{Date, TimeZone};

case class SearchResult(cigar: String, store: Store, carried: Option[Boolean], updatedAt: String)
extends Parcelable with TypedParceling {
  def isCarried = carried match {
    case Some(carried) => carried
    case None => false
  }

  lazy val lastUpdated = SearchResult.updatedFormat.parse(updatedAt)

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

  lazy val updatedFormat = {
    val format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
    format.setTimeZone(TimeZone.getTimeZone("UTC"))
    format
  }

  def nowString = updatedFormat.format(new Date)
}
