package com.seantheprogrammer.cigar_finder_android

class CigarSearchResult(val cigar: String, val store: CigarStore, val carried: Option[Boolean], updatedAt: String) {
  def isCarried = carried match {
    case Some(carried) => carried
    case None => false
  }

  def hasInformation = !carried.isEmpty
}
