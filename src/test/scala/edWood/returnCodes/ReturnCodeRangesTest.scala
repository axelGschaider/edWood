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
class FixedReturnCodesTests extends FunSuite {

  
  val singleVal = SingleVal(13)
  val range = Range(4,6)

  test("SingleVal") {
    assert(singleVal contains 13)
    assert(!singleVal.contains(14) )
  }

  test("Range") {

    assert(!range.contains(3))
    assert(range contains 4)
    assert(range contains 5)
    assert(range contains 6)
    assert(!range.contains(7))

  }

  test("Range Exception") {
    intercept[java.lang.Exception] {
      Range(2,1)
    }
  }

  test("Range Extreme Case") {
    val v = Range(3,3) //there should be no exception
    assert(v contains 3)
  }

  test("Negator") {
    val n1 = Negator(singleVal)
    assert(n1 contains 14)
    assert(!n1.contains(13) )

  }

  test("And") {
    val and = And(singleVal, range)
    assert(!and.contains(3))
    assert(!and.contains(5))
    assert(!and.contains(6))
    assert(!and.contains(13))
    assert(!and.contains(0))

    val and2 = And(Range(1,4), Range(3,6))
    assert(!and2.contains(1))
    assert(!and2.contains(2))
    assert(and2 contains 3)
    assert(and2 contains 4)
    assert(!and2.contains(5))
    assert(!and2.contains(6))
  }

  test("Or") {
    val or = Or( singleVal, range )

    assert(!or.contains(3))
    assert(or.contains(5))
    assert(!or.contains(7))
    assert(!or.contains(12))
    assert(or.contains(13))
    assert(!or.contains(14))
  }

  test("AlwaysFail") {
    assert(!AlwaysFail.contains(1))
    assert(!AlwaysFail.contains(2))
    assert(!AlwaysFail.contains(287691))
    assert(!AlwaysFail.contains(5))
  }

  test("AlwaysSuccess") {
    assert(AlwaysSuccess contains 1)
    assert(AlwaysSuccess contains 2)
    assert(AlwaysSuccess contains 3)
    assert(AlwaysSuccess contains 101010101)
    assert(AlwaysSuccess contains -1)
  }

}

class MySuite extends JUnitSuite with Checkers {
  @Test
  def singleVal() {
    forAll((a: Int, b: Int) => {
      val v = SingleVal(a)

      if(b != a) !v.contains(b)
      else v.contains(b)

    })
  }

  @Test
  def range() {
    forAll { (l:Int, u:Int, x:Int) => 
      (l <= u) ==> {
        val v = Range(l,u)
        val tested = v contains x

        if(x >= l && x <= u) tested
        else !tested

        false
      }  
    }
  }

}


