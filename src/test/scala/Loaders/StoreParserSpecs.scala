import com.seantheprogrammer.cigar_finder_android.{StoreListParser, StoreParser, Store}
import org.scalatest.matchers.ShouldMatchers
import org.scalatest._

class StoreParserSpecs extends FunSpec with ShouldMatchers {
  it("parses a list of stores") {
    val json = """[{
      "id": 1, "name": "Jim's Cigars", "latitude": 1.234, "longitude": -1.234,
      "address": "123 Main St", "phone_number": "(505) 555-1234"
    }]"""
    val parser = new StoreListParser(json)

    parser.stores should be (IndexedSeq(
      Store(1, "Jim's Cigars", 1.234, -1.234, "123 Main St", Some("(505) 555-1234"))
    ))
  }

  it("allows null for phone number") {
    val json = """{
      "id": 1, "name": "Jim's Cigars", "latitude": 1.234, "longitude": -1.234,
      "address": "123 Main St", "phone_number": null
    }"""
    val parser = new StoreParser(json)

    parser.store.phoneNumber should be (None)
  }
}
