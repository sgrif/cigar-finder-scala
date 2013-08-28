package com.seantheprogrammer.cigar_finder_android

import scala.io.Source
import scala.concurrent.future
import scala.concurrent.ExecutionContext.Implicits.global

import org.json.JSONArray

class CigarsLoader {
  def loadCigars = future {
    val content = Source.fromURL(cigarsUrl)
    val json = new JSONArray(content.mkString)
    0 until json.length map(json.getString(_))
  }

  private lazy val cigarsUrl = CigarFinder.baseUrl + "cigars.json"
}
