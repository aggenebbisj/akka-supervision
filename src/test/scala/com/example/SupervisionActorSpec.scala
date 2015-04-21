package com.example

import akka.actor.Actor.Receive
import akka.actor.{ActorRef, ActorSystem, Actor, Props}
import akka.testkit.{TestActorRef, TestActors, TestKit, ImplicitSender}
import com.example.Worker.WorkerException
import org.scalatest.WordSpecLike
import org.scalatest.Matchers
import org.scalatest.BeforeAndAfterAll
 
class SupervisionActorSpec(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {
 
  def this() = this(ActorSystem("MySpec"))
 
  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }
 
//  "A Worker actor" must {
//    "throw an exception on a ping" in {
//      val worker = system.actorOf(Worker.props(null))
//      val supervisor = system.actorOf(Supervisor.props)
//
//      intercept[WorkerException] {
//        worker ! Supervisor.WorkMessage("Should trigger an exception")
//      }
//    }
//  }

//  "A Pong actor" must {
//    "send back a pong on a ping" in {
//      val supervisor = system.actorOf(Supervisor.props)
//      val worker: ActorRef = system.actorOf(Worker.props(supervisor))
//      worker ! Supervisor.PingMessage("ping")
//      expectMsg(Worker.HitMeAgain("pong"))
//    }
//  }

}
