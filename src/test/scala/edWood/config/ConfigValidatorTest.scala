package edWood.config

import edWood.reader.ReaderAdapter;
import edWood.data._

import at.axelGschaider.utils.Utils._

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
   
   ConfigValidator.testIds(List(jobConf("lala")))
   
 }

 test("multiple IDs - should work") {
   
   ConfigValidator.testIds(
     jobConfs( List( "1", "2", "3", "4", "5" ) )
   )

 }
 
 test("multiple IDs - should fail") {
   try {
     ConfigValidator.testIds(
       jobConfs( List( "1", "2", "3", "4", "5" ) )
     )

     assert(false)

   } catch {
     case _ => {} //TODO change to "expected"
   }
 }

  test("job counter") {
    ConfigValidator testGeneralConfig GeneralConfig("", 1, Nil, None)
  }
 
 def jobConfs(ids:List[String]) = ids.map(jobConf(_))
 
 def jobConf(id:String) =
   JobConfig(
       id,
       Command("lalala", Nil, Nil, None),
       dummyReader,
       Nil,
       Nil
     )

 val dummyReader = new ReaderAdapter() {
   
   override def getDescription() = "dummy"
   
   override def init(args:Array[String]) = {}
   
   override def readLife(input:LifeInput) = ???
   
   override def readPostRunSources(input:Array[Input]) = ???
   
   override def getData() = ???
   
   override def getDummyData() = ???
   
 }

}
