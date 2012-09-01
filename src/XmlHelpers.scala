
package evaluationTool.xmlHelpers

import scala.xml._
import evaluationTool.exceptions.ReadXmlException

trait PropertyReader {
  
  def getNodes(property:String):NodeSeq



  def getProperty(property:String) =
    this.?(property).getOrElse(this throwPropertyException property)


  def ?!(property:String):String = this getProperty property

  
  def >?>(property:String):Option[Node] = this.getNodes(property).toList.headOption

  def >?!>(property:String):Node = this.>?>(property) match {
    case Some(n)  => n
    case None     => this throwPropertyException property
  }


  def getOptionalProperty(property:String) = {
    val nodes = getNodes(property)

    if(nodes.size > 1) 
      this throwPropertyException property
    
    if(nodes.size == 0) None
    else Some(nodes(0).text)
  }

  def ?(property:String):Option[String] = this getOptionalProperty property




  def getAtLeastOneProperty(property:String) =
    getNodes(property).map(_.text).toList

  def ?*(property:String):List[String] = this getAtLeastOneProperty property




  def getMultipleProperties(property:String) = {
    (this ?* property) match {
      case Nil  => this throwPropertyException property
      case x    => x
    }
  }

  def ?*!(property:String):List[String] = this getMultipleProperties property

  def throwPropertyException(property:String) = throw new ReadXmlException("no <" + property + "> tag")

}

case class NodeSeqPropertyReader(nodes:NodeSeq) extends PropertyReader {
  override def getNodes(property:String) = nodes \ property
}

case class NodePropertyReader(node:Node) extends PropertyReader {
  override def getNodes(property:String) = node \ property 
}

object Implicits {
  
  implicit def nodeSeq2PropertyReader(nodes:NodeSeq) =
    NodeSeqPropertyReader(nodes)

  implicit def node2PropertyReader(node:Node) = NodePropertyReader(node)

}

