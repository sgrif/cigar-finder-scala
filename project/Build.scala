import sbt._

import Keys._
import AndroidKeys._

object General {
  val settings = Defaults.defaultSettings ++ Seq (
    name := "Cigar Finder",
    version := "0.1",
    versionCode := 0,
    scalaVersion := "2.9.2",
    platformName in Android := "android-17",
    javacOptions ++= Seq("-encoding", "UTF-8", "-source", "1.6", "-target", "1.6")
  )

  val proguardSettings = Seq (
    useProguard in Android := true
  )

  lazy val loopjImageUrl = "http://cloud.github.com/downloads/loopj/android-smart-image-view/android-smart-image-view-1.0.0.jar"
  lazy val loopjHttpUrl = "https://raw.github.com/loopj/android-async-http/master/releases/android-async-http-1.4.3.jar"

  lazy val fullAndroidSettings =
    General.settings ++
    AndroidPath.settings ++
    AndroidProject.androidSettings ++
    TypedResources.settings ++
    proguardSettings ++
    AndroidManifestGenerator.settings ++
    AndroidMarketPublish.settings ++ Seq (
      keyalias in Android := "change-me",
      proguardOption in Android := "@project/proguard.cfg",
      libraryDependencies += "org.scalatest" %% "scalatest" % "1.8" % "test",
      // libraryDependencies += "com.android.support" % "support-v4" % "18.0.0",
      // libraryDependencies += "com.google.android.gms" % "play-services" % "3.2.25" artifacts(Artifact("play-services", "aar", "aar")),
      libraryDependencies += "commons-io" % "commons-io" % "2.4",
      libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.1",
      libraryDependencies += "com.loopj.android" % "image" % "1.0.0" from loopjImageUrl,
      libraryDependencies += "com.loopj.android" % "http" % "1.4.3" from loopjHttpUrl,
      libraryDependencies += "org.ocpsoft.prettytime" % "prettytime" % "3.0.2.Final"
    )
}

object AndroidBuild extends Build {
  lazy val main = Project (
    "CigarFinder",
    file("."),
    settings = General.fullAndroidSettings
  )
}
