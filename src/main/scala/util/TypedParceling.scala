package com.seantheprogrammer.cigar_finder_android

import android.os.{Parcel, Parcelable}
import scala.collection.JavaConversions._

trait TypedParceling {
  implicit def addListParcelingToParcel(parcel: Parcel) = new ParcelWithTypedParceling(parcel)
}

class ParcelWithTypedParceling(parcel: Parcel) {
  def read[T]: T = {
    parcel.readValue(getClass.getClassLoader) match {
      case raw: java.util.List[_] => raw.toIndexedSeq.asInstanceOf[T]
      case raw: Any => raw.asInstanceOf[T]
    }
  }

  def write(value: Any) = {
    parcel.writeValue(value)
  }

  def write[T <: Parcelable](value: IndexedSeq[T]) = {
    val l: java.util.List[T] = value.toList
    parcel.writeValue(l)
  }
}
