package edWood.xmlHelpers


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
class XmlHelpersTest extends FunSuite {

  test("empty attribute") {
    val d = <a></a>
    val id = Utils.attribute(d, "id")
    assert(id.isEmpty)
  }

  test("with attribute") {
    val d = <a id="idid" someOther="lalala"></a>
    val id = Utils.attribute(d, "id")
    assert(id.isDefined)
    assert(id.get == "idid")
  }

}
