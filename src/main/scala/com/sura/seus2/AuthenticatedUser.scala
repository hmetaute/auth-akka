package com.sura.seus2

import akka.actor.Actor
import java.util.UUID
import akka.actor.PoisonPill
import akka.actor.Props
import akka.actor.ActorRef
import akka.actor.ActorSelection
import scala.language.postfixOps
import scala.concurrent.duration.Duration
import scala.concurrent.duration.FiniteDuration
import java.nio.channels.ClosedSelectorException

class AuthenticatedUser(username: String, password: String) extends Actor{
  
  import context.dispatcher
  
  var tokenSeus = ""
  val usuariosValidos = List("hernan", "eliel", "luis")
  private val seusLogin: ActorSelection = context.actorSelection("akka://ActoresSeus/user/SeusLogin")
  val duration: FiniteDuration = FiniteDuration(30000, "millis")
  var scheduler: akka.actor.Cancellable = null
    
  override def receive: Receive ={
    case ValidateCredentials => 
      if(userValid){
        tokenSeus = generateToken
        scheduler = context.system.scheduler.scheduleOnce(duration, self, CloseSessionMessage(tokenSeus))
        
        println(s"Autenticado username $username con token generado de seus $tokenSeus")
        sender ! LoginSuccessfulMessage(username, tokenSeus)

      }else{
        println(s"Rechazado login de username $username ")
        sender ! LoginFailedMessage("No way, josei", username)
        context.stop(self)
        ()
      }
    case CloseSessionMessage(tokenSeus) => 
      println(s"cerrando la sesion del usuario $username!!")
      scheduler.cancel()
      seusLogin !  SessionClosedMessage(tokenSeus)
      context.stop(self)
      
    case RefreshSessionMessage(token) =>
      println(s"Refrescando sesion del usuario $username")
      scheduler.cancel
      scheduler = context.system.scheduler.scheduleOnce(duration, self, CloseSessionMessage(tokenSeus))
    case _ => println("mensaje no soportado")
  }
  
  
  def generateToken(): String={
    UUID.randomUUID().toString()
  }
  
  
  def userValid(): Boolean ={
    usuariosValidos.exists(_ == username)
  }
  
  //c

}