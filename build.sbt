// Include the Android plugin
androidDefaults

// Name of your app
name := "CigarFinder"

keyalias := "release"

// Version of your app
version := "1.1.0"

// Version number of your app
versionCode := 6

// Version of Scala
scalaVersion := "2.10.1"

// Version of the Android platform SDK
platformName := "android-17"

libraryDependencies += "com.android.support" % "support-v4" % "18.0.0"

libraryDependencies += aarlib("com.google.android.gms" % "play-services" % "3.1.36")

libraryDependencies += "commons-io" % "commons-io" % "2.4"

libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.1"

libraryDependencies += "com.loopj.android" % "image" % "1.0.0" from "http://cloud.github.com/downloads/loopj/android-smart-image-view/android-smart-image-view-1.0.0.jar"

libraryDependencies += "com.loopj.android" % "http" % "1.4.3" from "https://raw.github.com/loopj/android-async-http/master/releases/android-async-http-1.4.3.jar"

libraryDependencies += "org.ocpsoft.prettytime" % "prettytime" % "3.0.2.Final"

proguardOptions += "-keep class org.ocpsoft.**"

proguardOptions += "-keep class com.loopj.android.image.**"

proguardOptions += "-keep class com.loopj.android.http.**"

proguardOptions += "-keep class android.support.v4.app.FragmentManager"

proguardOptions += "-keep class android.support.v4.app.Fragment"

proguardOptions += "-keep class android.support.v4.app.Fragment$SavedState"

proguardOptions += "-keep class com.google.android.gms.location.Geofence"

proguardOptions += "-keep class com.google.android.gms.common.ConnectionResult"

proguardOptions += "-keep class scala.Function0"

proguardOptions += "-keep class scala.Function1"

proguardOptions += "-keep class scala.Option"

proguardOptions += "-keep class scala.collection.IndexedSeq"

proguardOptions += """
-keep class * extends java.util.ListResourceBundle {
  protected java.lang.Object[][] getContents();
}
"""
