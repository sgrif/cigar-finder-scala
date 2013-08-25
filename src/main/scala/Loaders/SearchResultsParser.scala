package com.seantheprogrammer.cigar_finder_android

import org.json.{JSONObject, JSONArray}

class SearchResultsParser(json: String) {
  def results = 0 until parsedResults.length map buildResult

  lazy val parsedResults = new JSONArray(json)

  def buildResult(index: Int) = {
    val rawResult = parsedResults.getJSONObject(index)
    new SearchResultParser(rawResult).result
  }
}

class SearchResultParser(json: JSONObject) {
  def result = {
    new SearchResult(
      json.getString("cigar"),
      buildStore(json.getJSONObject("cigar_store")),
      parseBoolean(json, "carried"),
      json.getString("updated_at")
    )
  }

  def buildStore(json: JSONObject) = new StoreParser(json).store

  def parseBoolean(json: JSONObject, key: String): Option[Boolean] = json.isNull(key) match {
    case true => None
    case false => Some(json.getBoolean(key))
  }
}
