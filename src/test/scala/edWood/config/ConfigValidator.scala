package edWood.config


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite

@RunWith(classOf[JUnitRunner])
class ConfigValidatorTest extends FunSuite {

 test("test IDs empty") {
   ConfigValidator.testIds(Nil)
   //no exception? Good.
 }

 test("single ID") {
 
 }

}
