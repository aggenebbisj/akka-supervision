package com.example

import akka.actor.{ActorRef, Actor, ActorLogging, Props}
import com.example.Supervisor.WorkRequest

class Worker(supervisor: ActorRef) extends Actor with ActorLogging {
  import Worker._

  @throws[Exception](classOf[Exception])
  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    log.info(s"[Worker] - PreRestart - $message")
  }

  @throws[Exception](classOf[Exception])
  override def postRestart(reason: Throwable) {
    log.info("[Worker] - PostRestart - About to notify supervisor we are ready to receive work again")
    supervisor ! WorkRequest("Worker ready for more")
  }

  def receive = {
  	case Work(text) =>
  	  log.info("[Worker] - Received message: {}", text)
      throw new WorkerException("Some exception")
  }
}

object Worker {
  def props(supervisor: ActorRef): Props = Props(new Worker(supervisor))
  case class Work(text: String)
  class WorkerException(msg: String) extends RuntimeException(msg)
}
