package com.seantheprogrammer.cigar_finder_android

import spray.json._
import DefaultJsonProtocol._

object StoreJsonProtocol extends DefaultJsonProtocol {
  implicit object StoreJsonFormat extends RootJsonFormat[Store] {
    override def write(s: Store) = JsObject(
      "id" -> JsNumber(s.id),
      "name" -> JsString(s.name),
      "latitude" -> JsNumber(s.latitude),
      "longitude" -> JsNumber(s.longitude),
      "address" -> JsString(s.address),
      "phoneNumber" -> s.phoneNumber.toJson
    )

    override def read(value: JsValue) = {
      value.asJsObject.getFields("id", "name", "latitude", "longitude", "address", "phone_number") match {
        case Seq(JsNumber(id), JsString(name), JsNumber(latitude), JsNumber(longitude), JsString(address), phoneNumber) => {
          Store(id.toInt, name, latitude.toDouble, longitude.toDouble, address, phoneNumber.convertTo[Option[String]])
        }
        case vals => println(vals); deserializationError("Error deserializing Stores")
      }
    }
  }
}

class StoreListParser(json: String) {
  import StoreJsonProtocol._
  def stores = json.asJson.convertTo[IndexedSeq[Store]]
}

class StoreParser(json: String) {
  import StoreJsonProtocol._
  def store = json.asJson.convertTo[Store]
}
