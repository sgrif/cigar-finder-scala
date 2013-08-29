package com.seantheprogrammer.cigar_finder_android

import android.content.{Context, SharedPreferences}
import org.scaloid.common._
import scala.collection.JavaConversions._

class Prefs(preferences: SharedPreferences) extends Preferences(preferences) {
  type SS = Set[String]

  override def updateDynamic(name: String)(value: Any) {
    value match {
      case v: SS => preferences.edit.putStringSet(name, v).commit()
      case _ => super.updateDynamic(name)(value)
    }
  }

  override def applyDynamic[T](name: String)(defaultVal: T): T = defaultVal match {
    case v: SS => preferences.getStringSet(name, v).toSet.asInstanceOf[T]
    case _ => super.applyDynamic[T](name)(defaultVal)
  }

  def remove(name: String): Unit = preferences.edit.remove(name).commit()
}

object Prefs {
  def apply()(implicit ctx: Context) = new Prefs(defaultSharedPreferences)
}
