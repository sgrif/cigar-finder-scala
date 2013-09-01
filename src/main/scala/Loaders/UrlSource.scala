package com.seantheprogrammer.cigar_finder_android

import scala.io.Source

trait UrlSource {
  def fromURL(url: String): String
}

object UrlSource extends UrlSource {
  override def fromURL(url: String) = {
    val source = Source.fromURL(url)
    try { source.mkString }
    finally { source.close() }
  }
}
