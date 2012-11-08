package edWood.actors

import edWood.actors.messages._
import edWood.config._
import edWood.run.Stopable

import akka.actor.{Actor, ActorRef, Props, Terminated}
import at.axelGschaider.loggsNProperties.DefaultLogs

class WorkerProxy extends Actor with DefaultLogs {
  
  private var handle:Option[Stopable] = None
  private var callBack:ActorRef = null
  private var config:JobConfig = null

  def receive = {
    
    case x:Workload2Proxy => x match {
      case Work(config) =>
        { info("starting Worker")
          val worker = context.actorOf(Props[Worker]) 
          context.watch(worker)
          worker ! Start(config)
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

    case Terminated(_) => { error("Worker died unexpectedly.")
                            this.handle foreach {
                              warn("Trying to kill underlying process...")
                              _.stop()
                            }
                            this.callBack ! Done(this.config) }

  }

}
