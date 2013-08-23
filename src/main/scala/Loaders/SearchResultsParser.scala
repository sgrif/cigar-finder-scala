package com.seantheprogrammer.cigar_finder_android

import org.json.{JSONObject, JSONArray}

class SearchResultsParser(json: String) {
  def results = 0 until parsedResults.length map buildResult

  lazy val parsedResults = new JSONArray(json)

  def buildResult(index: Int) = {
    val rawResult = parsedResults.getJSONObject(index)
    new SearchResult(
      rawResult.getString("cigar"),
      buildStore(rawResult.getJSONObject("cigar_store")),
      parseBoolean(rawResult, "carried"),
      rawResult.getString("updated_at")
    )
  }

  def buildStore(json: JSONObject) = {
    new Store(
      json.getInt("id"),
      json.getString("name"),
      json.getDouble("latitude"),
      json.getDouble("longitude"),
      json.getString("address"),
      json.getString("phone_number")
    )
  }

  def parseBoolean(json: JSONObject, key: String): Option[Boolean] = json.isNull(key) match {
    case true => None
    case false => Some(json.getBoolean(key))
  }
}
