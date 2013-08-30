package com.seantheprogrammer.cigar_finder_android

import scala.io.Source

class MissingInformationLoader(id: String) {
  def result = {
    val json = Source.fromURL(url).mkString
    new SearchResultParser(json).result
  }

  lazy val url = CigarFinder.baseUrl + "cigar_stores/" + id + "/missing_information.json"
}
