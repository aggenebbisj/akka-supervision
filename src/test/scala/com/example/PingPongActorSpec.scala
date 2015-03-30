package com.example

import akka.actor.{ActorRef, ActorSystem, Actor, Props}
import akka.testkit.{ TestActors, TestKit, ImplicitSender }
import org.scalatest.WordSpecLike
import org.scalatest.Matchers
import org.scalatest.BeforeAndAfterAll
 
class PingPongActorSpec(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {
 
  def this() = this(ActorSystem("MySpec"))
 
  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }
 
  "A Ping actor" must {
    "send back a ping on a pong" in {
      val pingActor = system.actorOf(Supervisor.props)
      pingActor ! Worker.HitMeAgain("pong")
      expectMsg(Supervisor.PingMessage("ping"))
    }
  }

  "A Pong actor" must {
    "send back a pong on a ping" in {
      val pingActor = system.actorOf(Supervisor.props)
      val pongActor: ActorRef = system.actorOf(Worker.props(pingActor))
      pongActor ! Supervisor.PingMessage("ping")
      expectMsg(Worker.HitMeAgain("pong"))
    }
  }

}
