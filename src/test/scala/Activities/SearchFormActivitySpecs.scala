package com.seantheprogrammer.cigar_finder_android

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Robolectric._
import org.junit.{Test, Before}
import org.robolectric.annotation.Config
import org.scalatest.junit.ShouldMatchersForJUnit
import org.robolectric.util.ActivityController

@RunWith(classOf[RobolectricTestRunner]) @Config(manifest="src/main/AndroidManifest.xml")
class SearchFormActivitySpecs extends ShouldMatchersForJUnit {
  var controller: ActivityController[SearchFormActivity] = _

  @Before def setUp {
    controller = buildActivity(classOf[SearchFormActivity])
    controller.create()
  }

  @Test def itSendsCigarAndLocationToSearchResults {
    searchFor("Tatuaje", "80031")

    val intent = shadow.getNextStartedActivity
    val startedClass = shadowOf(intent).getComponent.getClassName
    intent.getStringExtra("cigarName") should be ("Tatuaje")
    intent.getStringExtra("locationName") should be ("80031")
    startedClass should be (classOf[SearchResultsActivity].getName)
  }

  @Test def itDoesNotSubmitWhenCigarNameIsBlank {
    searchFor("", "Albuquerque")

    val intent = shadow.getNextStartedActivity
    intent should be (null)
  }

  @Test @Config(qualifiers="en-land")
  def itDoesNotShowTheLogoInLandscapeMode {
    val logo = activity.findView(TR.logo)

    logo should be (null)
  }

  @Test @Config(qualifiers="en-large-land")
  def itShowsLogoInLandscapeOnTablets {
    val logo = activity.findView(TR.logo)

    logo should not be (null)
  }

  def activity = controller.get
  def shadow = shadowOf(activity)
  def cigarInput = activity.findView(TR.inputCigarName)
  def locationInput = activity.findView(TR.inputLocationName)
  def submit = activity.findView(TR.searchSubmit)

  def searchFor(cigarName: String, locationName: String = "") {
    cigarInput.setText(cigarName)
    locationInput.setText(locationName)
    submit.performClick()
  }
}
