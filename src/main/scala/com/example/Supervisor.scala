package com.example

import akka.actor.SupervisorStrategy.Restart
import akka.actor.{OneForOneStrategy, Actor, ActorLogging, Props}
import com.example.Worker.{HitMeAgain, WorkerException}

class Supervisor extends Actor with ActorLogging {
  import Supervisor._
  import scala.concurrent.duration._

  var counter = 0
  val worker = context.actorOf(Worker.props(self), "worker")

  override val supervisorStrategy = OneForOneStrategy(maxNrOfRetries = 3,
    withinTimeRange = 5 seconds) {
    case _: WorkerException => Restart
  }

  def receive = {
  	case Initialize => 
	    log.info("In Supervisor - starting supervision")
  	  worker ! PingMessage("ping")
    case HitMeAgain(text: String) =>
      log.info(s"Hit me again: $text")
      worker ! PingMessage("pingping")
  }
}

object Supervisor {
  val props = Props[Supervisor]
  case object Initialize
  case class PingMessage(text: String)
}