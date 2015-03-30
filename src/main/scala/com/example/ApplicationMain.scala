package com.example

import akka.actor.ActorSystem

object ApplicationMain extends App {
  val system = ActorSystem("MyActorSystem")
  val supervisor = system.actorOf(Supervisor.props, "supervisor")
  supervisor ! Supervisor.Initialize
  system.awaitTermination()
}