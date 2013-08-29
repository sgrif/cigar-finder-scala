package com.seantheprogrammer.cigar_finder_android

import android.os.{Parcelable, Parcel}

class SearchResults(results: IndexedSeq[SearchResult]) extends Parcelable with TypedParceling {
  def sorted = carried ++ notCarried ++ noInformation

  def carried = results.filter { _.isCarried }
  def notCarried = results.filter { result =>  result.hasInformation && !result.isCarried }
  def noInformation = results.filter { !_.hasInformation }

  def size = results.size
  def isEmpty = results.isEmpty

  def indexOf(result: SearchResult) = sorted.indexOf(result)
  def indexForId(id: Int) = sorted.indexWhere(_.store.id == id)

  def updateResultCarried(index: Int, carried: Boolean) = {
    val newUpdatedAt = SearchResult.nowString
    val newResult = sorted(index).copy(
      carried = Some(carried),
      updatedAt = newUpdatedAt
    )
    new ResultUpdater(newResult).execute
    new SearchResults(sorted.updated(index, newResult))
  }

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
