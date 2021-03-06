
package edWood.actors

import edWood.actors.messages._
import edWood.config._
import edWood.run._
import edWood.data.{FileInputImpl, Input}

import java.io.File

import akka.actor.{Actor, ActorRef, Props}
import at.axelGschaider.loggsNProperties.LogsWithLazyId

class Worker extends Actor with LogsWithLazyId {
  

  def receive = {
    case Start(config) => {

      this.setLoggingId(config.id)

      info("starting")

      val wd = config.command.workingDirectory
      val executor = ExecutorFactory.getExecutor(loggingId, config.command.main, wd)
      val preJobs = config.command.pre.map( ExecutorFactory.getExecutor(loggingId, _, wd))
      val postJobs = config.command.post.map( ExecutorFactory.getExecutor(loggingId, _, wd))

      val stopableHub = StopableHub( preJobs ++ (executor :: postJobs ) )

      sender ! MyHandle(stopableHub)

      try {
        
        preJobs.foreach( _ exec true )

        executor.run
        config.reader readLife executor
        val fileInputs:List[Input] = config.files.map( f => FileInputImpl(f, new File(f)) )
        config.reader readPostRunSources fileInputs.toArray
        val data = config.reader.getData
        config.writers foreach (_ write data)

        val message = executor.execCode match {
          case 0    =>  Success
          case 666  =>  Failed("internal error: the job has not been run yet")
          case _    =>  Failed("external command failed for unknown reason")
        }
        
        postJobs.foreach( _ exec true )
  
        info( "sending " + message)
        sender ! message
      } catch {
        case e:Exception => { stopableHub.stop
                              val msg = "job died with error '" + e.getMessage + "'"
                              error(msg)
                              sender ! Failed(config.id + ": " + msg) }
      }


    }
  }


}



