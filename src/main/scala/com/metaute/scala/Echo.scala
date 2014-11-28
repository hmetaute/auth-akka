package com.metaute.scala

import akka.actor.ActorSystem
import scala.concurrent.ExecutionContext
import akka.actor.Props
import akka.actor.ActorRef

object Echo extends App{
  
  val actorSystem = ActorSystem("SistemaActoresEcho")
  implicit val _: ExecutionContext = actorSystem.dispatcher
  
  val generador: ActorRef = actorSystem.actorOf(Props[Generador], "Generador")
  val receptor: ActorRef = actorSystem.actorOf(Props[Receptor], "Receptor")
  
  generador ! "comenzo esta joda"
  
    /* End example */
  Thread.sleep(1000)
  actorSystem.shutdown()
  
}