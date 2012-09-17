package edWood.actors

import edWood.actors.messages._
import edWood.config._

import akka.actor.{Actor, ActorRef, Props}
import at.axelGschaider.loggsNProperties.Logs

import scala.collection.immutable.{Map, HashMap}

case class DataHolder(config:JobConfig, actor:ActorRef)


class WorkloadBalancer extends Actor with Logs {
  
  private var callBack:ActorRef = null
  private var maxJobs = 0
  private var noMore = false
  private var jobs:Map[String, DataHolder] = new HashMap()

  def receive = {
    case x:Main2Workload => x match {
      case Init(config)   => init(config)
      case NewJob(config) => startNew(config) match { 
                         case Some(a) => a ! Work(config)
                         case None    => this.callBack ! CantExceptNow(config)
                             }
      case NoMore         => { noMore = true
                               info("no more jobs for me")
                               if(jobs.size == 0) callBack ! AllDone }
      case StopAll        => { noMore = true
                               warn("killing all worker actors")
                               this.jobs.values.foreach( _.actor ! Cancel ) }
    }
    case x:Proxy2Workload => x match {
      case Done(config) => { info("got Done(" + config.id + ")")
                             oneDone(config).foreach( this.callBack ! _ ) }
    }
  }

  private def init(config:GeneralConfig) = {

    info("init")

    if(this.callBack != null)
      throw new Exception("can't be initialiced twice")

    this.callBack = sender

    this.maxJobs = config.maxJobs

    for (i <- 1 to maxJobs) this.callBack ! GimmeNew
  }

  private def startNew(config:JobConfig):Option[ActorRef] = 
    if( this.jobs.size >= this.maxJobs ) None
    else {
      val actor = context.actorOf(Props[WorkerProxy])
      this.jobs = this.jobs + ((config.id, DataHolder(config, actor)))
      Some(actor)
    }

  private def oneDone(config:JobConfig):Option[Workload2Main] = 
      this.jobs.get(config.id) match {
        case None     =>  { warn("job with id '" + config.id + "' sent me a DONE message. But I don't know this id.")
                            None }
        case Some(_)  =>  { this.jobs = this.jobs - config.id
                            this.getMessageForFather()  }
      }

  private def getMessageForFather():Option[Workload2Main] =
    if(this.noMore) {
      this.jobs.size match {
        case 0  => { info("sending AllDone")
                     Some(AllDone) }
        case js => {  info("waiting for " + this.jobs.size)
                      None }
      }
    }
    else {
      debug("requesting new")
      Some(GimmeNew)
    }

}
