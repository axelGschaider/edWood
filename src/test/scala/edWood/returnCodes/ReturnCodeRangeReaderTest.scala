
package edWood.returnCodes

import org.scalatest.junit.JUnitSuite
import org.scalatest.prop.Checkers
import org.junit.runner.RunWith
import org.junit.Test
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalacheck.Arbitrary._
import org.scalacheck.Prop._


@RunWith(classOf[JUnitRunner])
class ReturnCodeReaderTest extends FunSuite {
  
  test("single val") {
    val d = <range>1</range>
    val range = ReturnCodeRangeReader read d
    assert(range.contains(1))
    assert(!range.contains(2))
    assert(!range.contains(0))
  }

  test("simple range") {
    val d = <range>3-5</range>
    val range = ReturnCodeRangeReader read d
    assert(!range.contains(2))
    assert(range.contains(3))
    assert(range.contains(4))
    assert(range.contains(5))
    assert(!range.contains(6))
  }

  test("simple list") {
    val d = <range>2,4,7</range>
    val range = ReturnCodeRangeReader read d
    //val expected =
    //  Or(
    //    SingleVal(2),
    //    Or(
    //      SingleVal(4),
    //      SingleVal(7)
    //      )
    //    )

    assert(!range.contains(1))
    assert(range.contains(2))
    assert(!range.contains(3))
    assert(range.contains(4))
    assert(!range.contains(5))
    assert(!range.contains(6))
    assert(range.contains(7))
    assert(!range.contains(8))
  }

  test("empty text") {
    val d = <range></range>
    val range = ReturnCodeRangeReader read d
    
    assert(!range.contains(0))
    assert(!range.contains(1))
    assert(!range.contains(2))
    assert(!range.contains(3))
    assert(!range.contains(4))
  }

  test("simple or") {
    val d = 
      <range>
        <or>
          <range>2,4</range>
          <range>6-8</range>
        </or>
      </range>

    val range = ReturnCodeRangeReader read d

    println(range)

    assert(!range.contains(1))
    assert(range.contains(2))
    assert(!range.contains(3))
    assert(range.contains(4))
    assert(!range.contains(5))
    assert(range.contains(6))
    assert(range.contains(7))
    assert(range.contains(8))
    assert(!range.contains(9))

  }

}

