package edWood.returnCodes


import edWood.exceptions.ReadXmlException

import org.scalatest.junit.JUnitSuite
import org.scalatest.prop.Checkers
import org.junit.runner.RunWith
import org.junit.Test
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalacheck.Arbitrary._
import org.scalacheck.Prop._


@RunWith(classOf[JUnitRunner])
class RangeDictionaryReaderTest extends FunSuite {

  test("empty") {
    val d = <ranges></ranges>
    val dict = RangeDictionaryReader read d
    assert(dict.size == 0)
  }

  test("single range") {
    val d = 
      <ranges>
        <theOne id="theId">1</theOne> 
      </ranges>
    val dict = RangeDictionaryReader read d
    assert(dict.size == 1)
    assert(dict("theId").contains(1))
  }

  test("two ranges") {
    val d =
      <ranges>
        <theOne id="the1">1</theOne> 
        <everythingElse id="theOthers">
          <not>1</not>
        </everythingElse>
      </ranges>
    val dict = RangeDictionaryReader read d
    assert(dict.size == 2)
    assert(dict("the1").contains(1))
    assert(dict("theOthers").contains(2))
  }

  test("overlapping") {
    val d =
      <ranges>
        <theOne id="repeat">1</theOne> 
        <everythingElse id="repeat">
          <not>1</not>
        </everythingElse>
      </ranges>
    val dict = RangeDictionaryReader read d
    assert(dict.size == 1)
    assert(dict("repeat").contains(2))
  }
}
