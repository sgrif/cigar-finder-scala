// Include the Android plugin
androidDefaults

name := "CigarFinder"

keyalias := "release"

version := "1.1.1"

versionCode := 10

scalaVersion := "2.10.2"

scalacOptions ++= Seq("-feature", "-deprecation")

platformName := "android-17"

resolvers += "spray" at "http://repo.spray.io/"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.1" % "test"

libraryDependencies += "org.mockito" % "mockito-all" % "1.9.5" % "test"

libraryDependencies += "junit" % "junit" % "4.11" % "test"

libraryDependencies += "org.robolectric" % "robolectric" % "2.1.1" % "test"

libraryDependencies += "com.android.support" % "support-v4" % "18.0.0"

libraryDependencies += aarlib("com.google.android.gms" % "play-services" % "3.2.25")

libraryDependencies += "org.scaloid" %% "scaloid" % "2.3-8"

libraryDependencies += "io.spray" %%  "spray-json" % "1.2.5"

libraryDependencies += "com.loopj.android" % "image" % "1.0.0" from "http://cloud.github.com/downloads/loopj/android-smart-image-view/android-smart-image-view-1.0.0.jar"

libraryDependencies += "com.loopj.android" % "http" % "1.4.3" from "https://raw.github.com/loopj/android-async-http/master/releases/android-async-http-1.4.3.jar"

libraryDependencies += "org.ocpsoft.prettytime" % "prettytime" % "3.0.2.Final"

proguardOptions += "-keep class org.ocpsoft.**"

proguardOptions += "-keep class com.loopj.android.image.**"

proguardOptions += "-keep class com.loopj.android.http.**"

proguardOptions += "-keep class * extends java.util.ListResourceBundle { protected java.lang.Object[][] getContents(); }"
