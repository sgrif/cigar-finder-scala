package com.seantheprogrammer.cigar_finder_android.tests

import com.seantheprogrammer.cigar_finder_android.SearchResultsParser
import org.scalatest.matchers.ShouldMatchers
import org.scalatest._

class SearchResultsParserSpecs extends Spec with ShouldMatchers {
  describe("results") {
    it("parses an array of results") {
      val json = """
      [{"cigar_store":{"id":69,"name":"Cigars On 6th","latitude":39.72571,"longitude":-104.978525,
        "address":"707 East 6th Avenue, Denver","phone_number":"(303) 830-8100"},
        "cigar":"Tatuaje 7th Reserva",
        "carried":true,"updated_at":"2013-04-14T02:42:31.931Z"}]
      """

      val parser = new SearchResultsParser(json)
      val result = parser.results(0)
      val store = result.store

      result.cigar should be ("Tatuaje 7th Reserva")
      result.carried should be (Some(true))
      store.id should be (69)
      store.name should be ("Cigars On 6th")
      store.latitude should be (39.72571)
      store.longitude should be (-104.978525)
      store.address should be ("707 East 6th Avenue, Denver")
      store.phoneNumber should be ("(303) 830-8100")
    }
  }
}
