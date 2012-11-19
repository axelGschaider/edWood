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
class SuccessMappingReaderTest extends FunSuite {

  test("just empty") {
    val x = <a></a>
    val m= SuccessMappingReader read x
    assert(m success 0)
    assert(!m.success(1))
    assert(!m.success(2))
  }

  test("simple range success (naming is ignored)") {
    val x = 
      <a>
        <success>4-8</success>
        <naming label="never there">9</naming>
      </a>
    val m = SuccessMappingReader read x
    assert(m success 0)
    assert(m success 4)
    assert(m success 6)
    assert(m success 8)
  }

  test("mixed") {
    val x = 
      <a>
        <success>4-8</success>
        <failure>3,7</failure>
        <success>
          <and>
            <d>1</d>
            <e>9</e>
          </and>
        </success>
      </a>
    val m = SuccessMappingReader read x
    //we made it so far. so it's ok
  }

  test("single reference (and tag val is ignored)") {
    val x = 
      <a>
        <success ref="base">5</success>
      </a>
    val map = Map("base" -> Range(2,4))

    val m = SuccessMappingReader.read(x, map)
    assert(m success 0)
    assert(m success 2)
    assert(m success 4)
    assert(!m.success(5))
  }

  test("unknow tag") {
    val x = 
      <a>
        <failure>2</failure>
        <success>3</success>
        <naming>4</naming>
        <error>5</error>
      </a>

    val thrown = intercept[ReadXmlException] {
      val m = SuccessMappingReader.read(x)
    }
    assert(thrown.getMessage == "unknown tag <error> in mapping definition")
  }

  test("unresolvable reference") {
    val x = 
      <a>
        <success ref="base">5</success>
      </a>
    val map = Map("noBaseHere" -> Range(2,4))

    val thrown = intercept[ReadXmlException] {
      val m = SuccessMappingReader.read(x, map)
    }
    assert(thrown.getMessage == "could not resolve ranges reference 'base'")
  }

}

