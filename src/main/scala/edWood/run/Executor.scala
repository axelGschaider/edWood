
package edWood.run

import java.io._
import java.util.Iterator;

import edWood.data._
import at.axelGschaider.loggsNProperties._
import edWood.config.GeneralConfig



trait Executor extends Runable with ProcessInput  {
  def exec(wait:Boolean)
}

object ExecutorFactory extends DefaultLogs {



  private var interpreterLocal = List("/bin/sh", "-c") 
  private var workDirLocal:Option[String] = None

  def workDir = workDirLocal
  def workDir_=(wd:Option[String]) = {
    debug("setting workDir to " + wd.getOrElse("current workDirectory"))
    workDirLocal = wd
  }

  def interpreter = interpreterLocal
  def interpreter_=(i:List[String]) = {
    debug("setting interpreter to " + i)
    interpreterLocal = i
  }

  private def wdToChange(wd:Option[String]) = wd.map("cd " + _ + " ;").getOrElse("")

  private def changeWd = wdToChange(this.workDir)
  
  def getExecutor(id:String, command:String, wd:Option[String]):Executor = 
    ExecutorImpl(id, 
                 changeWd + wdToChange(wd) + command, 
                 interpreter)
  
}


private case class ExecutorImpl(loggingId:String, command:String, interpreter:List[String]) extends Executor with LogsWithId {
  
  var process:Option[Process] = None

  def getProcess = process match {
    case Some(p) => p
    case None    => throw new IOException("not started yet")
  }
  
  def run() = exec(false)

  def exec(wait:Boolean) = {
    val commandList = interpreter :+ command
    val commandArray = commandList.toArray  

    debug("running '" + command + "'")
    debug("commandList: " + commandList)
    
    val p = Runtime.getRuntime().exec(commandArray)
    this.process = Some(p)
    
    if(wait) p.waitFor
  }

  def execCode():Int = this.process.map(_.waitFor).getOrElse(666)

  def stop() = {
    this.process.foreach( _.destroy() )
  }

  def getIdentifier():String = command

 

}


