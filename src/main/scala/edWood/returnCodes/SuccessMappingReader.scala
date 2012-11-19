package edWood.returnCodes


import edWood.exceptions.ReadXmlException
import edWood.xmlHelpers._
import edWood.xmlHelpers.Implicits._
import edWood.xmlHelpers.Utils._

import at.axelGschaider.utils.Utils._
import at.axelGschaider.loggsNProperties.DefaultLogs


import scala.xml._

object SuccessMappingReader extends DefaultLogs {
  def read(xml:Elem, refs:Map[String, ReturnCodeRange]):SuccessMapping =
    DefaultSuccess


  def apply(refs:Map[String, ReturnCodeRange]) = new SuccessMappingReader(refs)
}

class SuccessMappingReader(refs:Map[String, ReturnCodeRange]) {
  def read(xml:Elem) = SuccessMappingReader.read(xml, refs)
}

