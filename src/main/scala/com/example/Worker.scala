package com.example

import akka.actor.{ActorRef, Actor, ActorLogging, Props}

class Worker(supervisor: ActorRef) extends Actor with ActorLogging {
  import Worker._

  @throws[Exception](classOf[Exception])
  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    log.info(s"Restarting because of $message")
  }


  @throws[Exception](classOf[Exception])
  override def postRestart(reason: Throwable) {
    log.info("Fresh actor ready!")
    supervisor ! HitMeAgain("Ready to work again")
  }

  def receive = {
  	case Supervisor.PingMessage(text) =>
  	  log.info("In Worker - received message: {}", text)
      throw new WorkerException("Some exception")
  }
}

object Worker {
  def props(supervisor: ActorRef): Props = Props(new Worker(supervisor))
  case class HitMeAgain(text: String)
  class WorkerException(msg: String) extends RuntimeException(msg)
}
