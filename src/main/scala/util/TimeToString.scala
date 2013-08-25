package com.seantheprogrammer.cigar_finder_android

import java.util.Date
import org.ocpsoft.prettytime.PrettyTime

class TimeToString(time: Date) {
  def numberPart = if (makeString.matches("^\\d+ .*$")) {
    makeString.replaceAll(regex, "$1")
  } else {
    ""
  }
  def unitPart = makeString.replaceAll(regex, "$2")
  override def toString = makeString

  val regex = "^(\\d+) (.*)$"
  private lazy val makeString = new PrettyTime().format(time)
}
