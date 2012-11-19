package edWood.returnCodes


import edWood.exceptions.ReadXmlException
import edWood.xmlHelpers._
import edWood.xmlHelpers.Implicits._
import edWood.xmlHelpers.Utils._

import at.axelGschaider.utils.Utils._
import at.axelGschaider.loggsNProperties.DefaultLogs


import scala.xml._

//object SuccessMappingReader extends DefaultLogs {
//  def read(xml:Node, refs:Map[String, ReturnCodeRange]):Map[String, SuccessMapping] = {
//    val elems = for {
//      node  <- children(xml)
//      label <- attribute(node, "label")
//      _ = debug("resolving mapping '" + label + "'")
//      mapping = readSingleMapping(node, refs)
//    } yield (label, mapping)
//
//    Map() ++ elems
//  }
//
//
//  def readSingleMapping(xml:Node, refs:Map[String, ReturnCodeRange]):SuccessMapping = {
//    val success = handleXmlList( (xml \ "success").toList, refs )
//    val error = handleXmlList( (xml \ "error").toList, refs )
//
//    (success, error) match {
//      case (Nil, Nil) => DefaultSuccess
//      case (Nil, err) => JustErrors(err)
//      case (suc, Nil) => JustSuccesses(suc)
//      case (suc, err) => FullMapping(suc, err)
//    }
//
//  }
//
//  private def handleXmlList( list:List[Node], refs:Map[String, ReturnCodeRange]) =
//    list.map(xmlToRange(_, refs))
//
//  private def xmlToRange(xml:Node, refs:Map[String, ReturnCodeRange]): ReturnCodeRange =
//    (xml \ "@ref").toList.headOption.map(_.toString) match {
//      case Some(r) => 
//          refs.get(r).getOrElse(throw new ReadXmlException("could not find referenced range '" + r + "'"))
//      case None   => ReturnCodeRangeReader read xml
//    }
//
//    
//
//  def apply(refs:Map[String, ReturnCodeRange]) = new SuccessMappingReader(refs)
//}
//
//class SuccessMappingReader(refs:Map[String, ReturnCodeRange]) {
//  def read(xml:Node) = SuccessMappingReader.read(xml, refs)
//}
//
