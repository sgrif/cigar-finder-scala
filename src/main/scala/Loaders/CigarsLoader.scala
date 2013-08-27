package com.seantheprogrammer.cigar_finder_android

import scala.io.Source
import scala.concurrent.future
import scala.concurrent.ExecutionContext.Implicits.global

import org.json.JSONArray

class CigarsLoader {
  def loadCigars(callback: IndexedSeq[String] => Unit) = {
    val f = future { Source.fromURL(cigarsUrl) }
    for (content <- f) {
      val json = new JSONArray(content.mkString)
      callback(0 until json.length map(json.getString(_)))
    }
  }

  private lazy val cigarsUrl = CigarFinder.baseUrl + "cigars.json"
}
