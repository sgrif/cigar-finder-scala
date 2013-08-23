package com.seantheprogrammer.cigar_finder_android

class SearchResult(val cigar: String, val store: Store, val carried: Option[Boolean], updatedAt: String) {
  def isCarried = carried match {
    case Some(carried) => carried
    case None => false
  }

  def hasInformation = !carried.isEmpty

  def storeName = store.name
  def storeId = store.id
}
