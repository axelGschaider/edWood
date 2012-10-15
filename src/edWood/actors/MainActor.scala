
package edWood.actors

import edWood.actors.messages._
import edWood.config._
import edWood.run.{Stopable, ExecutorFactory}

import akka.actor.{Actor, ActorRef, Props}
import at.axelGschaider.loggsNProperties.DefaultLogs

class MainActor extends Actor with DefaultLogs {

  var jobs:List[JobConfig] = Nil
  var workloadBalancer:ActorRef = null
  var handle:Stopable = null

  def receive = {
    case x:Outer2Main => x match {
      case InitAndStart(config, handle) => { init(config, handle)
                                             this.workloadBalancer ! Init(config.general) }
      case StopAll                      => warn("StopAll not implemented")
    }
    case x:Workload2Main => x match {
      case GimmeNew         => {
                                 debug("got GimmeNew")
                                 this.getNewJob() match {
                                   case Some(j) => this.workloadBalancer ! NewJob(j)
                                   case None    => this.workloadBalancer ! NoMore
                                 }
                               }
      case AllDone          => {
                                 debug("got AllDone")
                                 this.stop()
                               }
      case CantExceptNow(c) => {this.jobs = c :: this.jobs; info("got back a job. '" + c.id + "'") }
    }
  }

  private def stop() = {
    info("stopping actor system")
    handle.stop
    println("")
    println("")
    debug("have a nice day")
  }

  private def init(config:GlobalConfig, handle: Stopable) = {

    info("init")

    if(workloadBalancer != null)
      throw new Exception("can't be initialiced twice")


    ExecutorFactory.interpreter = config.general.interpreter
    ExecutorFactory.workDir = config.general.workingDirectory

    this.handle = handle
    this.jobs = config.jobs

    this.workloadBalancer = context.actorOf(Props[WorkloadBalancer])

  }

  private def getNewJob():Option[JobConfig] = this.jobs match {
    case (j::js) => { this.jobs = js
                      debug("sending job with id " + j.id)
                      Some(j) }
    case Nil     => None
  }
    

}

