package edWood.config

import at.axelGschaider.loggsNProperties.DefaultLogs
import edWood.reader.ReaderAdapter
import edWood.writer.WriterAdapter





/**
 * Created by IntelliJ IDEA.
 * User: axel
 * Date: 26.03.12
 * Time: 13:49
 * To change this template use File | Settings | File Templates.
 */

case class Command(main:String, pre:List[String], post:List[String], workingDirectory:Option[String])
case class JobConfig(id:String, command:Command, reader:ReaderAdapter, files:List[String],  writers:List[WriterAdapter])
case class GeneralConfig(actorSystem:String, maxJobs:Int, interpreter:List[String], workingDirectory:Option[String]) extends DefaultLogs {
  debug("actorSystem: " + actorSystem)
  debug("maxJobs: " + maxJobs)
  debug("interpreter: " + interpreter)
}
case class GlobalConfig(general:GeneralConfig, jobs:List[JobConfig])


