package com.seantheprogrammer.cigar_finder_android

import scala.io.{BufferedSource, Source}
import scala.concurrent.{Future, future}
import scala.concurrent.ExecutionContext.Implicits.global

import spray.json._
import DefaultJsonProtocol._

class CigarsLoader {
  def loadCigars: Future[IndexedSeq[String]] = loadCigars(UrlSource)

  def loadCigars(source: UrlSource) = future {
    val content = source.fromURL(cigarsUrl)
    content.asJson.convertTo[IndexedSeq[String]]
  }

  private lazy val cigarsUrl = CigarFinder.baseUrl + "cigars.json"
}

trait UrlSource {
  def fromURL(url: String): String
}

object UrlSource extends UrlSource {
  override def fromURL(url: String) = Source.fromURL(url).mkString
}
