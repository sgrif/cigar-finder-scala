package com.seantheprogrammer.cigar_finder_android

import scala.io.Source
import scala.concurrent.future
import scala.concurrent.ExecutionContext.Implicits.global

import spray.json._
import DefaultJsonProtocol._

class CigarsLoader {
  def loadCigars = future {
    val content = Source.fromURL(cigarsUrl)
    content.mkString.asJson.convertTo[IndexedSeq[String]]
  }

  private lazy val cigarsUrl = CigarFinder.baseUrl + "cigars.json"
}
