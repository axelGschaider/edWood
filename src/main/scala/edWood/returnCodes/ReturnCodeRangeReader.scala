package edWood.returnCodes

import edWood.exceptions.ReadXmlException
import edWood.xmlHelpers._
import edWood.xmlHelpers.Implicits._

import at.axelGschaider.utils.Utils._

import scala.xml._

object ReturnCodeRangeReader {
  
  implicit def int2ReturnCodeRange(i:Int):ReturnCodeRange = SingleVal(i)

  def read(xml:Elem):ReturnCodeRange = {
    val elements = children(xml)
    
    elements match {
      case Nil => parseText( xml.text )
      case _   => AlwaysFail
    }

  }

  private def parseText(text:String):ReturnCodeRange = {

    if(text == null || text.trim.length == 0) AlwaysFail
    else if( (text indexOf "-") != -1 ) (text split "-").toList match {
      case (a :: b :: Nil)  => Range(a.trim.toInt, b.trim.toInt)
      case _                => throw new ReadXmlException("can't parse '" + text + "' to list" )
    }
    else if( (text indexOf ",") != -1 ) readSplitList( (text split ",").toList )
    else text.trim.toInt

  }

  private def readSplitList(list:List[String]):ReturnCodeRange = list match {
    case Nil              => throw new ReadXmlException("empty list")
    case (a :: Nil)       => throw new ReadXmlException("not enough elements in list: [" + a + "]" )
    case (a :: b :: Nil)  => Or(a.trim.toInt, b.trim.toInt)
    case (a :: as)        => Or(a.trim.toInt, readSplitList(as))
  }

  private def children(xml:Elem):List[Node] = (xml \ "_").toList

}

