package com.seantheprogrammer.cigar_finder_android

import org.json.JSONObject
import scala.io.Source

class MissingInformationLoader(id: String) {
  def result = {
    val apiResponse = Source.fromURL(url).mkString
    val json = new JSONObject(apiResponse)
    new SearchResultParser(json).result
  }

  lazy val url = CigarFinder.baseUrl + "cigar_stores/" + id + "/missing_information.json"
}
