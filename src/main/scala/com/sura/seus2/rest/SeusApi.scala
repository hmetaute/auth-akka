package com.sura.seus2.rest

import akka.actor.Actor
import spray.routing._
import spray.http._
import MediaTypes._
import akka.actor.Props
import com.sura.seus2.SeusLogin
import akka.actor.ActorRef
import com.sura.seus2.LoginMessage
import com.sura.seus2.LoginSuccessfulMessage
import com.sura.seus2.LoginFailedMessage

class SeusApi extends Actor with MyService {
  def actorRefFactory = context
  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(myRoute)
}
// this trait defines our service behavior independently from the service actor
trait MyService extends HttpService {
  val myRoute =
    path("seus") {
      val seusLoginManager: ActorRef = actorRefFactory.actorOf(Props[SeusLogin], "SeusLogin")
	  post {
	     
			  ctx => {
				  ctx.complete(StatusCodes.OK -> "Me acabaste de hacer un post")
			  }
	  } ~ 
        get {
          parameters('username.as[String], 'password.as[String]) { (username, password) =>
            respondWithMediaType(`text/plain`) { // XML is marshalled to `text/xml` by default, so we simply override here
              ctx =>
                {
                  actorRefFactory.actorOf(Props(new Actor() {
                    def receive: Receive = {
                      case LoginSuccessfulMessage(usr, token) => ctx.complete(StatusCodes.OK -> s"token is $token")
                      case LoginFailedMessage(reason, _) => ctx.complete(StatusCodes.Forbidden  -> s"no se pudo autenticar porque $reason")
                      case _ =>
                        ctx.complete(StatusCodes.OK -> "unrecognized message")
                        context.stop(self)
                        ()
                    }
                    seusLoginManager ! LoginMessage(username, password)
                  }))
                  ()
                }

            }
          }
        } 
      
    }
}