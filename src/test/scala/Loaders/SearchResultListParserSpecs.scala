import com.seantheprogrammer.cigar_finder_android.{SearchResult, SearchResultListParser, SearchResultParser, Store}
import org.scalatest.matchers.ShouldMatchers
import org.scalatest._

class SearchResultListParserSpecs extends FunSpec with ShouldMatchers {
  lazy val arrayJson = "[%s]".format(json)
  lazy val json = """
    {
      "cigar_store":{"id":69,"name":"Cigars On 6th","latitude":39.72571,"longitude":-104.978525,
      "address":"707 East 6th Avenue, Denver","phone_number":"(303) 830-8100"},
      "cigar":"Tatuaje 7th Reserva",
      "carried":true,"updated_at":"2013-04-14T02:42:31.931Z"
    }
    """

  it("parses an array of results") {
    val parser = new SearchResultListParser(arrayJson)

    parser.results should be (IndexedSeq(SearchResult(
      "Tatuaje 7th Reserva",
      Store(69, "Cigars On 6th", 39.72571, -104.978525, "707 East 6th Avenue, Denver", Some("(303) 830-8100")),
      Some(true),
      "2013-04-14T02:42:31.931Z"
    )))
  }

  it("can have null for carried") {
    val json = """ {"cigar_store":{"id":12592,"name":"Tobacco Leaf","latitude":39.711793,"longitude":-105.076136,
    "address":"7111 West Alameda Avenue, Lakewood","phone_number":"(303) 274-8720"},"cigar":"Illusione Mk Ultra",
    "carried":null,"updated_at":"2013-08-20T19:49:08.572Z"} """
    val parser = new SearchResultParser(json)

    parser.result.carried should be (None)
  }
}
