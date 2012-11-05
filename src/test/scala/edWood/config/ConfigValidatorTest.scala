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
 
 
 def jobConfs(ids:List[String]) = ids.map(jobConf(_))
 
 def jobConf(id:String) {
   JobConfig(
       "id",
       Command("lalala", Nil, Nil, None),
       dummyReader,
       Nil,
       Nil
     )
 }
 
 val dummyReader = new ReaderAdapter() {
   
   override def getDescription() = "dummy"
   
   override def init(args:Array[String]) = {}
   
   override def readLife(input:LifeInput) = ???
   
   override def readPostRunSources(input:Array[Input]) = ???
   
   override def getData() = ???
   
   override def getDummyData() = ???
   
 }

}
