package com.metaute.scala

import akka.actor.Actor
import scala.concurrent.ExecutionContext
import akka.actor.ActorSelection

class Generador extends Actor{
   private implicit val _: ExecutionContext = context.dispatcher
   private val receptor: ActorSelection = context.actorSelection("akka://SistemaActoresEcho/user/Receptor")
  
  override def receive: Receive = {
    case message: String => 
      						println(s"a generador llego $message voy a generar $message 1" )
      						receptor ! message + " 1"
      						
    case _ => println("se murio esta joda")
  }

}