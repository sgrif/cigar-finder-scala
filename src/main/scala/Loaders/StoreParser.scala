package com.seantheprogrammer.cigar_finder_android

import org.json.{JSONArray, JSONObject}

class StoreParser(json: JSONObject) {
  def store = new Store(
    json.getInt("id"),
    json.getString("name"),
    json.getDouble("latitude"),
    json.getDouble("longitude"),
    json.getString("address"),
    json.getString("phone_number")
  )
}

class StoreListParser(json: String) {
  def stores = 0 until rawStores.length map buildStore

  lazy val rawStores = new JSONArray(json)

  def buildStore(index: Int) = {
    val rawStore = rawStores.getJSONObject(index)
    new StoreParser(rawStore).store
  }
}
