package com.example

import akka.actor.SupervisorStrategy.{Resume, Restart}
import akka.actor.{OneForOneStrategy, Actor, ActorLogging, Props}
import com.example.Worker.{Work, WorkRequest, WorkerException}

class Supervisor extends Actor with ActorLogging {
  import Supervisor._
  import scala.concurrent.duration._

  val worker = context.actorOf(Worker.props(self), "worker")

  // Only restart the failing child 
  override val supervisorStrategy = OneForOneStrategy(maxNrOfRetries = 3,
    withinTimeRange = 5 seconds) {
    case _: WorkerException => Restart
  }

  def receive = {
  	case Initialize => 
	    log.info("[Supervisor] - initializing...")
  	  worker ! Work("Please complete this initial task for me")
    case WorkRequest(text: String) =>
      log.info(s"[Supervisor] - received a request for work ($text)")
      worker ! Work("Please complete another task for me")
  }
}

object Supervisor {
  val props = Props[Supervisor]
  case object Initialize
  case class WorkRequest(text: String)
}