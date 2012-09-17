
package edWood.run

import java.io._
import java.util.Iterator;

import edWood.data._
import at.axelGschaider.loggsNProperties.Logs
import edWood.config.GeneralConfig


object ExecutorFactory extends Logs {



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

  private def wdToChange(wd:Option[String]) = wd.map("cd " ++ _ ++ " ;").getOrElse("")

  private def changeWd =wdToChange(this.workDir)
  
  def getExecutor(command:String, wd:Option[String]):Executor = ExecutorImpl( changeWd ++ wdToChange(wd) ++ command, interpreter)
  
}


trait Executor extends Runable with ProcessInput  {
  def exec(wait:Boolean)
}

private case class ExecutorImpl(command:String, interpreter:List[String]) extends Executor with Logs {
  
  var process:Option[Process] = None

  def getProcess = process match {
    case Some(p) => p
    case None    => throw new IOException("not started yet")
  }
  
  def run() = exec(false)

  def exec(wait:Boolean) = {
    debug("running '" + command + "'")
    val commandList = interpreter :+ command
    debug("commandList: " + commandList)
    val commandArray = commandList.toArray  //Array("/bin/sh", "-c", command)
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


