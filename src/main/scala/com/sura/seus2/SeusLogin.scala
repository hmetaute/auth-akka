package com.sura.seus2

import akka.actor.Actor
import akka.actor.ActorRef
import scala.collection.mutable
import akka.actor.Props
import scala.concurrent.duration._


class SeusLogin extends Actor{
  import akka.actor.SupervisorStrategy._

  
  var activeSessions: Map[String, ActorRef] = Map.empty[String, ActorRef]
  var userTokens: Map[String, String] = Map.empty[String, String]
  var originalSender: ActorRef = null
  
  
  override def receive: Receive={
    case LoginMessage(usr , pwd) =>
        originalSender = sender()
      	val userSessionActor = context.actorOf(Props(new AuthenticatedUser(usr, pwd)), usr)
	    println(s"Seus System: me piden autenticar al usuario $usr con el pwd $pwd")
	    userSessionActor ! ValidateCredentials
	    
    case LoginSuccessfulMessage(usr, token) => 
      println(s"Seus System: me acaba de llegar el mensaje de que el usuario $usr autentico bien con el token $token")
      userTokens += (token -> usr)
      activeSessions += (usr -> sender())
      originalSender ! LoginSuccessfulMessage(usr, token)
      printSessionState
      
    case LoginFailedMessage(reason, usr) => 
      println(s"Seus System: No se autenticó el usuario $usr por la razon $reason")
      originalSender ! LoginFailedMessage(reason, usr)
      
    case SessionClosedMessage(token) =>
      userTokens.get(token) match {
        case Some(username) => 
          println(s"Seus System: se cerro correctamente la sesion del usuario $username")
	      userTokens -= (token)
	      activeSessions -= (username)
	      printSessionState
        case _ =>  println("Seus System: me pidieron borrar un man que no existe")  
      }
      
    case LoggedUsersMessage() => 
      println(s"Seus System: los usuarios que en este momento estan vivos son ${activeSessions.keySet}")
      sender ! LoggedUsersResponse(activeSessions.keySet.toSeq)
      
    case CloseSessionMessage(token) =>
      userTokens.get(token) match{
        case Some(userName) => activeSessions(userName) ! CloseSessionMessage(token)
        case _ => println("Seus System: me pediste borrar un actor que no existe")
      }
      
    case RefreshSessionMessage(token) =>
      userTokens.get(token) match {
        case Some(username) => 
          val actorRef = activeSessions(username)
          actorRef ! RefreshSessionMessage(token)
          sender ! RefreshSessionResponse(true)
        case _ => sender ! RefreshSessionResponse(false)
      }
    case _ => println ("Seus System: no comprendo") 
    
    
  }
  
  
  def printSessionState(): Unit={
    println(s" userTokens: $userTokens")
    println(s" sessions: ${activeSessions.keySet}")
  }
  

}