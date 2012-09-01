package evaluationTool.actors

import evaluationTool.actors.messages._
import evaluationTool.config._
import evaluationTool.run.Stopable

import akka.actor.{Actor, ActorRef, Props}
import at.axelGschaider.loggsNProperties.Logs

class WorkerProxy extends Actor with Logs {
  
  private var handle:Option[Stopable] = None
  private var callBack:ActorRef = null
  private var config:JobConfig = null

  def receive = {
    
    case x:Workload2Proxy => x match {
      case Work(config) =>
        { info("WorkerProxy: starting Worker")
          context.actorOf(Props[Worker]) ! Start(config)
          this.config = config
          callBack = sender }
      case Cancel => this.handle foreach (_.stop())
    }
    
    case x:Worker2Proxy => x match {
      case Failed(msg) => { error("Worker failed with message '" + msg + "'")
                            this.callBack ! Done(this.config) }
      case Success     => { info("id: " + this.config.id + ": got Success")
                            this.callBack ! Done(this.config) }
      case MyHandle(h) => this.handle = Some(h)
    }

  }

}
