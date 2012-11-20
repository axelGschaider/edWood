package edWood.returnCodes


import edWood.exceptions.ReadXmlException
import edWood.xmlHelpers._
import edWood.xmlHelpers.Implicits._
import edWood.xmlHelpers.Utils._

import scalaz.Scalaz._
import scalaz._

import at.axelGschaider.utils.Utils._
import at.axelGschaider.loggsNProperties.DefaultLogs


import scala.xml.{Node => XNode}


object SuccessMappingReader extends DefaultLogs {

  type RangeReference = Map[String, ReturnCodeRange]
  private case class RangeRefPimp(ref:RangeReference) {
    def to(s:String) = 
      ref.get(s).getOrElse(throw new ReadXmlException("could not resolve ranges reference '" + s + "'"))
  }
  private implicit def toPimpedRange(ref:RangeReference) = RangeRefPimp(ref)

  type RangeTuple = (List[ReturnCodeRange], List[ReturnCodeRange])
  private val emptyRanges:RangeTuple = (Nil, Nil)
  def success = Lens[RangeTuple, List[ReturnCodeRange]](_._1, (tuple, suc) => (suc, tuple._2))
  def failure = Lens[RangeTuple, List[ReturnCodeRange]](_._2, (tuple, fail) => (tuple._1, fail))


  def read(xml:XNode, ref:RangeReference = Map()):SuccessMapping = 
    children(xml).foldLeft(emptyRanges)( reducer(ref) ) match {
      case (Nil, Nil) => DefaultSuccess
      case (suc, Nil) => JustSuccesses(suc)
      case (Nil, err) => JustErrors(err)
      case (suc, err) => FullMapping(suc, err)
    }
  
  def resolveToRange( ref:RangeReference ) = (xml:XNode) => attribute(xml, "ref") match {
    case Some(x) => ref to x
    case None    => ReturnCodeRangeReader read xml
  }

  private val reducer = (refs:RangeReference) => refs |> resolveToRange |> reduceToTupple


  private def reduceToTupple(resolve:(XNode => ReturnCodeRange)) = (tuple:RangeTuple,xml:XNode) =>
    xml.label match {
      case "naming"  => tuple
      case "success" => success.mod( tuple, (resolve(xml) :: _) )
      case "failure" => failure.mod( tuple, (resolve(xml) :: _) )
      case someName  => throw new ReadXmlException("unknown tag <" + someName + "> in mapping definition")
    }




}

