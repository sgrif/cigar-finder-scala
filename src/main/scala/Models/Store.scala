package com.seantheprogrammer.cigar_finder_android

import android.os.{Parcel, Parcelable}

class Store(
  val id: Int,
  val name: String,
  val latitude: Double,
  val longitude: Double,
  val address: String,
  val phoneNumber: String
) extends Parcelable with TypedParceling {
  def location = new ConstructedLocation(latitude, longitude)

  override def describeContents = 0
  override def writeToParcel(parcel: Parcel, flags: Int) = {
    parcel.write(id)
    parcel.write(name)
    parcel.write(latitude)
    parcel.write(longitude)
    parcel.write(address)
    parcel.write(phoneNumber)
  }
}

object Store extends TypedParceling {
  val CREATOR = new Parcelable.Creator[Store] {
    override def createFromParcel(p: Parcel) = new Store(p.read, p.read, p.read, p.read, p.read, p.read)
    override def newArray(size: Int) = new Array[Store](size)
  }
}
