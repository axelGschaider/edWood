package edWood.config

import java.io.File

import edWood.exceptions.{InitException, ReadXmlException}
import edWood.xmlHelpers._
import edWood.xmlHelpers.Implicits._
import edWood.reader.ReaderAdapter
import edWood.writer.WriterAdapter
import edWood.loadingHelpers.AdapterLoader._
import edWood.constants._

import scala.xml._

import at.axelGschaider.loggsNProperties.Logs



/**
 * Created by IntelliJ IDEA.
 * User: axel
 * Date: 26.03.12
 * Time: 13:49
 * To change this template use File | Settings | File Templates.
 */

case class Command(main:String, pre:List[String], post:List[String], workingDirectory:Option[String])
case class JobConfig(id:String, command:Command, reader:ReaderAdapter, files:List[String],  writers:List[WriterAdapter])
case class GeneralConfig(actorSystem:String, maxJobs:Int, interpreter:List[String], workingDirectory:Option[String]) extends Logs {
  debug("actorSystem: " + actorSystem)
  debug("maxJobs: " + maxJobs)
  debug("interpreter: " + interpreter)
}
case class GlobalConfig(general:GeneralConfig, jobs:List[JobConfig])

object ConfigReader extends Logs with ConfigConstants {

  
  def read(fileName:String):GlobalConfig = {
    
    val configFile = new File(fileName)
    
    if(!configFile.exists) 
      throw new InitException("config file '" + fileName + "' does not exist")
    
    if(!configFile.canRead) 
      throw new InitException("can not read config file '" + fileName + "'")
    
    val configXml = XML.loadFile(configFile)
    
    GlobalConfig (
      getGeneralConfig(configXml)
     ,getJobConfigs(configXml \ "job")
    )
  }
  
  private def getGeneralConfig(xml:Elem):GeneralConfig = {
    GeneralConfig( 
       xml ?! "instanceId"
     , (xml ? "maxJobs").map(_.toInt).getOrElse( DEFAULT_MAX_JOBS  )
     , (xml >?> "interpreter").map( _ ?*! "part" ).getOrElse( DEFAULT_INTERPRETER )
     , xml ? "workingDirectory"
    )
  }
  
  private def getJobConfigs(nodes:NodeSeq):List[JobConfig] = 
    nodes.map(getJobConfig(_)).toList
    
  private def getJobConfig(node:Node):JobConfig = 
    JobConfig(
      node ?!  "id"
     ,Command ( node ?! "command"
               ,node ?* "pre"
               ,node ?* "post"
               ,node ? "workingDirectory")
     ,handleReaderNodes( (node \  "reader").toList )
     ,node ?* "file"
     ,( node \ "writer" ).map(this.getWriter(_)).toList
    )

  private def handleReaderNodes(nodes:List[Node]):ReaderAdapter = nodes match {
    case Nil    => throw new Exception("no reader node")
    case n::Nil => this getReader n
    case _      => throw new Exception("more than one reader node")
  }

  private def getReader(node:Node):ReaderAdapter = {
    val reader:ReaderAdapter = node ?! "classPath"
    reader init ( node ?* "arg" ).toArray
    return reader
  }

  private def getWriter(node:Node):WriterAdapter = {
    val writer:WriterAdapter = node ?! "classPath"
    writer init ( node ?* "arg" ).toArray
    return writer
  }
  
}
