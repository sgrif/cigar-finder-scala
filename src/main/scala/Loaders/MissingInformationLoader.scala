package com.seantheprogrammer.cigar_finder_android

class MissingInformationLoader(id: String) {
  def result: SearchResult = result(UrlSource)

  def result(source: UrlSource) = {
    val json = source.fromURL(url)
    new SearchResultParser(json).result
  }

  lazy val url = CigarFinder.baseUrl + "cigar_stores/" + id + "/missing_information.json"
}
