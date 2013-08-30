package com.seantheprogrammer.cigar_finder_android

import spray.json._
import DefaultJsonProtocol._
import StoreJsonProtocol._

object SearchResultJsonProtocol extends DefaultJsonProtocol {
  implicit object SearchResultJsonFormat extends RootJsonFormat[SearchResult] {
    override def write(r: SearchResult) = JsObject(
      "cigar" -> JsString(r.cigar),
      "cigar_store" -> r.store.toJson,
      "carried" -> r.carried.toJson,
      "updated_at" -> JsString(r.updatedAt)
    )

    override def read(value: JsValue) = {
      value.asJsObject.getFields("cigar", "cigar_store", "carried", "updated_at") match {
        case Seq(JsString(cigar), store, carried, JsString(updatedAt)) => {
          SearchResult(cigar, store.convertTo[Store], carried.convertTo[Option[Boolean]], updatedAt)
        }
        case _ => deserializationError("Expected Search Result")
      }
    }
  }
}

class SearchResultListParser(json: String) {
  import SearchResultJsonProtocol._
  def results = {
    json.asJson.convertTo[IndexedSeq[SearchResult]]
  }
}

class SearchResultParser(json: String) {
  import SearchResultJsonProtocol._
  def result = {
    json.asJson.convertTo[SearchResult]
  }
}

