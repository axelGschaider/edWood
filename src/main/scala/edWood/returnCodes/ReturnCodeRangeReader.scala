package edWood.returnCodes

import edWood.exceptions.ReadXmlException
import edWood.xmlHelpers._
import edWood.xmlHelpers.Implicits._
import edWood.xmlHelpers.Utils._

import at.axelGschaider.utils.Utils._
import at.axelGschaider.loggsNProperties.DefaultLogs


import scala.xml._

object ReturnCodeRangeReader extends DefaultLogs {
  
  implicit def int2ReturnCodeRange(i:Int):ReturnCodeRange = SingleVal(i)
  implicit def node2ReturnCodeRange(xml:Node):ReturnCodeRange = handleTag(xml)

  def read(xml:Node):ReturnCodeRange = children(xml) match {
      case Nil      => parseText( xml )
      case a :: Nil => handleTag(a)
      case _        => throw new ReadXmlException("multiple elements in base tag")
    }



  private def handleTag(xml:Node):ReturnCodeRange = {
    val kids = children(xml)
    
    xml.label.trim match {
      case "and"  => and(kids)
      case "or"   => or(kids)
      case "not"  => not(xml)
      case a      => if(kids.length == 0) parseText( xml )
                     else throw new ReadXmlException("unknown tag <" + a + "> with children")
    }
  }

  private def not(xml:Node):ReturnCodeRange = children(xml) match {
    case Nil      => Not( parseText(xml) )
    case a :: Nil => Not( a )
    case _        => throw new ReadXmlException("<not>-tag with multiple children")
  }

  private def or(nodes:List[Node]):ReturnCodeRange = nodes match {
    case Nil            => { warn("empty <or>-tag"); AlwaysFail }
    case a :: Nil       => { warn("<or>-tag with single element"); a}
    case a :: b :: Nil  => Or(a, b)
    case a :: as        => Or(a, or(as) )
  }

  private def and(nodes:List[Node]):ReturnCodeRange = nodes match {
    case Nil            => { warn("empty <and>-tag"); AlwaysFail }
    case a :: Nil       => { warn("<and>-tag with single element"); a}
    case a :: b :: Nil  => And(a, b)
    case a :: as        => And(a, and(as) )
  }

  private def parseText(xml:Node):ReturnCodeRange = parseText(xml.text)

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


}

