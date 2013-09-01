package com.seantheprogrammer.cigar_finder_android

import android.location.Location
import android.net.Uri
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

class LetMeCigarFinderThatForYou(cigarName: String, location: Location) {
  def loadSearchResults: Future[SearchResults] = loadSearchResults(UrlSource)

  def loadSearchResults(source: UrlSource) = future {
    val content = source.fromURL(apiUrl)
    val parser = new SearchResultListParser(content)
    new SearchResults(parser.results)
  }

  private lazy val apiUrl = {
    CigarFinder.baseUrl + "cigar_search_results.json?cigar=%s&latitude=%f&longitude=%f"
      .format(Uri.encode(cigarName), location.getLatitude, location.getLongitude)
  }
}
