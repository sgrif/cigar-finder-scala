package com.seantheprogrammer.cigar_finder_android

import scala.concurrent.Await
import scala.concurrent.duration._
import org.scalatest._
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._

class CigarsLoaderSpecs extends FunSpec with ShouldMatchers with MockitoSugar {
  it("loads cigars from the internet") {
    val result = """["Tatuaje", "Liga Privada", "Illusionne"]"""
    val mockSource = mock[UrlSource]
    when(mockSource.fromURL(CigarFinder.baseUrl + "cigars.json")).thenReturn(result)

    val futureCigars = new CigarsLoader().loadCigars(mockSource)
    val cigars = Await.result(futureCigars, 5.seconds)

    cigars should be (IndexedSeq("Tatuaje", "Liga Privada", "Illusionne"))
  }
}
