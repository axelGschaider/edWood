
package evaluationTool.actors.messages

import evaluationTool.config._
import evaluationTool.run.Stopable

import akka.actor.ActorRef

sealed trait Outer2Main
case class InitAndStart(config:GlobalConfig, handle:Stopable) extends Outer2Main
case object StopAll extends Outer2Main with Main2Workload

sealed trait Main2Workload
sealed trait Workload2Main

case object GimmeNew extends Workload2Main
case object AllDone extends Workload2Main
case class CantExceptNow(config:JobConfig) extends Workload2Main

case object NoMore extends Main2Workload
case class NewJob(config:JobConfig) extends Main2Workload
case class Init(config:GeneralConfig) extends Main2Workload 

sealed trait Workload2Proxy
sealed trait Proxy2Workload

case class Work(config:JobConfig) extends Workload2Proxy
case object Cancel extends Workload2Proxy

case class Done(config:JobConfig) extends Proxy2Workload

sealed trait Proxy2Worker
sealed trait Worker2Proxy

case class Start(config:JobConfig) extends Proxy2Worker

case class Failed(msg:String) extends Worker2Proxy
case object Success extends Worker2Proxy
case class MyHandle(stopable:Stopable) extends Worker2Proxy


