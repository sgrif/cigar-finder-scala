package com.seantheprogrammer.cigar_finder_android

import android.location.Location
import android.net.Uri
import java.net.URL
import org.apache.commons.io.IOUtils

class CigarSearchResultsDownloader(cigarName: String, location: Location) {
  def json = IOUtils.toString(apiUrl, "UTF-8")
  def apiUrl = new URL(apiUrlString)
  def apiUrlString = {
    "http://cigar-finder.com/cigar_search_results.json?cigar=%s&latitude=%f&longitude=%f"
      .format(Uri.encode(cigarName), location.getLatitude, location.getLongitude)
  }
}
