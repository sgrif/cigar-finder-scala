// Include the Android plugin
androidDefaults

name := "CigarFinder"

keyalias := "release"

version := "1.1.0"

versionCode := 6

scalaVersion := "2.10.2"

scalacOptions ++= Seq("-feature", "-deprecation")

platformName := "android-17"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.1" % "test"

libraryDependencies += "com.android.support" % "support-v4" % "18.0.0"

libraryDependencies += aarlib("com.google.android.gms" % "play-services" % "3.1.36")

libraryDependencies += "org.scaloid" %% "scaloid" % "2.3-8"

libraryDependencies += "com.loopj.android" % "image" % "1.0.0" from "http://cloud.github.com/downloads/loopj/android-smart-image-view/android-smart-image-view-1.0.0.jar"

libraryDependencies += "com.loopj.android" % "http" % "1.4.3" from "https://raw.github.com/loopj/android-async-http/master/releases/android-async-http-1.4.3.jar"

libraryDependencies += "org.ocpsoft.prettytime" % "prettytime" % "3.0.2.Final"

proguardOptions += "-keep class org.ocpsoft.**"

proguardOptions += "-keep class com.loopj.android.image.**"

proguardOptions += "-keep class com.loopj.android.http.**"

proguardOptions += """
-keep class * extends java.util.ListResourceBundle {
  protected java.lang.Object[][] getContents();
}
"""
