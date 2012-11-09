
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

  test("simple and") {
    val d =
      <range>
        <and>
          <range>1 -3</range>
          <range>2- 5 </range>
        </and>
      </range>

    val range = ReturnCodeRangeReader read d

    assert(!range.contains(1))
    assert(range.contains(2))
    assert(range.contains(3))
    assert(!range.contains(4))
    assert(!range.contains(5))
  }

  test("simple not with plain text") {
    val d =
      <range>
       <not>1</not>
      </range>

    val range = ReturnCodeRangeReader read d
     
    assert(range.contains(0))
    assert(!range.contains(1))
    assert(range.contains(2))

  }

  test("simple not with internal tag") {
    val d =
      <range>
       <not>
         <or>
           <aa>1</aa>
           <bb>3</bb>
         </or>
       </not>
      </range>

    val range = ReturnCodeRangeReader read d

    assert(range.contains(0))
    assert(!range.contains(1))
    assert(range.contains(2))
    assert(!range.contains(3))
    assert(range.contains(4))

  }

  test("simple not with multiple tags") {
  
    val d =
      <range>
       <not>
         <or>
           <aa>1</aa>
           <bb>3</bb>
         </or>
         <iKill>1</iKill>
       </not>
      </range>

    val thrown = intercept[ReadXmlException] {
      ReturnCodeRangeReader read d
    }
    assert(thrown.getMessage == "<not>-tag with multiple children")

  }



  test("too many children in base tag") {
    val d = 
      <range>
        <or></or>
        <or></or>
      </range>

    val thrown = intercept[ReadXmlException] {
      ReturnCodeRangeReader read d
    }
    assert(thrown.getMessage == "multiple elements in base tag")
    
  }

  test("children in unknown tag") {
    val d = 
      <range>
        <or>
          <ok>1-150</ok>
          <notOk>
            <intern>2</intern>
          </notOk>
        </or>
      </range>

    val thrown = intercept[ReadXmlException] {
      ReturnCodeRangeReader read d
    }
    assert(thrown.getMessage == "unknown tag <notOk> with children")
  }

}

