package com.seantheprogrammer.cigar_finder_android

class CigarSearchResults(results: IndexedSeq[CigarSearchResult]) {
  def carried = results.filter { _.isCarried }
  def notCarried = results.filter { result =>  result.hasInformation && !result.isCarried }
  def noInformation = results.filter { !_.hasInformation }

  def size = results.size
}
