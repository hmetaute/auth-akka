package com.sura.seus2

import akka.actor.ActorSystem
import scala.concurrent.ExecutionContext
import akka.actor.Props
import akka.actor.ActorRef
import akka.actor.Inbox
import scala.concurrent.duration.FiniteDuration
import akka.actor.Actor

object SeusService extends App{
	val actorSystem = ActorSystem("ActoresSeus")

	val timeout: FiniteDuration = FiniteDuration(3000, "millis")

	implicit val _: ExecutionContext = actorSystem.dispatcher

	val seusLoginManager: ActorRef = actorSystem.actorOf(Props[SeusLogin], "SeusLogin")
	//Mensajes

	val standardIn = System.console()
	println("available commands:")
	println("login username")
	println("logout token")
	
	val login = "login\\s(.+)".r
	val logout = "logout\\s(.+)".r
	val validate = "validate\\s(.+)".r

	var command: String = ""
	val inbox = Inbox.create(actorSystem)

	while(!command.startsWith("exit")) {

		println("Enter a command> ")

		
		command = standardIn.readLine();
		
		command match {
		  case login(username) => {
		    println("Password> ")
			val pw = standardIn.readPassword()
			def concatChar (x:String,y:Char): String = x + y
			val strPw = pw.foldLeft("") (concatChar)

			//inbox.send(seusLoginManager, LoginMessage(username, strPw))
			
			actorSystem.actorOf(Props(new Actor() {
			  def receive = {
			    case _ => println("unrecognized message")
			  }
			  seusLoginManager ! LoginMessage(username, strPw)
			}))
		  }
		  case logout(token) => {
		    //inbox.send(seusLoginManager, CloseSessionMessage(token))
		    actorSystem.actorOf(Props(new Actor() {
			  def receive = {
			    case _ => println("unrecognized message")
			  }
			  seusLoginManager ! CloseSessionMessage(token)
		    }))
		  }
		  case validate(token) => {
//		    inbox.send(seusLoginManager, RefreshSessionMessage(token))
//
//			inbox.receive(timeout) match {
//			  case RefreshSessionResponse(valido) => println(s"desde el servicio me dicen que el token es valido? $valido")
//			  case _ => "no entendi lo que vino en el inbox"
//			}
		    
		    actorSystem.actorOf(Props(new Actor() {
			  def receive = {
			    case RefreshSessionResponse(valido) => {
			      println(s"desde el servicio me dicen que el token es valido? $valido")
			      context.stop(self)
			    }
			    case _ => {
			      println("no entendi lo que vino en el inbox")
			      context.stop(self)
			    }
			  }
			  seusLoginManager ! RefreshSessionMessage(token)
			}))
		  }
		  case "list" => {		    
		    actorSystem.actorOf(Props(new Actor() {
			  def receive = {
			    case LoggedUsersResponse(usuariosAutorizados) => {
			      println(s"desde el servicio me llegaron $usuariosAutorizados usuarios")
			      context.stop(self)
			    }
			    case _ => {
			      println("no entendi lo que vino en el inbox")
			      context.stop(self)
			    }
			  }
			  seusLoginManager ! LoggedUsersMessage()
			}))
		  }
		  case _ => println("not recognized command")
		}
		
	}
	
	actorSystem.actorOf(Props(new Actor() {
	  def receive = {
	    case _ => println("hola")
	  }
	}))

	actorSystem.shutdown()

}


case class LoginMessage(usr: String, pwd: String)
case class ValidateCredentials()

case class LoginSuccessfulMessage(usr: String, token: String)
case class LoginFailedMessage(reason: String, user: String)

case class CloseSessionMessage(token: String)
case class SessionClosedMessage(token: String)

case class LoggedUsersMessage()
case class LoggedUsersResponse(loggedUsers: Seq[String])

case class RefreshSessionMessage(token: String)
case class RefreshSessionResponse(isValidToken: Boolean)
