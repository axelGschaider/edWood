package edWood.returnCodes


import edWood.exceptions.ReadXmlException
import edWood.xmlHelpers._
import edWood.xmlHelpers.Implicits._
import edWood.xmlHelpers.Utils._

import at.axelGschaider.utils.Utils._
import at.axelGschaider.loggsNProperties.DefaultLogs


import scala.xml._


object SuccessMappingReader extends DefaultLogs {

  type RangeReference = Map[String, ReturnCodeRange]
  type RangeTuple = (List[ReturnCodeRange], List[ReturnCodeRange])
  private val emptyRanges:RangeTuple = (Nil, Nil)


  def read(xml:Node, refs:RangeReference = Map()):SuccessMapping = 
    children(xml).foldLeft(emptyRanges)( reducer(refs) ) match {
      case (Nil, Nil) => DefaultSuccess
      case (suc, Nil) => JustSuccesses(suc)
      case (Nil, err) => JustErrors(err)
      case (suc, err) => FullMapping(suc, err)
    }
  
  def resolveToRange( refs:RangeReference ) = (xml:Node) => attribute(xml, "ref") match {
    case Some(ref) => 
      refs.get(ref).getOrElse(throw new ReadXmlException("could not resolve ranges reference '" + ref + "'"))
    case None      => ReturnCodeRangeReader read xml
  }

  private val reducer = (refs:RangeReference) => 
    reduceToTupple( resolveToRange(refs) )


  private def reduceToTupple(resolve:(Node => ReturnCodeRange)) = (tuple:RangeTuple,xml:Node) =>
    xml.label match {
      case "naming"  => tuple
      case "success" => ( resolve(xml) :: tuple._1
                        , tuple._2
                        )
      case "failure" => ( tuple._1
                        , resolve(xml) :: tuple._2 
                        )
      case someName  => throw new ReadXmlException("unknown tag <" + someName + "> in mapping definition")
    }




}

