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
class SuccessMappingTest extends FunSuite {

  val just0 = SingleVal(0)
  val just3 = SingleVal(3)
  val just5 = SingleVal(5)
  val range4to8 = Range(4,8)
    
  test("Default Success") {
    assert(DefaultSuccess success 0)
    assert(!DefaultSuccess.success(1))
  }

  test("Full Mapping") {
    val full = FullMapping(List(just3, just5), List(range4to8))
    assert(full success 0)
    assert(full success 3)
    assert(!full.success(4))
    assert(!full.success(5))
    assert(!full.success(8))
  }

  test("just errors and default override") {
    val er = JustErrors(List(just0))
    assert(!er.success(1))
    assert(!er.success(0))
  }

  test("just success") {
    val suc = JustSuccesses(List(just3))
    assert(suc success 0)
    assert(suc success 3)
    assert(!suc.success(1))
    assert(!suc.success(2))
    assert(!suc.success(4))
  }


}

