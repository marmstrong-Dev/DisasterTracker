import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import com.tools.Validator._

class ValidatorTests extends AnyFlatSpec with should.Matchers {
  "check_for_nulls()" should "return false when nulls are present" in {
    val testList = Array[String]("This", "Is", "A", "Test", "Array", null)
    assert(!check_for_null(testList))
  }

  "check_for_nulls()" should "return true when all fields are present" in {
    val testList = Array[String]("This", "Is", "A", "Test", "Array", "Full")
    assert(check_for_null(testList))
  }

  "check_email()" should "return false if missing @ sign" in {
    assert(!check_email("testaddresscom"))
  }
}
