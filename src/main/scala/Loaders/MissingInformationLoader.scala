package com.seantheprogrammer.cigar_finder_android

import java.net.URL
import org.apache.commons.io.IOUtils
import org.json.JSONObject

class MissingInformationLoader(id: String) {
  def result = {
    val apiResponse = IOUtils.toString(url, "UTF-8")
    val json = new JSONObject(apiResponse)
    new SearchResultParser(json).result
  }

  lazy val url = new URL(CigarFinder.baseUrl + "cigar_stores/" + id + "/missing_information.json")
}
