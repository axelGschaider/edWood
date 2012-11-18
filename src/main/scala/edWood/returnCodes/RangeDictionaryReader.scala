package edWood.returnCodes



import edWood.exceptions.ReadXmlException
import edWood.xmlHelpers._
import edWood.xmlHelpers.Implicits._
import edWood.xmlHelpers.Utils._

import at.axelGschaider.utils.Utils._
import at.axelGschaider.loggsNProperties.DefaultLogs


import scala.xml._


object RangeDictionaryReader extends DefaultLogs {
  
  def read(xml:Elem):Map[String, ReturnCodeRange] = {
    val elements = for {
      child <- children(xml)
      elem  = toDictionaryTuple(child)
      name  <- elem._1
    } yield {
      debug("found range '" + name + "'")
      (name, elem._2)
    }

    Map() ++ elements
  }

  private def toDictionaryTuple(xml:Node):(Option[String], ReturnCodeRange) = 
    (attribute(xml, "id"), ReturnCodeRangeReader read xml)

}


