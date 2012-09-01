
package edWood.run

import edWood.actors.MainActor
import edWood.actors.messages.{InitAndStart, StopAll}
import edWood.config._

import at.axelGschaider.loggsNProperties.Logs

import akka.actor.{ActorSystem, ActorRef, Props}



object KickOff extends Logs with Stopable {

  var system:Option[ActorSystem] = None
  var actor:Option[ActorRef] = None

  def start(config:GlobalConfig) = {

    this.forceShutdown

    info("starting actor system: " + config.general.actorSystem)

    val sys = ActorSystem(config.general.actorSystem)
    system = Some(sys)

    val act = sys.actorOf(Props[MainActor])
    actor = Some(act)
    
    act ! InitAndStart(config, this)

    info("started actor system")
  }

  def forceShutdown() = system.foreach(s => {

    actor.foreach( _ ! StopAll )

    info("stopping currently running system")
    s.shutdown
    system = None
    actor = None
  })

  def stop() = system.foreach( _.shutdown )
  
}


