package com.seantheprogrammer.cigar_finder_android

import android.location.Location
import android.net.Uri
import scala.concurrent.future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.Source

class LetMeCigarFinderThatForYou(cigarName: String, location: Location) {
  def loadSearchResults = future {
    val content = Source.fromURL(apiUrl)
    val parser = new SearchResultListParser(content.mkString)
    new SearchResults(parser.results)
  }

  private lazy val apiUrl = {
    CigarFinder.baseUrl + "cigar_search_results.json?cigar=%s&latitude=%f&longitude=%f"
      .format(Uri.encode(cigarName), location.getLatitude, location.getLongitude)
  }
}
