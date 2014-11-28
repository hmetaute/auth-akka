package com.metaute.scala

import akka.actor.Actor
import scala.concurrent.ExecutionContext
import akka.actor.ActorSelection

class Receptor extends Actor{
  
   private implicit val _: ExecutionContext = context.dispatcher
   //private val generador: ActorSelection = context.actorSelection("akka://SistemaActoresEcho/user/Generador")
  
  
  override def receive: Receive = {
    case message: String => 
      println(s"a receptor llego $message y lo voy a retornar")
      sender ! message
    case _ => println("se jodio esto")
  }

}